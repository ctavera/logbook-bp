package com.revenatium.logbookbp.bootstrap;

import com.revenatium.logbookbp.domain.Product;
import com.revenatium.logbookbp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {
            loadProductsObjects();
        }
    }

    private void loadProductsObjects() {
        Product tv = Product.builder()
                .name("TV")
                .description("Neo QLED Samsung 4k")
                .model("2023")
                .build();

        Product console = Product.builder()
                .name("Console")
                .description("Sony PS5")
                .model("PS5")
                .build();

        Product mac = Product.builder()
                .name("Lap")
                .description("Apple Mac")
                .model("Mac Air")
                .build();

        Product iphone = Product.builder()
                .name("Cellphone")
                .description("Apple Iphone 14")
                .model("Iphone 14")
                .build();

        Product ipad = Product.builder()
                .name("Tablet")
                .description("Apple Ipad")
                .model("Ipad 10 generation")
                .build();

        productRepository.save(tv);
        productRepository.save(console);
        productRepository.save(mac);
        productRepository.save(iphone);
        productRepository.save(ipad);
    }
}
