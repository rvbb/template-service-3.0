package com.rvbb.api.template.controller;

import com.rvbb.api.template.controller.handler.Error;
import com.rvbb.api.template.dto.financeinfo.FinanceInfoInput;
import com.rvbb.api.template.dto.financeinfo.FinanceInfoRes;
import com.rvbb.api.template.dto.Response;
import com.rvbb.api.template.dto.financeinfo.FinanceInfoFilterInput;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Api(value = "Loan financial information API")
@RequestMapping("finance")
public interface IFinanceInfoController {
    @ApiOperation(value = "Create")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FinanceInfoInput.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    @PostMapping
    Response<FinanceInfoRes> create(@Valid @RequestBody FinanceInfoInput request);

    @ApiOperation(value = "Get last")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FinanceInfoRes.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    @GetMapping("/last")
    Response<FinanceInfoRes> getLast();

    @ApiOperation(value = "Update")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FinanceInfoInput.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    @PutMapping("/{uuid}")
    Response<FinanceInfoRes> update(@PathVariable String uuid, @Valid @RequestBody FinanceInfoInput request);

    @ApiOperation(value = "Read One")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FinanceInfoInput.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    @GetMapping("/{uuid}")
    Response<FinanceInfoRes> get(@PathVariable String uuid);

    @ApiOperation(value = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FinanceInfoInput.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    @GetMapping("/list")
    Response<List<FinanceInfoRes>> list();

    @ApiOperation(value = "Delete One")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FinanceInfoInput.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    @DeleteMapping("/{uuid}")
    Response<FinanceInfoRes> del(@PathVariable String uuid);

    @ApiOperation(value = "Filter")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FinanceInfoInput.class, responseContainer = "PagedListHolder"),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    @PostMapping("/filter")
    Response<Page<FinanceInfoRes>> filter(@Valid @RequestBody FinanceInfoFilterInput filter);

    @ApiOperation(value = "Filter with Pageable")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = FinanceInfoInput.class, responseContainer = "PagedListHolder"),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = Error.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    @GetMapping("/filter")
    Response<Page<FinanceInfoRes>> filter(
            @ApiParam(
                    name = "sort",
                    type = "String",
                    value = "multi column/field name and value",
                    example = "sort=desc(col1)&sort=asc(col3)",
                    required = false)
            @RequestParam(defaultValue = "desc(id)", required=false) String[] sort,
            @ApiParam(
                    name = "condition",
                    type = "String",
                    value = "multi column/field name and value",
                    example = "condition=condition=equal(col1:1)&condition=greater(col2:abc)",
                    required = false)
            @RequestParam(required=false) String[] condition,
            @ApiParam(
                    name = "page",
                    type = "String",
                    value = "current page number into ",
                    example = "1",
                    required = false)
            @RequestParam(defaultValue = "0", required=false) @Valid @Min(value = 0L, message = "The value must be positive") int page,
            @ApiParam(
                    name = "size",
                    type = "int",
                    value = "page size - number of item per page",
                    example = "50",
                    required = false)
            @RequestParam(defaultValue = "50", required=false) @Valid @Min(value = 0L, message = "The value must be positive") int size);
}