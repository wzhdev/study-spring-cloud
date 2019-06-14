package com.sf.mip.spring.cloud.demo.client;

import com.sf.mip.spring.cloud.demo.domain.BookDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "book-detail-service")
public interface BookDetailClient {

    @GetMapping("/book-detail-service/api/v1/bookDetails/{id}")
    ResponseEntity<BookDetail> getBookDetailById(@PathVariable("id") int id);
}
