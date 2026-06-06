package jgj.web.service;

import jgj.web.dto.PostDto;
import jgj.web.entity.Post;
import jgj.web.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    public List<PostDto.Response> findAll() {
        return postRepository.findAll().stream()
                .map(PostDto.Response::new)
                .toList();
    }

    public PostDto.Response findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found: " + id));
        return new PostDto.Response(post);
    }

    @Transactional
    public PostDto.Response create(PostDto.Request request) {
        Post saved = postRepository.save(request.toEntity());
        return new PostDto.Response(saved);
    }

    @Transactional
    public PostDto.Response update(Long id, PostDto.Request request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found: " + id));
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        return new PostDto.Response(post);
    }

    @Transactional
    public void delete(Long id) {
        if (!postRepository.existsById(id)) {
            throw new IllegalArgumentException("Post not found: " + id);
        }
        postRepository.deleteById(id);
    }
}
