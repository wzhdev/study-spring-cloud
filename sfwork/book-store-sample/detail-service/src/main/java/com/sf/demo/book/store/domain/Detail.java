package com.sf.demo.book.store.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@ApiModel
public class Detail {

    @ApiModelProperty("ID")
    private int id;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("页数")
    private int pages;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("出版日期")
    private Date pubdate;

    @ApiModelProperty("ISBN")
    private String isbn;

}
