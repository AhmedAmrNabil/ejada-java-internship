import { Component, computed } from '@angular/core';
import { ThemeService } from '../../services/theme.service';
import { NgIconComponent } from '@ng-icons/core';

@Component({
  selector: 'app-theme-toggle',
  imports: [NgIconComponent],
  templateUrl: './theme-toggle.component.html',
  styleUrl: './theme-toggle.component.css',
})
export class ThemeToggleComponent {
  constructor(private readonly themeService: ThemeService) {}
  protected readonly isDarkTheme = computed(() => this.themeService.theme() === 'dark');
  toggleTheme() {
    this.themeService.toggleTheme();
  }
}
