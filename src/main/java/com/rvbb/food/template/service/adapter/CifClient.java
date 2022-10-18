package com.rvbb.food.template.service.adapter;

import com.rvbb.food.template.dto.adapter.Cif;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "cif", url = "${cif.apis}")
public interface CifClient {

    @GetMapping(consumes = "application/json")
    List<Cif> getCif(@RequestParam("base") String base);

    @PostMapping(produces = "application/json", consumes = "application/json")
    Cif createNewCif(@RequestBody Cif request);

}
