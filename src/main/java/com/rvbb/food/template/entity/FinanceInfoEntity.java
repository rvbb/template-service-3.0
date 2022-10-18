package com.rvbb.food.template.entity;

import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "finance_info")
public class FinanceInfoEntity extends AuditFields implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "pre_tax_income")
    private BigDecimal preTaxIncome;

    @Column(name = "expense")
    private BigDecimal expense;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_address")
    private String companyAddress;

    @Column(name = "status")
    private Byte status;

    @Column(name = "uuid", unique = true)
    private String uuid;
}
