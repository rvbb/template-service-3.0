package com.rvbb.food.template.dto;

import lombok.Data;

import jakarta.persistence.MappedSuperclass;
import java.sql.Timestamp;

@Data
@MappedSuperclass
public class AuditDto {

  private Timestamp createdDate;

  private Timestamp lastUpdatedDate;
}
