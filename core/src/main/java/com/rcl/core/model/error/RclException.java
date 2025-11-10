package com.rcl.core.model.error;

import com.rcl.core.util.CoreConstants;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
public class RclException extends RuntimeException {
    private String message;

    public RclException() {
        super(CoreConstants.UNKNOWN_EXCEPTION);
    }

    public RclException(String message) {
        super(message);
        this.message = message;
    }

    public RclException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public RclException(Throwable cause) {
        super(cause);
        this.message = cause.getMessage();
    }

    @Override
    public String toString() {
        return this.message;
    }
}
