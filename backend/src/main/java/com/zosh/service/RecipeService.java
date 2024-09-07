package com.zosh.service;

import com.zosh.model.Recipe;
import com.zosh.model.User;

import java.util.List;

public interface RecipeService {
    Recipe createRecipe(Recipe recipe, User user);
    Recipe findRecipeById(Long id) throws Exception;
    void deleteRecipeById(Long id) throws Exception;
    Recipe updateRecipe(Recipe recipe, Long id) throws Exception;
    List<Recipe> findAllRecipes();
    Recipe likeRecipe(Long id, User user) throws Exception;
}
