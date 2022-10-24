package com.rvbb.food.template.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rvbb.food.template.controller.handler.Error;
import com.rvbb.food.template.dto.Response;
import com.rvbb.food.template.dto.financeinfo.FinanceInfoFilterInput;
import com.rvbb.food.template.dto.financeinfo.FinanceInfoInput;
import com.rvbb.food.template.dto.financeinfo.FinanceInfoRes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Tag(name = "crud", description = "Loan financial information API")
@RestController
@RequestMapping("finance")
public interface FinanceInfoController {
    @Operation(summary = "Create",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success",
                            content = @Content(schema = @Schema(implementation = FinanceInfoInput.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found Exception",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "409", description = "Conflict Exception",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = Error.class))),
            })
    @PostMapping
    Response<FinanceInfoRes> create(@Valid @RequestBody FinanceInfoInput request);

    @Operation(description = "Get last",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success",
                            content = @Content(schema = @Schema(implementation = FinanceInfoRes.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found Exception",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "409", description = "Conflict Exception",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = Error.class)))
            })
    @GetMapping("/last")
    Response<FinanceInfoRes> getLast();

    @Operation(description = "Update",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success",
                            content = @Content(schema = @Schema(implementation = FinanceInfoRes.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found Exception",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "409", description = "Conflict Exception",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = Error.class)))
            })
    @PutMapping("/{uuid}")
    Response<FinanceInfoRes> update(@PathVariable String uuid, @Valid @RequestBody FinanceInfoInput request);

    @Operation(description = "Read One",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success",
                            content = @Content(schema = @Schema(implementation = FinanceInfoRes.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found Exception",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "409", description = "Conflict Exception",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = Error.class)))
            })
    @GetMapping("/{uuid}")
    Response<FinanceInfoRes> get(@PathVariable String uuid);

    @Operation(description = "List",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success",
                            content = @Content(array = @ArraySchema(uniqueItems = false, schema = @Schema(implementation = FinanceInfoInput.class)))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found Exception",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "409", description = "Conflict Exception",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = Error.class)))
            })
    @GetMapping("/list")
    Response<List<FinanceInfoRes>> list();

    @Operation(description = "Delete One",
    responses = {
        @ApiResponse(responseCode = "200", description = "Success",
                content = @Content(schema = @Schema(implementation = FinanceInfoInput.class))),
        @ApiResponse(responseCode = "400", description = "Bad request",
                content = @Content(schema = @Schema(implementation = Error.class))),
        @ApiResponse(responseCode = "404", description = "Not Found Exception",
                content = @Content(schema = @Schema(implementation = Error.class))),
        @ApiResponse(responseCode = "409", description = "Conflict Exception",
                content = @Content(schema = @Schema(implementation = Error.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/{uuid}")
    Response<FinanceInfoRes> del(@PathVariable String uuid);

    @Operation(description = "Filter",responses = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(schema = @Schema(implementation = FinanceInfoInput.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "Not Found Exception",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "409", description = "Conflict Exception",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/filter")
    Response<Page<FinanceInfoRes>> filter(@Valid @RequestBody FinanceInfoFilterInput filter);

    @Operation(description = "Filter with Pageable",responses = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(array = @ArraySchema(uniqueItems = false, schema = @Schema(implementation = FinanceInfoRes.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "Not Found Exception",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "409", description = "Conflict Exception",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/filter")
    Response<Page<FinanceInfoRes>> filter(
            @Parameter(
                    name = "sort",
                    description = "multi column/field name and value",
                    example = "sort=desc(col1)&sort=asc(col3)",
                    required = false)
            @RequestParam(defaultValue = "desc(id)", required = false) String[] sort,
            @Parameter(
                    name = "condition",
                    description = "multi column/field name and value",
                    example = "condition=condition=equal(col1:1)&condition=greater(col2:abc)",
                    required = false)
            @RequestParam(required = false) String[] condition,
            @Parameter(
                    name = "page",
                    description = "current page number into ",
                    example = "1",
                    required = false)
            @RequestParam(defaultValue = "0", required = false) @Valid @Min(value = 0L,
                    message = "The value must be positive") int page,
            @Parameter(
                    name = "size",
                    description = "page size - number of item per page",
                    example = "50",
                    required = false)
            @RequestParam(defaultValue = "50", required = false) @Valid @Min(value = 0L,
                    message = "The value must be positive") int size);
}
