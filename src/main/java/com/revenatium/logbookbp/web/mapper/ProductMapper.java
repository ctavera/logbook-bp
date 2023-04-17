package com.revenatium.logbookbp.web.mapper;

import com.revenatium.logbookbp.domain.Product;
import com.revenatium.logbookbp.web.model.ProductDto;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    Product productDtoTProduct (ProductDto productDto);

    ProductDto productToProductDto (Product product);
}
