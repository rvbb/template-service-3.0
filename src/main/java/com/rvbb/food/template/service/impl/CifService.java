package com.rvbb.food.template.service.impl;

import com.rvbb.food.template.common.util.LogIt;
import com.rvbb.food.template.dto.adapter.Cif;
import com.rvbb.food.template.service.adapter.CifClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CifService {

    private final CifClient cifClient;

    @LogIt
    public List<Cif> getCif(String base) {
        return cifClient.getCif(base);
    }

    @LogIt
    public Cif createCif(Cif cif) {
        return cifClient.createNewCif(cif);
    }
}
