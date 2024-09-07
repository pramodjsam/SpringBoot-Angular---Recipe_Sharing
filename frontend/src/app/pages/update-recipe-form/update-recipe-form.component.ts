import { Component, inject, OnInit } from '@angular/core';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatRadioModule } from '@angular/material/radio';
import { RecipeService } from '../../services/recipe/recipe.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-update-recipe-form',
  standalone: true,
  imports: [
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatRadioModule,
  ],
  templateUrl: './update-recipe-form.component.html',
  styleUrl: './update-recipe-form.component.scss',
})
export class UpdateRecipeFormComponent implements OnInit {
  private recipeService = inject(RecipeService);
  private dialogData = inject(MAT_DIALOG_DATA);
  private dialogRef = inject(MatDialogRef);
  recipeItem = {
    title: '',
    description: '',
    foodType: '',
    image: '',
    id: '',
  };

   // Use constructor-based injection
  //  constructor(
  //   private dialogRef: MatDialogRef<UpdateRecipeFormComponent>,
  // ) {}

  ngOnInit() {
    this.recipeItem = {
      ...this.dialogData.recipe,
      foodType: this.dialogData.recipe.vegetarian ? 'veg' : 'non_veg',
    };
  }

  onSubmit() {
    this.recipeService
      .updateRecipe({
        ...this.recipeItem,
        vegetarian: this.recipeItem.foodType === 'veg',
      })
      .subscribe({
        next: () => {
          this.dialogRef.close();
        },
        error: (error) => {
          console.log('ERROR', error);
        },
      });
  }
}
