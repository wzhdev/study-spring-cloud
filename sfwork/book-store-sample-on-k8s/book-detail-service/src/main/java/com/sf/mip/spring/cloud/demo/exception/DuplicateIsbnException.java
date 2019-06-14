package com.sf.mip.spring.cloud.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "error.BookDetail.isbn.duplicate")
public class DuplicateIsbnException extends BusinessException {

    private static final long serialVersionUID = 2856863872120655787L;

    public DuplicateIsbnException(String isbn) {
        super("duplicate isbm: " + isbn);
    }
}
