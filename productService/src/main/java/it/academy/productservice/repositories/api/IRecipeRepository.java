package it.academy.productservice.repositories.api;

import it.academy.productservice.repositories.entity.RecipeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IRecipeRepository extends CrudRepository<RecipeEntity, UUID>,
        PagingAndSortingRepository<RecipeEntity, UUID> {
}
