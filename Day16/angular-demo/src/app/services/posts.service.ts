import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { Observable } from 'rxjs';

export type Post = {
  userId: number;
  id: number;
  title: string;
  body: string;
};

@Service()
export class PostsService {
  private readonly baseUrl = 'https://jsonplaceholder.typicode.com/posts';

  private readonly http = inject(HttpClient);

  getPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(this.baseUrl);
  }

  getPostById(id: number): Observable<Post> {
    return this.http.get<Post>(`${this.baseUrl}/${id}`);
  }
}
