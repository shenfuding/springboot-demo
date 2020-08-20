package com.shen.tag.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.oracle.tools.packager.Log;
import com.shen.httpresult.HttpResult;
import com.shen.tag.model.NewsTag;
import com.shen.tag.service.NewsTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * <p>
 * 新闻标签表 前端控制器
 * </p>
 *
 * @author shenfuding
 * @since 2020-08-12
 */
@Slf4j
@RestController
@RequestMapping("/tag/newsTag")
public class NewsTagController {

    @Autowired
    private NewsTagService newsTagService;

    @RequestMapping("/create")
    public HttpResult create() {
        NewsTag tag = new NewsTag();
        Random random = new Random();
        tag.setTitle("时事"+random.nextInt());
        newsTagService.insert(tag);
        log.info("-->创建成功，id:{}", tag.getId());
        return HttpResult.success();
    }

    @RequestMapping("/load")
    public HttpResult load() {
        NewsTag tag = newsTagService.selectById(1);
        return HttpResult.success(tag);
    }

    @RequestMapping("/query")
    public HttpResult query() {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("title", "时事");
        List<NewsTag> list = this.newsTagService.selectList(wrapper);
        return HttpResult.success(list);
    }

    @RequestMapping("/page/{currentPage}/{size}")
    public HttpResult page(@PathVariable("currentPage") Integer currentPage, @PathVariable("size") Integer size) {
        log.info("-->查询第{}页，每页{}条", currentPage, size);
        EntityWrapper<NewsTag> wrapper = new EntityWrapper<NewsTag>();
        wrapper.like("title", "时事");
        Page<NewsTag> page = new Page<>( currentPage, size, "id", true);
        page = newsTagService.selectPage(page, wrapper);
        return HttpResult.success(page);
    }

    @RequestMapping("/join/page/{currentPage}/{size}")
    public HttpResult joinPage(@PathVariable("currentPage") Integer currentPage, @PathVariable("size") Integer size) {
        log.info("-->联合查询第{}页，每页{}条", currentPage, size);
        Page<NewsTag> page = new Page<>( currentPage, size, "id", true);
        page = newsTagService.selectPage(page);
        return HttpResult.success(page);
    }
}

