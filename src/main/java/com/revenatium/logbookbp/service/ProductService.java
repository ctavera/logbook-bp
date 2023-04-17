package com.revenatium.logbookbp.service;

import com.revenatium.logbookbp.web.model.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> listProducts();

    ProductDto getProductByName(String name);
}
