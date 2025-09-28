package com.iprody.payment.service.app.exception;

public class FieldErrorDto {
    private String field;         // имя поля
    private String message;       // текст ошибки
    private Object rejectedValue; // отклонённое значение (опционально)

    public FieldErrorDto() {}

    public FieldErrorDto(String field, String message, Object rejectedValue) {
        this.field = field;
        this.message = message;
        this.rejectedValue = rejectedValue;
    }

    public String getField() { return field; }
    public void setField(String field) { this.field = field; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Object getRejectedValue() { return rejectedValue; }
    public void setRejectedValue(Object rejectedValue) { this.rejectedValue = rejectedValue; }
}
