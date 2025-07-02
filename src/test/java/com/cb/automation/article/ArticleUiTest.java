package com.cb.automation.article;

import com.cb.model.Article;
import com.cb.repository.ArticleRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArticleUiTest {

    @Autowired
    private ArticleRepository articleRepository;

    private WebDriver driver;

    @BeforeAll
    void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // Use headless mode
        driver = new ChromeDriver(options);
    }

    @Test
    void testArticlesRenderCorrectly() {
        List<Article> articles = articleRepository.findAll();
        for (Article article : articles) {
            String url = "http://localhost:8080/articles/" + article.getSlug();
            driver.get(url);

            String title = driver.getTitle();
            String body = driver.findElement(By.tagName("body")).getText();

            Assertions.assertTrue(title.contains(article.getTitle()), "Title not rendered correctly for: " + url);
            Assertions.assertTrue(body.contains(article.getContent()), "Content not rendered correctly for: " + url);
        }
    }

    @AfterAll
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
