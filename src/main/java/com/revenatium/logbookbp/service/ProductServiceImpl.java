package com.revenatium.logbookbp.service;

import com.revenatium.logbookbp.domain.Product;
import com.revenatium.logbookbp.repository.ProductRepository;
import com.revenatium.logbookbp.web.mapper.ProductMapper;
import com.revenatium.logbookbp.web.model.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDto> listProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::productToProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductByName(String name) {
        return productMapper.productToProductDto(productRepository.findByName(name).orElse(Product.builder().build()));
    }
}
