package com.shen.kafka.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 测试订单消息
 */
@Data
@ToString
public class Order implements Serializable {

    private String orderNo;

}
