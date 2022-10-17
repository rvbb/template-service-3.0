package com.rvbb.api.template.dto;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@Data
@MappedSuperclass
public class AuditDto {

  private Timestamp createdDate;

  private Timestamp lastUpdatedDate;
}
