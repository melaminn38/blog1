package org.example1.springbootdeveloper1.controller;

import lombok.RequiredArgsConstructor;
import org.example1.springbootdeveloper1.domain.Article;
import org.example1.springbootdeveloper1.dto.ArticleListViewResponse;
import org.example1.springbootdeveloper1.dto.ArticleViewResponse;
import org.example1.springbootdeveloper1.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {

    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleListViewResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleListViewResponse::new)
                .toList();
        model.addAttribute("articles", articles);//블로그 글 리스트저장

        return "articleList";//articlelist.html라는 뷰 조회
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model){
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));

        return "article";
    }

    @GetMapping("/new-article")//idㅋ키를 가진 쿼리 파라미터 값을 id볂수에 매핑
    public String newArticle(@RequestParam(required = false) Long id, Model model){
        if(id == null){//id가없으면 생성
            model.addAttribute("article", new ArticleViewResponse());
        } else {//id가없으면 수정
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }
        return "newArticle";
    }
}
