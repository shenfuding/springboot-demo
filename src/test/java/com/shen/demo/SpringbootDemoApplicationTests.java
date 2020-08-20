package com.shen.demo;

import com.alibaba.fastjson.JSONObject;
import com.shen.elasticsearch.config.Order;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.io.IOException;
import java.util.*;

@Slf4j
@SpringBootTest
class SpringbootDemoApplicationTests {

	@Autowired
	private RestHighLevelClient restHighLevelClient;

	@Autowired
	private ElasticsearchRestTemplate template;

	// 指定超时时间
	// 方式一：request.timeout(TimeValue.timeValueSeconds(1));
	// 方式二：request.timeout("1s");

	/**
	 * 创建索引
	 */
	@Test
	public void createIndex() throws IOException {
		log.info("开始创建索引..");
		String indexName = "index_blog";
		// 查询索引请求
		GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
		boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
		if(exists) {
			log.info("索引{}已存在", indexName);
			return;
		}
		// 创建索引请求
		CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
		// 创建索引并获取响应
		CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
		log.info("创建索引{}响应：{}", indexName, createIndexResponse.index());
	}

	/**
	 * 删除索引
	 * @throws IOException
	 */
	@Test
	public void deleteIndex() throws IOException {
		String indexName = "index_order";
		// 查询索引请求
		GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
		boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
		if(!exists) {
			log.info("索引{}不存在", indexName);
			return;
		}
		// 删除索引请求
		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
		AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
		log.info("-->删除索引{}结果：{}", indexName, acknowledgedResponse.isAcknowledged());
	}

	/**
	 * 创建文档，如果文档存在进会进行更新
	 * @throws IOException
	 */
	@Test
	public void createDocument() throws IOException {
		Order order1 = new Order("1001", "苹果手机", "天猫商城", 15888888888L, 8999L);
		Order order2 = new Order("1002", "小米手机","小米商城", 15888888888L, 3999L);
		Order order3 = new Order("1003", "华为手机", "华为商城", 15888888888L, 4999L);
		List<Order> list = new ArrayList<>();
		list.add(order1);
		list.add(order2);
		list.add(order3);
		String indexName = "index_order";
		for(Order order : list) {
			IndexRequest indexRequest = new IndexRequest(indexName);
			indexRequest.id(order.getOrderNo()); // 文档ID
			indexRequest.source(JSONObject.toJSONString(order), XContentType.JSON); // 文档内容
			// 执行创建
			IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
			log.info("保存文档{}结果: {}", order.getOrderNo(), indexResponse.status()); // 成功返回 CREATED
			if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
				log.info("文档{}创建成功", order.getOrderNo());
			} else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
				log.info("文档{}更新成功", order.getOrderNo());
			}
			ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
			if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
				log.info("shardInfo.getTotal()={}, shardInfo.getSuccessful()={}", shardInfo.getTotal(), shardInfo.getSuccessful());
				log.error("Please Handle the situation where number of successful shards is less than total shards");
			}
			if (shardInfo.getFailed() > 0) {
				for (ReplicationResponse.ShardInfo.Failure failure :
						shardInfo.getFailures()) {
					String reason = failure.reason();
					log.error("失败原因:{}", reason);
				}
			}
		}
	}

	/**
	 * 批量创建文档
	 * @throws IOException
	 */
	@Test
	public void batchCreateDocuemnt() throws IOException {
		String indexName = "index_order";
		BulkRequest bulkRequest = new BulkRequest();
		Order order1 = new Order("1001", "苹果手机", "天猫商城", 15888888888L, 8999L);
		Order order2 = new Order("1002", "小米手机","小米商城", 15888888888L, 3999L);
		Order order3 = new Order("1003", "华为手机", "华为商城", 15888888888L, 4999L);
		List<Order> list = new ArrayList<>();
		list.add(order1);
		list.add(order2);
		list.add(order3);
		for(Order order : list) {
			IndexRequest indexRequest = new IndexRequest(indexName);
			indexRequest.id(order.getOrderNo()); // 文档ID
			indexRequest.source(JSONObject.toJSONString(order), XContentType.JSON); // 文档内容
			bulkRequest.add(indexRequest);
		}
		// 同步创建
//		BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
//		log.info("批量创建文档，结果: {}, hasFailures: {}", bulkResponse.status(), bulkResponse.hasFailures());
		// 批量创建文档，结果: OK, hasFailures: false

		// 异步创建
		restHighLevelClient.bulkAsync(bulkRequest, RequestOptions.DEFAULT, new ActionListener<BulkResponse>() {
			@Override
			public void onResponse(BulkResponse bulkResponse) {

				if (bulkResponse.hasFailures()) {
					log.info("批量创建存在失败记录");
				} else {
					log.info("批量创建成功");
				}
//				for (BulkItemResponse bulkItemResponse : bulkResponse) {
//					if (bulkItemResponse.isFailed()) {
//						BulkItemResponse.Failure failure =
//								bulkItemResponse.getFailure();
//						log.error("{}/{}创建失败, message: ", failure.getIndex(), failure.getId(), failure.getMessage());
//					} else {
//						log.info("{}/{}创建成功", bulkItemResponse.getIndex(), bulkItemResponse.getId());
//					}
//				}
			}

			@Override
			public void onFailure(Exception e) {
				log.error("批量创建失败", e);
			}
		});


	}

	/**
	 * 根据索引和文档ID判断文档是否存在
	 */
	@Test
	public void documentExists() throws IOException {
		String indexName = "index_order";
		String docId = "1003";
		GetRequest getRequest = new GetRequest(indexName, docId);
		boolean result = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
		log.info("文档{}是否存在: {}", docId, result);
	}

	/**
	 * 根据索引和文档ID查询文档json内容
	 * @throws IOException
	 */
	@Test
	public void queryDocument() throws IOException {
		String indexName = "index_order";
		String docId = "1003";
		GetRequest getRequest = new GetRequest(indexName, docId);

		// 指定返回字段
		String[] includes = new String[]{"orderNo", "goodsName"};
		String[] excludes = Strings.EMPTY_ARRAY; // 可以反着来指定不返回的字段
		FetchSourceContext fetchSourceContext =
				new FetchSourceContext(true, includes, excludes);
		getRequest.fetchSourceContext(fetchSourceContext);

		boolean result = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
		if(result) {
			GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
			String documentJson = getResponse.getSourceAsString(); // getResponse.getSourceAsMap() 返回Map
			log.info("文档{} JSON 内容: {}", docId, documentJson);
		}
	}

	/**
	 * 修改文档
	 * 如果文档不存在会报错
	 * @throws IOException
	 */
	@Test
	public void updateDocument() throws IOException {
		String indexName = "index_order";
		String docId = "1001";
		Order orderForUpdate = new Order("1002", "华为荣耀手机", "华为商城", 15813809741L, 5000L);
		UpdateRequest updateRequest = new UpdateRequest(indexName, docId);
		updateRequest.doc(JSONObject.toJSONString(orderForUpdate), XContentType.JSON);
		UpdateResponse response = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
		log.info("更新文档{}结果: {}", docId, response.status()); // 成功返回OK
	}

	/**
	 * 删除文档
	 * 文档不存在会返回NOT_FOUND
	 * @throws IOException
	 */
	@Test
	public void deleteDocument() throws IOException {
		String indexName = "index_order";
		String docId = "1001";
		DeleteRequest deleteRequest = new DeleteRequest(indexName, docId);
		DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
		log.info("--删除文档{}结果: {}", docId, deleteResponse.status()); // 成功返回OK，如果不存在返回NOT_FOUND
	}

	/**
	 * term精确查询
	 * @throws IOException
	 */
	@Test
	public void search() throws IOException {
		String indexName = "index_order";

		// 构建搜索条件
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		// 使用 term 精确查询，（前提是orderNo字段类型是keyword，通常是根据订单号精确查询)
		TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("orderNo", "1001");
		// goodsName类型为text，会进行分词，使用精确查询是查不到结果的
//		TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("goodsName", "小米手机");
		searchSourceBuilder.query(termQueryBuilder);

		SearchRequest searchRequest = new SearchRequest(indexName);
		searchRequest.source(searchSourceBuilder);

		SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		log.info("命中个数:{}，最高分数: {}", response.getHits().getTotalHits().value, response.getHits().getMaxScore());
		if(response.getHits() != null && response.getHits().getTotalHits().value > 0) {
			log.info("=====命中对象=====");
			for(SearchHit searchHit : response.getHits().getHits()) { // 遍历命中对象
				log.info(searchHit.getSourceAsString());
			}
		}
	}

	/**
	 * 查询所有，并分页
	 * @throws IOException
	 */
	@Test
	public void search2() throws IOException {
		String indexName = "index_order";

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchSourceBuilder.from(0);
		searchSourceBuilder.size(2);
		SearchRequest searchRequest = new SearchRequest(indexName);
		searchRequest.source(searchSourceBuilder);

		SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		log.info("命中个数:{}，最高分数: {}", response.getHits().getTotalHits().value, response.getHits().getMaxScore());
		if(response.getHits() != null && response.getHits().getTotalHits().value > 0) {
			log.info("=====命中对象=====");
			for(SearchHit searchHit : response.getHits().getHits()) { // 遍历命中对象
				log.info(searchHit.getSourceAsString());
			}
		}
	}

	/**
	 * 多条件组合查询
	 * matchQuery会进行分词查询
	 * @throws IOException
	 */
	@Test
	public void search4() throws IOException {
		String indexName = "index_order";

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		MatchQueryBuilder builder1 = QueryBuilders.matchQuery("goodsName", "苹果"); // 分词查询
		MatchQueryBuilder builder2 = QueryBuilders.matchQuery("store", "华为"); // 分词查询

		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		queryBuilder.should(builder1).should(builder2);
		searchSourceBuilder.query(queryBuilder);

		searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC)); // 匹配度最高的排前面
		searchSourceBuilder.sort(new FieldSortBuilder("totalPrice").order(SortOrder.DESC)); // 分数相同的按价格排序
		searchSourceBuilder.from(0);
		searchSourceBuilder.size(3);

		// 高亮显示， 貌似只对text类型有效
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		HighlightBuilder.Field highlightGoodsName = new HighlightBuilder.Field("goodsName");
		highlightGoodsName.highlighterType("unified");
		highlightBuilder.field(highlightGoodsName);

//		HighlightBuilder.Field highlightTotalPrice = new HighlightBuilder.Field("totalPrice");
//		highlightTotalPrice.highlighterType("unified");
//		highlightBuilder.field(highlightTotalPrice);

		searchSourceBuilder.highlighter(highlightBuilder);
		searchSourceBuilder.timeout(TimeValue.timeValueSeconds(30)); // 设置超时
		SearchRequest searchRequest = new SearchRequest(indexName);
		searchRequest.source(searchSourceBuilder);


		SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		if(response.status().getStatus() == 200) {
			log.info("命中个数:{}，最高分数: {}", response.getHits().getTotalHits().value, response.getHits().getMaxScore());
			if (response.getHits() != null && response.getHits().getTotalHits().value > 0) {
				log.info("=====命中对象=====");
				for (SearchHit hit : response.getHits().getHits()) { // 遍历命中对象
					log.info("{}, 分数：{}", hit.getSourceAsString(), hit.getScore());

					// 获取高亮字段
					Map<String, HighlightField> highlightFields = hit.getHighlightFields();
					HighlightField highlight1 = highlightFields.get("goodsName");
					if (highlight1 != null) {
						String goodsName = highlight1.fragments()[0].string();
						log.info(goodsName);
					}

					HighlightField highlight2 = highlightFields.get("totalPrice");
					if (highlight2 != null) {
						String totalPrice = highlight2.fragments()[0].string();
						log.info(totalPrice);
					}
				}
			}
		}
	}

	@Test
	public void search3() throws IOException {
		String indexName = "index_order";

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery("goodsName", "手机")); // 单字段 -> 单值 分词查询
//		searchSourceBuilder.query(QueryBuilders.multiMatchQuery("华为商城", "goodsName", "store")); // 根据goodsName和store两个字段查询  多字段 -> 单值  分词查询


//		Set<String> values = new HashSet<>();
//		values.add("1001");
//		values.add("1002");
//		searchSourceBuilder.query(QueryBuilders.termsQuery("orderNo", values)); // 单字段 -> 多值 精确匹配
//		searchSourceBuilder.query(QueryBuilders.termsQuery("orderNo", "1001", "1002")); // 单字段 -> 多值 精确匹配


		searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC)); // 匹配度最高的排前面
		searchSourceBuilder.sort(new FieldSortBuilder("totalPrice").order(SortOrder.DESC)); // 分数相同的按价格排序
		searchSourceBuilder.from(0);
		searchSourceBuilder.size(3);

		// 高亮显示， 貌似只对text类型有效
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		HighlightBuilder.Field highlightGoodsName = new HighlightBuilder.Field("goodsName");
		highlightGoodsName.highlighterType("unified");
		highlightBuilder.field(highlightGoodsName);

//		HighlightBuilder.Field highlightTotalPrice = new HighlightBuilder.Field("totalPrice");
//		highlightTotalPrice.highlighterType("unified");
//		highlightBuilder.field(highlightTotalPrice);

		searchSourceBuilder.highlighter(highlightBuilder);
		searchSourceBuilder.timeout(TimeValue.timeValueSeconds(30)); // 设置超时
		SearchRequest searchRequest = new SearchRequest(indexName);
		searchRequest.source(searchSourceBuilder);


		SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		if(response.status().getStatus() == 200) {
			log.info("命中个数:{}，最高分数: {}", response.getHits().getTotalHits().value, response.getHits().getMaxScore());
			if (response.getHits() != null && response.getHits().getTotalHits().value > 0) {
				log.info("=====命中对象=====");
				for (SearchHit hit : response.getHits().getHits()) { // 遍历命中对象
					log.info("{}, 分数：{}", hit.getSourceAsString(), hit.getScore());

					// 获取高亮字段
					Map<String, HighlightField> highlightFields = hit.getHighlightFields();
					HighlightField highlight1 = highlightFields.get("goodsName");
					if (highlight1 != null) {
						String goodsName = highlight1.fragments()[0].string();
						log.info(goodsName);
					}

					HighlightField highlight2 = highlightFields.get("totalPrice");
					if (highlight2 != null) {
						String totalPrice = highlight2.fragments()[0].string();
						log.info(totalPrice);
					}
				}
			}
		}
	}
}
