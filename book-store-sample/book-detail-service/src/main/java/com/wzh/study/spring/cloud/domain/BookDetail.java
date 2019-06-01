package com.wzh.study.spring.cloud.domain;

import lombok.Data;

import java.util.Date;

@Data
public class BookDetail {

    private int id;

    private String title;

    private String author;

    private Date pubdate;

}
