package it.academy.productservice.services.api;

import it.academy.productservice.core.nutrition.dtos.RecipeDTO;
import it.academy.productservice.core.nutrition.dtos.RecipeForCUDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.UUID;

public interface IRecipeService {
    UUID add(RecipeForCUDTO recipe);

    Page<RecipeDTO> getPage(Pageable pageable);

    UUID update(UUID uuid, Instant dtUpdate, RecipeForCUDTO recipe);
}
