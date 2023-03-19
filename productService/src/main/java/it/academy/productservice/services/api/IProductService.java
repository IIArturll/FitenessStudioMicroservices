package it.academy.productservice.services.api;

import it.academy.productservice.core.nutrition.dtos.ProductDTO;
import it.academy.productservice.repositories.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.UUID;

public interface IProductService {
    UUID add(ProductDTO product);

    Page<ProductDTO> getPage(Pageable pageable);

    UUID update(UUID uuid, Instant dtUpdate, ProductDTO product);

    ProductEntity find(UUID uuid);
}
