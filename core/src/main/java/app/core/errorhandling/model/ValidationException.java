package app.core.errorhandling.model;

import lombok.Builder;

import java.util.Map;

@Builder
public record ValidationException(Map<String, String> errors, String msg) {
}
