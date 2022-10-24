package com.rvbb.food.template.controller.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

import com.rvbb.food.template.common.util.LogIt;
import com.rvbb.food.template.controller.FinanceInfoController;
import com.rvbb.food.template.dto.Response;
import com.rvbb.food.template.dto.financeinfo.FinanceInfoFilterInput;
import com.rvbb.food.template.dto.financeinfo.FinanceInfoInput;
import com.rvbb.food.template.dto.financeinfo.FinanceInfoRes;
import com.rvbb.food.template.service.FinanceInfoService;
import com.rvbb.food.template.validator.FinanceInfoValidator;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@RestController
@AllArgsConstructor
public class FinanceInfoControllerImpl implements FinanceInfoController {

    private final FinanceInfoService loanFinInfoService;
    private final FinanceInfoValidator loanFinInfoValidator;

    @Override
    @LogIt
    public Response<FinanceInfoRes> create(@Valid FinanceInfoInput request) {
        FinanceInfoRes result = loanFinInfoService.create(request);
        return Response.ok(result);
    }

    @Override
    public Response<FinanceInfoRes> getLast() {
        return Response.ok(loanFinInfoService.getLast());
    }

    @Override
    @LogIt
    public Response<FinanceInfoRes> update(String uuid, @Valid FinanceInfoInput request) {
        FinanceInfoRes lastFinInfo = loanFinInfoService.get(uuid);
        loanFinInfoValidator.validateInputType(request);
        loanFinInfoValidator.checkSimilarWithLast(lastFinInfo, request);
        FinanceInfoRes result = loanFinInfoService.update(uuid, request);
        return Response.ok(result);
    }

    @Override
    @LogIt
    public Response<FinanceInfoRes> get(String uuid) {
        return Response.ok(loanFinInfoService.get(uuid));
    }

    @Override
    @LogIt
    public Response<List<FinanceInfoRes>> list() {
        return Response.ok(loanFinInfoService.list());
    }

    @Override
    @LogIt
    public Response<FinanceInfoRes> del(String uuid) {
        return Response.ok(loanFinInfoService.del(uuid));
    }

    @Override
    @LogIt
    public Response<Page<FinanceInfoRes>> filter(FinanceInfoFilterInput filter) {
        log.debug("filter={}", filter);
        return Response.ok(loanFinInfoService.doFilter(filter));
    }

    @Override
    @LogIt
    public Response<Page<FinanceInfoRes>> filter(String[] sort, String[] condition,
                                                 @Valid @Min(value = 0L, message = "The value must be positive") int page,
                                                 @Valid @Min(value = 0L, message = "The value must be positive") int size) {
        loanFinInfoValidator.validateFilter(sort, condition, page, size);
        return Response.ok(loanFinInfoService.doFilter(sort, condition, page, size));
    }

}
