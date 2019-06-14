package com.sf.demo.book.store.controller;

import com.sf.demo.book.store.client.DetailClient;
import com.sf.demo.book.store.domain.Detail;
import com.sf.demo.book.store.domain.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductPageController {

    Logger logger = LoggerFactory.getLogger(ProductPageController.class);

    @Autowired
    private DetailClient detailClient;

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int id) {
        logger.info("product id: {}", id);
        Detail detail = detailClient.getDetailById(id).getBody();
        Product product = new Product();
        product.setId(detail.getId());
        product.setDetail(detail);
        product.setStars(4);
        logger.info("product: {}", product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllDetails() {
        List<Detail> details = detailClient.getAllDetails().getBody();
        List<Product> products = new ArrayList<>();
        for (Detail detail : details) {
            Product product = new Product();
            product.setId(detail.getId());
            product.setDetail(detail);
            product.setStars(4);
            products.add(product);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
