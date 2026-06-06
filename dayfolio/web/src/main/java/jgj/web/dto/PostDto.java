package jgj.web.dto;

import jgj.web.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class PostDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Request {
        private String title;
        private String content;

        public Post toEntity() {
            Post post = new Post();
            post.setTitle(title);
            post.setContent(content);
            return post;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long id;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Response(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.createdAt = post.getCreatedAt();
            this.updatedAt = post.getUpdatedAt();
        }
    }
}