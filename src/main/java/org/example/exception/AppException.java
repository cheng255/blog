package org.example.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author nuonuo
 * @create 2021-01-03 14:40
 */
@Setter
@Getter
public class AppException extends RuntimeException {
    String code;//错误码
    public AppException(String code, String message) {
        this(code, message, null);
    }
    public AppException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
