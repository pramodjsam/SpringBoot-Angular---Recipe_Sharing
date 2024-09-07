package com.zosh.controller;

import com.zosh.model.Recipe;
import com.zosh.model.User;
import com.zosh.service.RecipeService;
import com.zosh.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    private final RecipeService recipeService;
    private final UserService userService;

    public RecipeController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity createRecipe(@RequestBody Recipe recipe, @RequestHeader("Authorization") String jwt) {
        try {
            User user = this.userService.findUserByJwt(jwt);

            Recipe createdRecipe = this.recipeService.createRecipe(recipe, user);
            return ResponseEntity.ok(createdRecipe);
        } catch (Exception ex) {
            return new ResponseEntity<String>("User Not Found", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipe() {
        return new ResponseEntity<>(this.recipeService.findAllRecipes(), HttpStatus.OK);
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long recipeId) {
        try {
            this.recipeService.deleteRecipeById(recipeId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{recipeId}")
    public ResponseEntity updateRecipe(@RequestBody Recipe recipe, @PathVariable Long recipeId) {
        try {
            return new ResponseEntity<Recipe>(this.recipeService.updateRecipe(recipe, recipeId), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{recipeId}/like")
    public ResponseEntity likeRecipe(@PathVariable Long recipeId, @RequestHeader("Authorization") String jwt) {
        try {
            User user = this.userService.findUserByJwt(jwt);

            return new ResponseEntity<Recipe>(this.recipeService.likeRecipe(recipeId, user), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
