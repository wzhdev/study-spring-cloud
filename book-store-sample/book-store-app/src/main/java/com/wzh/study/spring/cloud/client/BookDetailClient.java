package com.wzh.study.spring.cloud.client;

import com.wzh.study.spring.cloud.domain.BookDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "book-detail-service")
public interface BookDetailClient {

    @GetMapping("/book-detail-service/api/v1/bookDetail/{id}")
    ResponseEntity<BookDetail> getBookDetailById(@PathVariable("id") int id);
}
