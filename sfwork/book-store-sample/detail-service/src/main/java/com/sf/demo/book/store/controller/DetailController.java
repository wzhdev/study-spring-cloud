package com.sf.demo.book.store.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.sf.demo.book.store.domain.Detail;
import com.sf.demo.book.store.mapper.DetailMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DetailController {

    Logger logger = LoggerFactory.getLogger(DetailController.class);

    //@Autowired
    private DetailMapper detailMapper;

    @GetMapping("/detail/{id}")
    @ApiOperation(value = "获取详情", notes = "按ID获取详情", produces = "application/json")
    @SentinelResource(value = "detail/id",fallback = "fallback", blockHandler = "detailExceptionHandler")
    public ResponseEntity<Detail> getDetailById(@ApiParam(name = "id", value = "ID", required = true, example = "1") @PathVariable("id") int id) {
        logger.info("detail id: {}", id);
        //Detail detail = detailMapper.findById(id);
        Detail detail = new Detail();
        detail.setId(1);
        detail.setTitle("Hello The World");
        detail.setAuthor("William Shakespeare");
        detail.setIsbn("1234567890");
        detail.setPages(210);
        detail.setPubdate(new Date());
        logger.info("detail: {}", detail);
        return new ResponseEntity<>(detail, HttpStatus.OK);
    }

    @GetMapping("/details")
    @ApiOperation(value = "获取详情列表", notes = "获取详情列表", produces = "application/json")
    @SentinelResource(value = "details", blockHandler = "detailsExceptionHandler")
    public ResponseEntity<List<Detail>> getAllDetails() {
        //Detail detail = detailMapper.findById(id);
        List<Detail> details = new ArrayList<>();
        Detail detail = new Detail();
        detail.setId(1);
        detail.setTitle("Hello The World");
        detail.setAuthor("William Shakespeare");
        detail.setIsbn("1234567890");
        detail.setPages(200);
        detail.setPubdate(new Date());
        details.add(detail);

        detail = new Detail();
        detail.setId(2);
        detail.setTitle("白银世界");
        detail.setAuthor("王小波");
        detail.setIsbn("1234567890");
        detail.setPages(240);
        detail.setPubdate(new Date());
        details.add(detail);

        return new ResponseEntity<List<Detail>>(details, HttpStatus.OK);
    }

    public ResponseEntity<Detail> fallback(int id) {
        Detail detail = new Detail();
        detail.setTitle("From fallback!");
        return new ResponseEntity<Detail>(detail, HttpStatus.OK);
    }

    public ResponseEntity<Detail> detailExceptionHandler(int id, BlockException exception) {
        Detail detail = new Detail();
        detail.setTitle("获取详情的动作过快!");
        return new ResponseEntity<Detail>(detail, HttpStatus.OK);
    }
    public ResponseEntity<Detail> detailsExceptionHandler(int id, BlockException exception) {
        Detail detail = new Detail();
        detail.setTitle("获取详情列表的动作过快!");
        return new ResponseEntity<Detail>(detail, HttpStatus.OK);
    }

}
