import { Component } from '@angular/core';

@Component({
  selector: 'app-products-list',
  imports: [],
  templateUrl: './products-list.component.html',
  styleUrl: './products-list.component.css',
})
export class ProductsListComponent {
  public productsList = [
    { id: 1, name: 'Product 1', description: 'Description for Product 1', price: 100 },
    { id: 2, name: 'Product 2', description: 'Description for Product 2', price: 200 },
    { id: 3, name: 'Product 3', description: 'Description for Product 3', price: 300 },
  ];
}
