package com.example.springboot.webflux.client.app.services;

import com.example.springboot.webflux.client.app.models.Product;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    public Flux<Product> findAll();

    public Mono<Product> findById(String id);

    public Mono<Product> save(Product product);

    public Mono<Product> update(Product product, String id);

    public Mono<Void> delete(String id);

    public Mono<Product> upload(FilePart file, String id);
}
