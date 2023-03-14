package it.academy.productservice.services.api;

import it.academy.productservice.core.exceptions.SingleErrorResponse;
import it.academy.productservice.core.nutrition.dtos.ProductDTO;
import it.academy.productservice.repositories.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.UUID;

public interface IProductService {
    void add(ProductDTO product);

    Page<ProductDTO> getPage(Pageable pageable);

    void update(UUID uuid, Instant dtUpdate, ProductDTO product) throws SingleErrorResponse;

    ProductEntity find(UUID uuid) throws SingleErrorResponse;
}
