package com.shen.tag.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.shen.tag.model.NewsTag;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 新闻标签表 服务类
 * </p>
 *
 * @author shenfuding
 * @since 2020-08-12
 */
public interface NewsTagService extends IService<NewsTag> {

    Page<NewsTag> selectPage(Page<NewsTag> page);

}
