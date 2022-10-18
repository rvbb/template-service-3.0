package com.rvbb.food.template.dto.financeinfo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class FinanceInfoInput {

    @Size(max = 15)
    @NotBlank
    private String preTaxIncome;

    @Size(max = 300)
    @NotBlank
    private String companyName;

    @Size(max = 500)
    @NotBlank
    private String companyAddress;

    @Size(max = 15)
    @NotBlank
    private String expense;

    @NumberFormat
    private Byte status;
}
