package com.wzh.study.spring.cloud.exception;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -2949369401299366160L;
    
    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
