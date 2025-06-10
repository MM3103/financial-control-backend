package app.core.errorhandling.model;

import lombok.Builder;

@Builder
public record CommonException(String msg, String cause) {
}
