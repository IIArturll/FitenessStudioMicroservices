package it.academy.productservice.services;

import it.academy.productservice.audit.annotations.Audit;
import it.academy.productservice.audit.enums.EssenceType;
import it.academy.productservice.core.exceptions.SingleErrorResponse;
import it.academy.productservice.core.nutrition.dtos.ProductDTO;
import it.academy.productservice.core.nutrition.mappers.ProductConverter;
import it.academy.productservice.repositories.api.IProductRepository;
import it.academy.productservice.repositories.entity.ProductEntity;
import it.academy.productservice.services.api.IProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class ProductService implements IProductService {
    private final IProductRepository repository;
    private final ProductConverter converter;

    public ProductService(IProductRepository repository, ProductConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    @Audit(message = "create", essence = EssenceType.PRODUCT)
    public UUID add(ProductDTO product) {
        ProductEntity productEntity = converter.convertToEntity(product);
        productEntity.setUuid(UUID.randomUUID());
        productEntity.setDtCreate(Instant.now());
        productEntity.setDtUpdate(Instant.now());
        repository.save(productEntity);
        return productEntity.getUuid();
    }

    @Override
    public Page<ProductDTO> getPage(Pageable pageable) {
        return repository.findAll(pageable).map(converter::convertToProductDTO);
    }

    @Override
    @Audit(message = "update", essence = EssenceType.PRODUCT)
    public UUID update(UUID uuid, Instant dtUpdate, ProductDTO product) {
        ProductEntity productEntity = repository.findById(uuid).orElseThrow(() ->
                new SingleErrorResponse("error", "there is no product with this id : " + uuid));
        if (productEntity.getDtUpdate().toEpochMilli() != dtUpdate.toEpochMilli()) {
            throw new SingleErrorResponse("error", "product already has been update");
        }
        productEntity.setTitle(product.getTitle());
        productEntity.setWeight(product.getWeight());
        productEntity.setCalories(product.getCalories());
        productEntity.setProteins(product.getProteins());
        productEntity.setFats(product.getFats());
        productEntity.setCarbohydrates(product.getCarbohydrates());
        repository.save(productEntity);
        return uuid;
    }

    @Override
    public ProductEntity find(UUID uuid) {
        return repository.findById(uuid).orElseThrow(() ->
                new SingleErrorResponse("error", "there is no product with this id : " + uuid));
    }
}
