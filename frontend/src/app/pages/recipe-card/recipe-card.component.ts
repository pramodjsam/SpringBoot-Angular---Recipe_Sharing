import { Component, inject, Input } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { RecipeService } from '../../services/recipe/recipe.service';
import { UpdateRecipeFormComponent } from '../update-recipe-form/update-recipe-form.component';

@Component({
  selector: 'app-recipe-card',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, MatIconModule],
  templateUrl: './recipe-card.component.html',
  styleUrl: './recipe-card.component.scss',
})
export class RecipeCardComponent {
  private recipeService = inject(RecipeService);
  readonly dialog = inject(MatDialog);
  @Input() recipe: any;

  handleOpenEditRecipeForm() {
    this.dialog.open(UpdateRecipeFormComponent, {
      data: {
        recipe: this.recipe,
      },
    });
  }

  handleDeleteRecipe() {
    this.recipeService.deleteRecipe(this.recipe.id).subscribe({
      next: (data) => {
        console.log('DATA', data);
      },
      error: (error) => {
        console.log('ERROR', error);
      },
    });
  }
}
