package com.sf.mip.spring.cloud.demo.repository;

import com.sf.mip.spring.cloud.demo.domain.BookDetailEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookDetailRepository extends PagingAndSortingRepository<BookDetailEntity, Integer> {

    boolean existsByIsbn(String isbn);

}
