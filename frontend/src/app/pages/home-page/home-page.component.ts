import { Component, inject, OnInit } from '@angular/core';
import { RecipeCardComponent } from '../recipe-card/recipe-card.component';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { CreateRecipeFormComponent } from '../create-recipe-form/create-recipe-form.component';
import { AuthService } from '../../services/auth/auth.service';
import { RecipeService } from '../../services/recipe/recipe.service';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [RecipeCardComponent, MatIconModule, MatButtonModule],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.scss',
})
export class HomePageComponent implements OnInit{
  private authService = inject(AuthService);
  private recipeService = inject(RecipeService);
  readonly dialog = inject(MatDialog);
  recipes = [1, 1, 1, 1, 1, 1];

  ngOnInit(){
    this.recipeService.getRecipes().subscribe()
    this.recipeService.recipeSubject.subscribe((state) => this.recipes = state.recipes)
  }

  handleOpenCreateRecipeForm() {
    this.dialog.open(CreateRecipeFormComponent);
  }
}
