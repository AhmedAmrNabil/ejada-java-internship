import { effect, Service, signal } from '@angular/core';

type Theme = 'light' | 'dark';
@Service()
export class ThemeService {
  private readonly storageKey = 'theme';
  readonly theme = signal<Theme>(this.getInitialTheme());

  constructor() {
    effect(() => {
      const theme = this.theme();
      document.documentElement.classList.toggle('dark', theme === 'dark');
      document.documentElement.style.colorScheme = theme;
      localStorage.setItem(this.storageKey, theme);
    });

    window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', (e) => {
      if (!localStorage.getItem(this.storageKey + ':explicit')) {
        this.theme.set(e.matches ? 'dark' : 'light');
      }
    });
  }

  toggleTheme() {
    const newTheme: Theme = this.theme() === 'light' ? 'dark' : 'light';
    localStorage.setItem(this.storageKey + ':explicit', 'true');
    this.theme.set(newTheme);
  }

  private getInitialTheme(): Theme {
    const stored = localStorage.getItem(this.storageKey) as Theme | null;
    if (stored) return stored;
    return window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
  }
}
