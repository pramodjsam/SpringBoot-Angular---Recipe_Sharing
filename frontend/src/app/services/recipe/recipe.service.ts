import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { BehaviorSubject, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RecipeService {
  private http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080';
  recipeSubject = new BehaviorSubject<any>({
    recipes: [],
    loading: false,
    newRecipe: null,
  });

  constructor() {}

  private getHeaders() {
    const token = localStorage.getItem('jwt');
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  getRecipes() {
    const headers = this.getHeaders();
    return this.http.get<any>(`${this.baseUrl}/api/recipe`, { headers }).pipe(
      tap((recipes) => {
        const currentSubject = this.recipeSubject.value;
        this.recipeSubject.next({ ...currentSubject, recipes });
      })
    );
  }

  createRecipe(recipeData: any) {
    const headers = this.getHeaders();
    return this.http
      .post<any>(`${this.baseUrl}/api/recipe`, recipeData, { headers })
      .pipe(
        tap((newRecipe) => {
          const currentSubject = this.recipeSubject.value;
          this.recipeSubject.next({
            ...currentSubject,
            recipes: [newRecipe, ...currentSubject.recipes],
          });
        })
      );
  }

  updateRecipe(recipeData: any) {
    const headers = this.getHeaders();
    return this.http
      .put<any>(`${this.baseUrl}/api/recipe/${recipeData.id}`, recipeData, {
        headers,
      })
      .pipe(
        tap((updatedRecipe) => {
          const currentSubject = this.recipeSubject.value;
          const updatedRecipes = currentSubject.recipes.map((recipe: any) =>
            recipe.id === updatedRecipe.id ? updatedRecipe : recipe
          );
          this.recipeSubject.next({
            ...currentSubject,
            recipes: updatedRecipes,
          });
        })
      );
  }

  deleteRecipe(id: any) {
    const headers = this.getHeaders();
    return this.http
      .delete<any>(`${this.baseUrl}/api/recipe/${id}`, {
        headers,
      })
      .pipe(
        tap(() => {
          const currentSubject = this.recipeSubject.value;
          const updatedRecipes = currentSubject.recipes.filter(
            (recipe: any) => recipe.id !== id
          );
          this.recipeSubject.next({
            ...currentSubject,
            recipes: updatedRecipes,
          });
        })
      );
  }

  likeRecipe(id: any) {
    const headers = this.getHeaders();
    return this.http
      .post<any>(`${this.baseUrl}/api/recipe/${id}/like`, {
        headers,
      })
      .pipe(
        tap((updatedRecipe) => {
          const currentSubject = this.recipeSubject.value;
          const updatedRecipes = currentSubject.recipes.map((recipe: any) =>
            recipe.id === updatedRecipe.id ? updatedRecipe : recipe
          );
          this.recipeSubject.next({
            ...currentSubject,
            recipes: updatedRecipes,
          });
        })
      );
  }
}
