package com.rvbb.api.template.dto.financeinfo;

import com.rvbb.api.template.dto.Filter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
