package com.mark.productservice.service;

import com.mark.productservice.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class MongoDataSetupService implements CommandLineRunner {

    private final ProductService service;

    @Override
    public void run(String... args) throws Exception {
        ProductDto p1 = new ProductDto("Galaxy S24 Ultra", 1000);
        ProductDto p2 = new ProductDto("4k-tv", 1000);
        ProductDto p3 = new ProductDto("slr-camera", 750);
        ProductDto p4 = new ProductDto("I-Phone", 950);

        Flux.just(p1, p2, p3, p4)
                .concatWith(newProducts())
                .flatMap(p -> this.service.insertProduct(Mono.just(p)))
                .subscribe(System.out::println);
    }

    private Flux<ProductDto> newProducts() {
        return Flux.range(1, 1000)
                .delayElements(Duration.ofSeconds(2))
                .map(i -> new ProductDto("product-" + i, ThreadLocalRandom.current().nextInt(10, 100)));
    }
}
