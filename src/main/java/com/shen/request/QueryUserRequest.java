package com.shen.request;

import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class QueryUserRequest {

    @NotNull(message = "用户ID不能为空")
    private Long uid;

    @NotBlank(message="用户名不能为空")
    private String username;

    @NotEmpty(message="集合不能为空")
    List<String> list;
}
