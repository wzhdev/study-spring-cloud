package com.wzh.study.spring.cloud.client;

import com.wzh.study.spring.cloud.domain.BookDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-detail-service", url = "http://${BOOK_DETAIL_SERVICE_HOST}:${BOOK_DETAIL_SERVICE_PORT}")
public interface BookDetailClient {

    @GetMapping("/api/v1/bookDetails/{id}")
    ResponseEntity<BookDetail> getBookDetailById(@PathVariable("id") int id);
}
