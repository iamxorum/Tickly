package com.xrm.tickly.ticketing_app.dto;

import lombok.Builder;
import lombok.Data;
import java.util.Map;

@Data
@Builder
public class ValidationResult {
    private boolean valid;
    private Map<String, String> errors;
}