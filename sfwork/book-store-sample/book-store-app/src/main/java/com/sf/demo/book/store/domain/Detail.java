package com.sf.demo.book.store.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
public class Detail {

    private int id;

    private String author;

    private int pages;

    private String title;

    private Date pubdate;

    private String isbn;

}
