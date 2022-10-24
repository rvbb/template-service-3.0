package com.rvbb.food.template.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "test", description = "Test API")
@RestController
@RequestMapping(path = "/test")
public class TestRest {

    private String test = "Test";

    @Operation(summary = "Get test string", description = "Returns a test string", tags = { "test" })
//        @ApiResponses(res = "200", description = "Success")
    @GetMapping(value = "", produces = MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody
    String getTest() {
        return test;
    }

}
