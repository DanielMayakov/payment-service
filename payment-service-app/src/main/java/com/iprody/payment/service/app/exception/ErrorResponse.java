package com.iprody.payment.service.app.exception;

import java.time.Instant;
import java.util.UUID;

public class ErrorResponse {
    private final String error;
    private final Instant timestamp;
    private final String operation;
    private final UUID entityId;
    public ErrorResponse(String error, String operation, UUID entityId)
    {
        this.error = error;
        this.timestamp = Instant.now();
        this.operation = operation;
        this.entityId = entityId;
    }
    public String getError() {
        return error;
    }
    public Instant getTimestamp() {
        return timestamp;
    }
    public String getOperation() {
        return operation;
    }
    public UUID getEntityId() {
        return entityId;
    }
}