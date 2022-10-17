package com.rvbb.api.template.dto.financeinfo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
