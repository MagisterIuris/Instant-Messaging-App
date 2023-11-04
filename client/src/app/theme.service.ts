import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ThemeService {
  public readonly DARK_THEME = 'dark-theme';
  public readonly LIGHT_THEME = 'light-theme';
  private currentTheme!: string;

  constructor() {
    if (!localStorage.getItem('app-theme')) {
      localStorage.setItem('app-theme', this.LIGHT_THEME);
    }
    
    this.loadTheme();
  }
  

  loadTheme() {
    this.currentTheme = localStorage.getItem('app-theme') || this.LIGHT_THEME;
    this.applyTheme();
  }

  setDarkTheme() {
    this.currentTheme = this.DARK_THEME;
    this.applyTheme();
    localStorage.setItem('app-theme', this.DARK_THEME);
  }

  setLightTheme() {
    this.currentTheme = this.LIGHT_THEME;
    this.applyTheme();
    localStorage.setItem('app-theme', this.LIGHT_THEME);
  }

  getCurrentTheme() {
    return this.currentTheme;
  }

  // Ajoutez cette méthode pour vérifier si le thème actuel est sombre
  isDarkTheme() {
    return this.currentTheme === this.DARK_THEME;
  }

  private applyTheme() {
    document.body.classList.remove(this.LIGHT_THEME, this.DARK_THEME);
    document.body.classList.add(this.currentTheme);
  }
}
