package com.wzh.study.spring.cloud.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("书籍详情")
public class BookDetail  implements Serializable {

    @ApiModelProperty(value = "ID")
    private int id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "出版日期")
    private Date pubdate;

}
