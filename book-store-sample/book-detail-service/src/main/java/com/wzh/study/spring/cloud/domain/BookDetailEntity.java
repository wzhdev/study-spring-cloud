package com.wzh.study.spring.cloud.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("书籍详情")
@Entity
@Table(name = "book_detail", indexes = {@Index(name = "book_detail_isbn", columnList = "isbn", unique = true)})
public class BookDetailEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @ApiModelProperty(value = "ID")
    private int id;

    @ApiModelProperty(value = "标题")
    @Column(length = 126)
    @NotBlank(message = "{validation.BookDetailEntity.title.NotBlank}")
    private String title;

    @ApiModelProperty(value = "作者")
    @Column(length = 32)
    private String author;

    @ApiModelProperty(value = "出版日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date pubdate;

    @ApiModelProperty(value = "页数")
    private int pages;

    @ApiModelProperty(value = "ISBN")
    @Column(length = 13)
    private String isbn;

}
