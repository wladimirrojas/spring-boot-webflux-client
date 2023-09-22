package com.example.springboot.webflux.client.app.services;

import com.example.springboot.webflux.client.app.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private WebClient client;

    @Override
    public Flux<Product> findAll() {
        return client.get().accept(APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Product.class);
                //.exchangeToFlux(response -> response.bodyToFlux(Product.class));
    }

    @Override
    public Mono<Product> findById(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return client.get().uri("/{id}", params)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Product.class);
                //.exchangeToMono(response -> response.bodyToMono(Product.class));
    }

    @Override
    public Mono<Product> save(Product product) {
        return client.post()
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .body(fromValue(product))
                .retrieve()
                .bodyToMono(Product.class);
    }

    @Override
    public Mono<Product> update(Product product, String id) {

        return client.put().uri("/{id}", Collections.singletonMap("id", id))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                //.body(fromValue(product))
                .retrieve()
                .bodyToMono(Product.class);
    }

    @Override
    public Mono<Void> delete(String id) {
        return client.delete().uri("/{id}", Collections.singletonMap("id", id))
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<Product> upload(FilePart file, String id) {
        MultipartBodyBuilder parts = new MultipartBodyBuilder();
        parts.asyncPart("file", file.content(), DataBuffer.class).headers(h -> {
            h.setContentDispositionFormData("file", file.filename());
        });
        return client.post()
                .uri("/upload/{id}", Collections.singletonMap("id", id))
                .contentType(MULTIPART_FORM_DATA)
                .syncBody(parts.build())
                .retrieve()
                .bodyToMono(Product.class);
    }
}
