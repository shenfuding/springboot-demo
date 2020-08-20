package com.shen.sharding.controller;


import com.oracle.tools.packager.Log;
import com.shen.httpresult.HttpResult;
import com.shen.sharding.model.Sharding;
import com.shen.sharding.service.ShardingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 测试分表 前端控制器
 * </p>
 *
 * @author shenfuding
 * @since 2020-08-13
 */
@Slf4j
@RestController
@RequestMapping("/sharding/")
public class ShardingController {

    @Autowired
    private ShardingService shardingService;

    @RequestMapping("/insert")
    public HttpResult insert() {
        Sharding s = new Sharding();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
        Date now = new Date();
        s.setTitle("标题"+sdf.format(now));
        shardingService.insert(s);
        log.info("新增数据成功，id:{}", s.getId());
        return HttpResult.success();
    }

    @RequestMapping("/load")
    public HttpResult load() {
        long id = 1293920954383020033L;
        return HttpResult.success(shardingService.selectById(id));
    }
}

