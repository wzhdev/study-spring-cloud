package com.sf.mip.spring.cloud.demo.service;

import com.sf.mip.spring.cloud.demo.exception.NotFindException;
import com.sf.mip.spring.cloud.demo.domain.BookDetailEntity;
import com.sf.mip.spring.cloud.demo.domain.BookDetailQuery;
import com.sf.mip.spring.cloud.demo.exception.DuplicateIsbnException;
import com.sf.mip.spring.cloud.demo.repository.BookDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
