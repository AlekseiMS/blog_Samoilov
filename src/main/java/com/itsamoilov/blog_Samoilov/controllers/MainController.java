package com.itsamoilov.blog_Samoilov.controllers;

import com.itsamoilov.blog_Samoilov.models.Review;
import com.itsamoilov.blog_Samoilov.models.Role;
import com.itsamoilov.blog_Samoilov.models.User;
import com.itsamoilov.blog_Samoilov.repo.ReviewRepository;
import com.itsamoilov.blog_Samoilov.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class MainController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

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

    @GetMapping("/reviews/{id}")
    public String reviewInfo(@PathVariable(value = "id") long reviewId,
                             Map<String, Object> model ) {
        Optional<Review> review = reviewRepository.findById(reviewId);

        ArrayList<Review> result = new ArrayList<>();
        review.ifPresent(result::add);

        model.put("review", result);

        return "review-info";
    }

    @GetMapping("/reviews/{id}/update")
    public String reviewUpdate(@PathVariable(value = "id") long reviewId, Map<String, Object> model) {
        Optional<Review> review = reviewRepository.findById(reviewId);

        ArrayList<Review> result = new ArrayList<>();
        review.ifPresent(result::add);

        model.put("review", result);
        return "review-update";
    }
    @PostMapping("/reviews/{id}/update")
    public String reviewsUpdateForm(@PathVariable(value = "id") long reviewId
            , @RequestParam String title
            , @RequestParam String text, Map<String, Object> model) throws ClassNotFoundException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ClassNotFoundException());
        review.setTitle(title);
        review.setText(text);
        reviewRepository.save(review);

        return "redirect:/reviews/" + reviewId;
    }
    @PostMapping("/reviews/{id}/delete")
    public String reviewsDelete(@PathVariable(value = "id") long reviewId
            , Map<String, Object> model) throws ClassNotFoundException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ClassNotFoundException());
        reviewRepository.delete(review);

        return "redirect:/reviews";
    }

    @GetMapping("/reg")
    public String reg() {
        return "reg";
    }

    @PostMapping("/reg")
    public String addUser(User user, Map<String, Object> model) {
        user.setEnabled(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return "redirect:/login";
    }
}
