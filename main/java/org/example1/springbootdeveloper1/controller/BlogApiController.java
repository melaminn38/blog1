package org.example1.springbootdeveloper1.controller;

import lombok.RequiredArgsConstructor;
import org.example1.springbootdeveloper1.domain.Article;
import org.example1.springbootdeveloper1.dto.AddArticleRequest;
import org.example1.springbootdeveloper1.dto.ArticleResponse;
import org.example1.springbootdeveloper1.dto.UpdateArticleRequest;
import org.example1.springbootdeveloper1.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController //http response body에 객체 데이터를 json형식으로 반환하는 컨트롤러
public class BlogApiController {

    private final BlogService blogService;

    //http메서드가 post일때 전달받은url과 동일하면 매서드로 매핑
    @PostMapping("/api/articles")
    // @requestbody로 요청 본문 값 매핑
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request, Principal principal){
        Article savedArticle = blogService.save(request, principal.getName());
        //요청한 자원이 성공적으로 생성되엇으며 저장된 블로그 글 정보를 응답객체에 담아전송
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }
    @GetMapping("/api/articles/{id}")//url경로에서 값 추출
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id){
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id){
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody UpdateArticleRequest request){
        Article updatedArticle = blogService.update(id, request);

        return  ResponseEntity.ok()
                .body(updatedArticle);
    }
}



