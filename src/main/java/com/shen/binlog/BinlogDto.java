package com.shen.binlog;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BinlogDto {
    private String event;
    private Object value;
}
