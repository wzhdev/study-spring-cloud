package com.sf.demo.book.store.client;

import com.sf.demo.book.store.domain.Detail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "detail-service", path = "/detail-service/api/v1")
public interface DetailClient {

    @GetMapping("/detail/{id}")
    ResponseEntity<Detail> getDetailById(@PathVariable("id") int id);

    @GetMapping("/details")
    ResponseEntity<List<Detail>> getAllDetails();

}
