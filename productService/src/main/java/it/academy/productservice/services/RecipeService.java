package it.academy.productservice.services;

import it.academy.productservice.core.exceptions.SingleErrorResponse;
import it.academy.productservice.core.nutrition.dtos.RecipeDTO;
import it.academy.productservice.core.nutrition.dtos.RecipeForCUDTO;
import it.academy.productservice.core.nutrition.mappers.RecipeConverter;
import it.academy.productservice.repositories.api.IRecipeRepository;
import it.academy.productservice.repositories.entity.RecipeEntity;
import it.academy.productservice.services.api.IRecipeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RecipeService implements IRecipeService {

    private final IRecipeRepository repository;
    private final RecipeConverter converter;


    public RecipeService(IRecipeRepository repository, RecipeConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public void add(RecipeForCUDTO recipe) throws SingleErrorResponse {
        RecipeEntity entity = converter.convertToRecipeEntity(recipe);
        entity.setDtCreate(Instant.now());
        entity.setDtUpdate(Instant.now());
        repository.save(entity);
    }

    @Override
    public Page<RecipeDTO> getPage(Pageable pageable) {
        return repository.findAll(pageable).map(converter::convertTORecipeDTO);
    }

    @Override
    public void update(UUID uuid, Instant dtUpdate, RecipeForCUDTO recipe)
            throws SingleErrorResponse {
        RecipeEntity entity = repository.findById(uuid).orElseThrow(() ->
                new SingleErrorResponse("error",
                        "recipe with this id: " + uuid + " not found"));
        if (entity.getDtUpdate().toEpochMilli() != dtUpdate.toEpochMilli()) {
            throw new SingleErrorResponse("err",
                    "recipe already has been update");
        }
        var newEntity = converter.convertToRecipeEntity(recipe);
        entity.setComposition(newEntity.getComposition());
        entity.setTitle(newEntity.getTitle());
        repository.save(entity);
    }


}
