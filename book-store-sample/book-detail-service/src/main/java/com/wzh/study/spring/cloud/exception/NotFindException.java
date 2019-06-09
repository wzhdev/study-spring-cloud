package com.wzh.study.spring.cloud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "error.BookDetail.notFound")
public class NotFindException  extends BusinessException  {

    public NotFindException(int id) {
        super("not find book detail id " + id);
    }
}
