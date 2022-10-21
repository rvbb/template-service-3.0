package com.rvbb.food.template.service.adapter;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.rvbb.food.template.config.FeignConfig;
import com.rvbb.food.template.dto.adapter.Cif;

@FeignClient(contextId = "cifApis", name = "cif-api", url = "${cif.apis}", configuration = FeignConfig.class)
public interface CifClient {

    @GetMapping(consumes = "application/json")
    List<Cif> getCif(@RequestParam("base") String base);

    @PostMapping(produces = "application/json", consumes = "application/json")
    Cif createNewCif(@RequestBody Cif request);

}
