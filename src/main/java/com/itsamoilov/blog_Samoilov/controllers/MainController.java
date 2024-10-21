package com.itsamoilov.blog_Samoilov.controllers;

import com.itsamoilov.blog_Samoilov.models.Review;
import com.itsamoilov.blog_Samoilov.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/")
    public String home(Map<String, Object> model) {
        model.put("name", "World");
        return "home";
    }


    @GetMapping("/about")
    public String about(Map<String, Object> model) {
        model.put("title", "Страница про меня");
        return "about";
    }
    @GetMapping("/reviews")
    public String reviews(Map<String, Object> model) {
        Iterable<Review> reviews = reviewRepository.findAll();
        model.put("title", "Страница с отзывами");
        model.put("reviews", reviews);
        return "reviews";
    }
    @PostMapping("/reviews-add")
    public String reviewsAdd(@RequestParam String title,
                             @RequestParam String text,Map<String, Object> model) {
        Review review = new Review(title, text);
        reviewRepository.save(review);

        return "redirect:/reviews";
    }
}
