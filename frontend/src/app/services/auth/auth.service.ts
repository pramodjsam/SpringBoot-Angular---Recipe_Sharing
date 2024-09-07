import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly baseUrl = 'http://localhost:8080';
  private http = inject(HttpClient);

  constructor() {}

  authSubject = new BehaviorSubject({
    user: null,
  });

  login(userData: any) {
    return this.http.post(`${this.baseUrl}/auth/login`, userData);
  }

  register(userData: any) {
    return this.http.post(`${this.baseUrl}/auth/signup`, userData);
  }

  getUserProfile() {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('jwt')}`,
    });
    return this.http
      .get<any>(`${this.baseUrl}/api/users/profile`, { headers })
      .pipe(
        tap((user) => {
          const currentState = this.authSubject.value;
          this.authSubject.next({ ...currentState, user });
        })
      );
  }

  logout() {
    localStorage.clear();
    this.authSubject.next({ user: null });
  }
}
