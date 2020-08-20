package com.shen.sharding.service.impl;

import com.shen.sharding.model.Sharding;
import com.shen.sharding.mapper.ShardingMapper;
import com.shen.sharding.service.ShardingService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 测试分表 服务实现类
 * </p>
 *
 * @author shenfuding
 * @since 2020-08-13
 */
@Service
public class ShardingServiceImpl extends ServiceImpl<ShardingMapper, Sharding> implements ShardingService {

}
