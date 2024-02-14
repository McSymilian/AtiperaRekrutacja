package pl.ryder.githubapiconsumer.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ExceptionResponse (String message) {
}
