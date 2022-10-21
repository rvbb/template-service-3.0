package com.rvbb.food.template;

import com.rvbb.food.template.dto.adapter.BankListDemo;
import com.rvbb.food.template.dto.adapter.Cif;
import com.rvbb.food.template.service.impl.CifService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CifServiceTest {

    @Autowired
    CifService service;

    /*	{
        "_id" : ObjectId("604ee8b4a91a422bfea0dae7"),
        "description" : "Using credit and loan",
        "base" : "User",
        "strict" : true,
        "idInjection" : false,
        "_class" : "webflux.demo.customer.model.Cif"
    }*/
    @Test
    void givenUser_whenGetCif_thenReturnManyCifs() {
        String base = "Users";
        List<Cif> cifList = service.getCif(base);
        assertTrue(cifList != null && cifList.size() > 0);
    }

    @Test
    void givenVCB_whenGetCif_thenReturnOneCif() {
        String base = "VCB";
        List<Cif> cifList = service.getCif(base);
        assertTrue(cifList != null && base.equals(cifList.get(0).getBase()) );
    }

    @Test
    void givenNotExistedBase_whenGetCif_thenReturnEmpty() {
        String base = "not existence";
        List<Cif> cifList = service.getCif(base);
        assertTrue(cifList != null && cifList.size() == 0);
    }

    /*
        {
            "name": "Customer c",
            "description": "customer cccccccccccccccccc",
            "base": "VCB",
            "idInjection": true,
            "strict": false,
            "banklist": [{"bankName":"Vietinbnak", "type":1}]
        }
    */
    @Test
    void givenCifRequest_whenAddCif_thenCreateNewOne() {
        BankListDemo bank = BankListDemo.builder()
                .type(1)
                .bankName("bankname Test")
                .build();
        Cif cifRequest = Cif.builder()
                .base("Test")
                .description("UT create cif")
                .strict(true)
                .idInjection(false)
                .bankList(Arrays.asList(bank))
                .build();
        Cif createdCif = service.createCif(cifRequest);
        assertTrue(createdCif != null && "Test".equalsIgnoreCase(createdCif.getBase()));
    }

}
