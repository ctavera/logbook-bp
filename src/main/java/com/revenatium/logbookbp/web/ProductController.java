package com.revenatium.logbookbp.web;

import com.revenatium.logbookbp.service.ProductService;
import com.revenatium.logbookbp.web.model.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProducts () {
        return new ResponseEntity<>(productService.listProducts(), HttpStatus.OK);
    }

    @GetMapping("/products/{name}")
    public ResponseEntity<ProductDto> getProductByName (@PathVariable String name) {
        return new ResponseEntity<>(productService.getProductByName(name), HttpStatus.OK);
    }
}

