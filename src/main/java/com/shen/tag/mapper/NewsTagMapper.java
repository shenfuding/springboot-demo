package com.shen.tag.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.shen.tag.model.NewsTag;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 新闻标签表 Mapper 接口
 * </p>
 *
 * @author shenfuding
 * @since 2020-08-12
 */
public interface NewsTagMapper extends BaseMapper<NewsTag> {

    /**
     * 传递page参数即自动分页
     * @param page
     * @return
     */
    List<NewsTag> queryPage(Pagination page);

}
