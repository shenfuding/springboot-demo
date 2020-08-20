package com.shen.tag.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.shen.tag.model.NewsTag;
import com.shen.tag.mapper.NewsTagMapper;
import com.shen.tag.service.NewsTagService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 新闻标签表 服务实现类
 * </p>
 *
 * @author shenfuding
 * @since 2020-08-12
 */
@Service
public class NewsTagServiceImpl extends ServiceImpl<NewsTagMapper, NewsTag> implements NewsTagService {

    @Autowired
    private NewsTagMapper mapper;

    @Override
    public Page<NewsTag> selectPage(Page<NewsTag> page) {
        List<NewsTag> list = mapper.queryPage(page);
        return page.setRecords(list);
    }
}
