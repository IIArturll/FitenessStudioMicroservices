package it.academy.productservice.core.nutrition.dtos;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public class ProductModelDTO {
    @NotNull
    private UUID product;
    @NotNull
    @Positive
    private Integer weight;

    public ProductModelDTO() {
    }

    public ProductModelDTO(UUID product, Integer weight) {
        this.product = product;
        this.weight = weight;
    }

    public UUID getProduct() {
        return product;
    }

    public Integer getWeight() {
        return weight;
    }
}
