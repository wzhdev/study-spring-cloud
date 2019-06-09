package com.wzh.study.spring.cloud.service;

import com.wzh.study.spring.cloud.domain.BookDetailAuthor;
import com.wzh.study.spring.cloud.domain.BookDetailEntity;
import com.wzh.study.spring.cloud.domain.BookDetailQuery;
import com.wzh.study.spring.cloud.exception.DuplicateIsbnException;
import com.wzh.study.spring.cloud.exception.NotFindException;
import com.wzh.study.spring.cloud.repository.BookDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BookDetailService {

    private Logger logger = LoggerFactory.getLogger(BookDetailService.class);

    @Autowired
    private BookDetailRepository bookDetailRepository;

    public BookDetailEntity findBookDetailById(int id) {
        return bookDetailRepository.findById(id).orElseThrow(() -> new NotFindException(id));
    }

    public Page<BookDetailEntity> findAllBookDetail(int page, int size, BookDetailQuery query) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return bookDetailRepository.findAll(pageRequest);
    }

    @Transactional
    public BookDetailEntity saveBookDetail(BookDetailEntity bookDetail) {
        boolean exists = bookDetailRepository.existsByIsbn(bookDetail.getIsbn());
        if (exists) {
            logger.info("duplicate isbn: {}", bookDetail.getIsbn());
            throw new DuplicateIsbnException("error.BookDetail.isbn.duplicate");
        }
        return bookDetailRepository.save(bookDetail);
    }

    @Transactional
    public BookDetailEntity updateBookDetail(int id, BookDetailEntity bookDetail) {
        boolean exists = bookDetailRepository.existsById(bookDetail.getId());
        if (!exists) {
            logger.info("not exists id: {}", bookDetail.getId());
            throw new NotFindException(bookDetail.getId());
        }
        bookDetail.setId(id);
        return bookDetailRepository.save(bookDetail);
    }
}
