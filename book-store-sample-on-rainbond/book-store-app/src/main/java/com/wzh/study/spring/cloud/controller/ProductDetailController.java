package com.wzh.study.spring.cloud.controller;

import com.wzh.study.spring.cloud.client.BookDetailClient;
import com.wzh.study.spring.cloud.domain.BookDetail;
import com.wzh.study.spring.cloud.domain.ProductDetail;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class ProductDetailController {

    private Logger logger = LoggerFactory.getLogger(ProductDetailController.class);

    @Autowired
    private BookDetailClient bookDetailClient;

    @Autowired
    private RedisTemplate<Object, Object>  redisTemplate;

    private final static String PRODUCT_DETAIL_KEY_PREFIX = "product_detail_";

    @GetMapping("/productDetails/{id}")
    @ApiOperation(value = "按产品ID获取产品详情",  produces = "application/json")
    public ResponseEntity<ProductDetail> getProductDetailById(@ApiParam(value = "产品ID", type = "int", example = "1") @PathVariable("id") int id) {
        logger.info("id: {}", id);

        ProductDetail pd  = (ProductDetail)redisTemplate.opsForValue().get(PRODUCT_DETAIL_KEY_PREFIX + id);
        if (pd != null) {
            logger.info("ProductDetail from redis: {}", pd);
            return new ResponseEntity<>(pd, HttpStatus.OK);
        }

        BookDetail bd = bookDetailClient.getBookDetailById(id).getBody();
        logger.info("BookDetail: {}", bd);
        pd = new ProductDetail();
        pd.setId(bd.getId());
        pd.setBookDetail(bd);
        pd.setIssuedDate(new Date());
        pd.setStars(3);

        redisTemplate.opsForValue().set(PRODUCT_DETAIL_KEY_PREFIX + id, pd);

        logger.info("ProductDetail: {}", pd);
        return new ResponseEntity<>(pd, HttpStatus.OK);
    }
}
