package com.wzh.study.spring.cloud.controller;

import com.wzh.study.spring.cloud.domain.BookDetail;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class BookDetailController {

    private Logger logger = LoggerFactory.getLogger(BookDetailController.class);

    @GetMapping("/bookDetail/{id}")
    @ApiOperation("get Book Detail by id")
    public ResponseEntity<BookDetail> getBookDetailById(@ApiParam(value = "the book id", type = "int", example = "1") @PathVariable("id") int id) {
        logger.info("id: {}", id);
        BookDetail bd = new BookDetail();
        bd.setId(id);
        bd.setAuthor("Wang xiaowu");
        bd.setTitle("Hello World");
        bd.setPubdate(new Date());
        logger.info("BookDetail: {}", bd);
        return new ResponseEntity<>(bd, HttpStatus.OK);
    }
}
