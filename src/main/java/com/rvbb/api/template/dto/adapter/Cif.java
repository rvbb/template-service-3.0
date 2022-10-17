package com.rvbb.api.template.dto.adapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cif {
    private String id;
    private String description;
    private String base;
    private Boolean strict;
    private Boolean idInjection;
    private List<BankListDemo> bankList;
    private Date created;
}
