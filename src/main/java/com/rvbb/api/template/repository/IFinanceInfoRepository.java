package com.rvbb.api.template.repository;


import com.rvbb.api.template.entity.FinanceInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFinanceInfoRepository extends JpaRepository<FinanceInfoEntity, Long>, JpaSpecificationExecutor<FinanceInfoEntity> {

    Optional<FinanceInfoEntity> findByUuid(String uuid);

    @Query(value = "SELECT * FROM finance_info l WHERE id = (select max(ll.id) from finance_info ll)", nativeQuery = true)
    FinanceInfoEntity getLast();

}
