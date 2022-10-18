package com.rvbb.food.template.dto.financeinfo;

import com.rvbb.food.template.dto.Filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class FinanceInfoFilterInput extends Filter {

    @Size(max = 300)
    @NotBlank
    private String companyName;

    @NumberFormat
    private Byte status;

    @Override
    public String toString(){
        return "companyName=[" + companyName + "], status=[" + status + "]";
    }
}
