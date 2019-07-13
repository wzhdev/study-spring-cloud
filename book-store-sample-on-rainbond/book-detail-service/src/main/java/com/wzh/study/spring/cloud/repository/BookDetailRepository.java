package com.wzh.study.spring.cloud.repository;

import com.wzh.study.spring.cloud.domain.BookDetailEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookDetailRepository extends PagingAndSortingRepository<BookDetailEntity, Integer> {

    boolean existsByIsbn(String isbn);

}
