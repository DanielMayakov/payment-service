package com.iprody.payment.service.app.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDto {

    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private String code;
    private List<FieldErrorDto> errors;

    public ErrorDto() {}

    public ErrorDto(Instant timestamp, int status, String error, String message, String path, String code, List<FieldErrorDto> errors) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.code = code;
        this.errors = errors;
    }

    public static ErrorDto of(int status, String error, String message, String path) {
        return new ErrorDto(Instant.now(), status, error, message, path, null, null);
    }

    public static ErrorDto of(int status, String error, String message, String path, String code) {
        return new ErrorDto(Instant.now(), status, error, message, path, code, null);
    }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public List<FieldErrorDto> getErrors() { return errors; }
    public void setErrors(List<FieldErrorDto> errors) { this.errors = errors; }
}
