package com.rvbb.api.template.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditFields implements Serializable {

  @CreatedDate
  @Column(name = "created_date", updatable = false)
  public Timestamp created;

  @LastModifiedDate
  @Column(name = "updated_date")
  public Timestamp lastUpdated;
}
