package com.rvbb.food.template.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditFields implements Serializable {

  @Serial
  private static final long serialVersionUID = -3907285110989026618L;
  @CreatedDate
  @Column(name = "created_date", updatable = false)
  public Timestamp created;

  @LastModifiedDate
  @Column(name = "updated_date")
  public Timestamp lastUpdated;
}
