package com.shen.tag.vo;

import com.shen.tag.model.NewsTag;
import lombok.Data;

@Data
public class NewsTagVO extends NewsTag {

    private Integer newsPicId;
    private String createTime;
}
