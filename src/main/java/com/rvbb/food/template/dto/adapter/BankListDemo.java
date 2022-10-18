package com.rvbb.food.template.dto.adapter;

import lombok.*;

import java.util.Date;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankListDemo {
    private long id;
    private String bankName;
    private int type;
    private Date created;
}
