import { Component, inject } from '@angular/core';
import { PostsService } from '../../services/posts.service';
import { catchError, of } from 'rxjs';
import { RouterModule } from '@angular/router';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-posts-list',
  imports: [RouterModule,AsyncPipe],
  templateUrl: './posts-list.component.html',
  styleUrl: './posts-list.component.css',
})
export class PostsListComponent {
  private readonly postsService = inject(PostsService);

  posts$ = this.postsService.getPosts().pipe(
    catchError((error) => {
      console.error('Error fetching posts:', error);
      return of([]); // Return an empty array in case of error
    }),
  )

  // posts = toSignal(
  //   this.postsService.getPosts().pipe(
  //     catchError((error) => {
  //       console.error('Error fetching posts:', error);
  //       return of([]); // Return an empty array in case of error
  //     }),
  //   ),
  //   { initialValue: [] },
  // );
}
