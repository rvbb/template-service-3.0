package com.rvbb.food.template.controller;

import com.rvbb.food.template.controller.handler.Error;
import com.rvbb.food.template.dto.Response;
import com.rvbb.food.template.dto.adapter.Cif;
import com.rvbb.food.template.service.FinanceInfoService;
import com.rvbb.food.template.service.impl.CifService;
import com.rvbb.food.template.validator.FinanceInfoValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "CIF Services", description = "Loan financial information API")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("cif")
public class CifController {

    private final FinanceInfoService loanFinInfoService;
    private final FinanceInfoValidator loanFinInfoValidator;
    private final  CifService service;
    @Operation(summary = "Retrieve a CIF",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success",
                            content = @Content(schema = @Schema(implementation = Cif.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "404", description = "Not Found Exception",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "409", description = "Conflict Exception",
                            content = @Content(schema = @Schema(implementation = Error.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = Error.class))),
            })
    @GetMapping
    Response<List<Cif>> retrieveCIf(){
        String base = "Users";
        List<Cif> cifList = service.getCif(base);
        return Response.ok(cifList);
    }
}
