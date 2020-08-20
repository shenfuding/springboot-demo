package com.shen.elasticsearch.config;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Order implements Serializable {

    private String orderNo;
    private String goodsName;
    private String store;
    private Long uid;
    private Long totalPrice;

}
