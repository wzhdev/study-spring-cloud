package com.wzh.study.spring.cloud.controller;

import javax.validation.constraints.Positive;

import com.wzh.study.spring.cloud.domain.BookDetailEntity;
import com.wzh.study.spring.cloud.domain.BookDetailQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.wzh.study.spring.cloud.service.BookDetailService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController @RequestMapping("/api/v1") @Validated public class BookDetailController {

    private Logger logger = LoggerFactory.getLogger(BookDetailController.class);

    @Autowired private BookDetailService bookDetailService;

    @ApiOperation(value = "获取书籍详情", produces = "application/json") @GetMapping("/bookDetails/{id}")
    public BookDetailEntity getBookDetailById(@Positive(message = "{validation.BookDetailEntity.id.Positive}")
    @ApiParam(value = "书籍ID", type = "int", example = "1") @PathVariable("id") int id) {
        logger.info("id: {}", id);
        BookDetailEntity bookDetail = bookDetailService.findBookDetailById(id);
        logger.info("BookDetailEntity: {}", bookDetail);
        return bookDetail;
    }

    @ApiOperation(value = "查询书籍详情", produces = "application/json") @GetMapping("/bookDetails")
    public Page<BookDetailEntity> listAllBookDetail(int page, int size, BookDetailQuery query) {
        return bookDetailService.findAllBookDetail(page, size, query);
    }

    @ApiOperation(value = "新增书籍详情", produces = "application/json") @PostMapping("/bookDetails")
    public void saveBookDetail(@RequestBody BookDetailEntity bookDetail) {
        logger.info("BookDetailEntity: {}", bookDetail);
        bookDetailService.saveBookDetail(bookDetail);
        logger.info("id: {}", bookDetail.getId());
    }

    @ApiOperation(value = "更新书籍详情", produces = "application/json") @PutMapping("/bookDetails/{id}")
    public void updateBookDetail(@Positive(message = "{validation.BookDetailEntity.id.Positive}")
    @ApiParam(value = "书籍ID", type = "int", example = "1") @PathVariable("id") int id,
        @RequestBody BookDetailEntity bookDetail) {
        logger.info("BookDetailEntity: {}", bookDetail);
        bookDetailService.updateBookDetail(id, bookDetail);
    }

}
