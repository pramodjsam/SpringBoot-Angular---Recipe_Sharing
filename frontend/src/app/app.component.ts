import { Component, inject, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AuthComponent } from './pages/auth/auth.component';
import { FooterComponent } from './pages/footer/footer.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { NavbarComponent } from './pages/navbar/navbar.component';
import { AuthService } from './services/auth/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    NavbarComponent,
    FooterComponent,
    HomePageComponent,
    AuthComponent,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent implements OnInit {
  title = 'recipe-sharing-frontend';
  private authService = inject(AuthService);
  user: any = null;

  ngOnInit() {
    this.authService.getUserProfile().subscribe({
      next: (response) => {
        // console.log('RESPONSE', response);
      },
      error: (error) => console.log(error),
    });
    this.authService.authSubject.subscribe((auth) => {
      this.user = auth.user;
    });
  }
}
