import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { map, switchMap } from 'rxjs';
import { PostsService } from '../../services/posts.service';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-post-detail',
  imports: [AsyncPipe],
  templateUrl: './post-detail.component.html',
  styleUrl: './post-detail.component.css',
})
export class PostDetailComponent {
  private readonly route = inject(ActivatedRoute);
  private readonly postService = inject(PostsService);

  readonly post$ = this.route.paramMap.pipe(
    map((params) => Number(params.get('id'))),
    switchMap((id) => this.postService.getPostById(id)),
  );
}
