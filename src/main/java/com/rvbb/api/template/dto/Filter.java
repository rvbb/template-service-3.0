package com.rvbb.api.template.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public abstract class Filter {

    private Map<String, String> sorts;
    private Integer pageNum;
    private Integer pageSize;
    private Integer totalPage;
}
