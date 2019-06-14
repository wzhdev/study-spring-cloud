package com.sf.demo.book.store.domain;

import lombok.Data;

@Data
public class Product {

    private int id;

    private Detail detail;

    private int stars;

}
