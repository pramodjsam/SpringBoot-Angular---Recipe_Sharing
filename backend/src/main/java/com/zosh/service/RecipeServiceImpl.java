package com.zosh.service;

import com.zosh.model.Recipe;
import com.zosh.model.User;
import com.zosh.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Recipe createRecipe(Recipe recipe, User user) {
        Recipe createdRecipe = new Recipe();

        createdRecipe.setTitle(recipe.getTitle());
        createdRecipe.setDescription(recipe.getDescription());
        createdRecipe.setImage(recipe.getImage());
        createdRecipe.setUser(user);
        createdRecipe.setVegetarian(recipe.isVegetarian());
        createdRecipe.setCreatedAt(LocalDateTime.now());

        return this.recipeRepository.save(createdRecipe);
    }

    @Override
    public Recipe findRecipeById(Long id) throws Exception {
        Optional<Recipe> optionalRecipe = this.recipeRepository.findById(id);

        if (optionalRecipe.isPresent()) {
            return optionalRecipe.get();
        }

        throw new Exception("Recipe not exists with id: " + id);
    }

    @Override
    public void deleteRecipeById(Long id) throws Exception {
        Optional<Recipe> optionalRecipe = this.recipeRepository.findById(id);

        if(optionalRecipe.isPresent()) {
            this.recipeRepository.delete(optionalRecipe.get());
        }

        throw new Exception("Recipe not exists with id: " + id);
    }

    @Override
    public Recipe updateRecipe(Recipe recipe, Long id) throws Exception {
        Recipe oldRecipe = this.findRecipeById(id);

        if(recipe.getTitle() != null){
            oldRecipe.setTitle(recipe.getTitle());
        }
        if(recipe.getImage() != null){
            oldRecipe.setImage(recipe.getImage());
        }
        if(recipe.getDescription() != null){
            oldRecipe.setDescription(recipe.getDescription());
        }
        oldRecipe.setVegetarian(recipe.isVegetarian());

        return this.recipeRepository.save(oldRecipe);
    }

    @Override
    public List<Recipe> findAllRecipes() {
        return this.recipeRepository.findAll();
    }

    @Override
    public Recipe likeRecipe(Long id, User user) throws Exception {
        Recipe recipe = findRecipeById(id);

        if(recipe.getLikes().contains(user)){
            recipe.getLikes().remove(user);
        }else{
            recipe.getLikes().add(user);
        }

        return this.recipeRepository.save(recipe);
    }
}
