package it.academy.productservice.core.nutrition.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.academy.productservice.core.converters.DoubleSerializer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductCompositionDTO {
    @NotNull
    private ProductDTO product;
    @NotNull
    @Positive
    private Integer weight;
    @NotNull
    @Positive
    private Integer calories;
    @NotNull
    @Positive
    @JsonSerialize(using = DoubleSerializer.class)
    private Double proteins;
    @NotNull
    @Positive
    @JsonSerialize(using = DoubleSerializer.class)
    private Double fats;
    @NotNull
    @Positive
    @JsonSerialize(using = DoubleSerializer.class)
    private Double carbohydrates;

    public ProductCompositionDTO() {
    }

    public ProductCompositionDTO(ProductDTO product){
        this.product=product;
    }

    public ProductCompositionDTO(ProductDTO product, Integer weight, Integer calories,
                                 Double proteins, Double fats, Double carbohydrates) {
        this.product = product;
        this.weight = weight;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Double getProteins() {
        return proteins;
    }

    public void setProteins(Double proteins) {
        this.proteins = proteins;
    }

    public Double getFats() {
        return fats;
    }

    public void setFats(Double fats) {
        this.fats = fats;
    }

    public Double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(Double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }
}
