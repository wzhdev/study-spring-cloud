package com.sf.mip.spring.cloud.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@ApiModel("书籍详情")
public class BookDetail {

    @ApiModelProperty(value = "ID")
    private int id;

    @ApiModelProperty(value = "标题")
    @NotBlank(message = "{validation.BookDetail.title.NotBlank}")
    private String title;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "出版日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date pubdate;

    @ApiModelProperty(value = "页数")
    private int pages;

    @ApiModelProperty(value = "ISBN")
    private String isbn;

}
