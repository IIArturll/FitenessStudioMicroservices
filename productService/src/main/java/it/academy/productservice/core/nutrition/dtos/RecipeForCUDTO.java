package it.academy.productservice.core.nutrition.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class RecipeForCUDTO {
    @NotBlank
    private String title;
    @NotNull
    @Size(min = 1)
    List<ProductModelDTO> composition;

    public RecipeForCUDTO() {
    }

    public RecipeForCUDTO(String title, List<ProductModelDTO> composition) {
        this.title = title;
        this.composition = composition;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ProductModelDTO> getComposition() {
        return composition;
    }

    public void setComposition(List<ProductModelDTO> composition) {
        this.composition = composition;
    }
}
