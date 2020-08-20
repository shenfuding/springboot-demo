package com.shen.binlog;

import com.alibaba.fastjson.JSON;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class BinlogClientRunner implements CommandLineRunner {

    @Value("${binlog.host}")
    private String host;

    @Value("${binlog.port}")
    private int port;

    @Value("${binlog.user}")
    private String user;

    @Value("${binlog.password}")
    private String password;

    // binlog server_id
    @Value("${server.id}")
    private long serverId;

    @Value("${binlog.database.table}")
    private String database_table;

    @Async
    @Override
    public void run(String... args) throws Exception {
        // 获取监听数据表数组
        List<String> databaseList = Arrays.asList(database_table.split(","));
        HashMap<Long, String> tableMap = new HashMap<Long, String>();
        // 创建binlog监听客户端
        log.info("创建binlog监听客户端");
        BinaryLogClient client = new BinaryLogClient(host, port, user, password);
        client.setServerId(serverId);
        client.registerEventListener((event -> {
            // binlog事件
            EventData data = event.getData();
            if (data != null) {
                if (data instanceof TableMapEventData) {
                    TableMapEventData tableMapEventData = (TableMapEventData) data;
                    tableMap.put(tableMapEventData.getTableId(), tableMapEventData.getDatabase() + "." + tableMapEventData.getTable());
                    log.info("table:{}.{}", tableMapEventData.getDatabase(), tableMapEventData.getTable());
                }
                // update数据
                if (data instanceof UpdateRowsEventData) {
                    UpdateRowsEventData updateRowsEventData = (UpdateRowsEventData) data;
                    String tableName = tableMap.get(updateRowsEventData.getTableId());
                    if (tableName != null && databaseList.contains(tableName)) {
                        String eventKey = tableName + ".update";
                        for (Map.Entry<Serializable[], Serializable[]> row : updateRowsEventData.getRows()) {
                            String msg = JSON.toJSONString(new BinlogDto(eventKey, row.getValue()));
                            log.info("update:{}", msg); // update:{"event":"echat_database_dev.t_news_tag.update","value":[7,"时事9"]}
                        }
                    }
                }
                // insert数据
                else if (data instanceof WriteRowsEventData) {
                    WriteRowsEventData writeRowsEventData = (WriteRowsEventData) data;
                    String tableName = tableMap.get(writeRowsEventData.getTableId());
                    if (tableName != null && databaseList.contains(tableName)) {
                        String eventKey = tableName + ".insert";
                        for (Serializable[] row : writeRowsEventData.getRows()) {
                            String msg = JSON.toJSONString(new BinlogDto(eventKey, row));
                            log.info("insert:{}", msg); // insert:{"event":"echat_database_dev.t_news_tag.insert","value":[8,"时事7"]}
                        }
                    }
                }
                // delete数据
                else if (data instanceof DeleteRowsEventData) {
                    DeleteRowsEventData deleteRowsEventData = (DeleteRowsEventData) data;
                    String tableName = tableMap.get(deleteRowsEventData.getTableId());
                    if (tableName != null && databaseList.contains(tableName)) {
                        String eventKey = tableName + ".delete";
                        for (Serializable[] row : deleteRowsEventData.getRows()) {
                            String msg = JSON.toJSONString(new BinlogDto(eventKey, row));
                            log.info("delete:{}", msg); // {"event":"echat_database_dev.t_news_tag.delete","value":[8,"时事7"]}
                        }
                    }
                }
            }
        }));
        client.connect();
    }
}
