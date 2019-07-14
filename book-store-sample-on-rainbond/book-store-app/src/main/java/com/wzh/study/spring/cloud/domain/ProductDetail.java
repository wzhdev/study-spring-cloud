package com.wzh.study.spring.cloud.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("产品详情")
public class ProductDetail implements Serializable {

    @ApiModelProperty(value = "ID")
    private int id;

    @ApiModelProperty(value = "书籍详情")
    private BookDetail bookDetail;

    @ApiModelProperty(value = "标星数")
    private int stars;

    @ApiModelProperty(value = "发售日期")
    private Date issuedDate;

}
