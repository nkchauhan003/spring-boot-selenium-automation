package com.cb.controller;

import com.cb.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles/{slug}")
    public String getArticleBySlug(@PathVariable String slug, Model model) {
        var article = articleRepository.findBySlug(slug).orElseThrow(() -> new RuntimeException("Article not found"));
        model.addAttribute("article", article);
        return "article";
    }
}
