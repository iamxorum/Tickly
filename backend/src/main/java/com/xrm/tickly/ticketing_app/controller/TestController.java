package com.xrm.tickly.ticketing_app.controller;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/test")
@Tag(name = "Test", description = "APIs for testing purposes")
public class TestController {

    @GetMapping
    @Operation(summary = "Test endpoint", description = "A simple test endpoint to check the API")
    public String testEndpoint() {
        return "Test endpoint is working!";
    }
}