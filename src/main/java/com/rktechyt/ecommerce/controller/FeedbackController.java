package com.rktechyt.ecommerce.controller;
import com.rktechyt.ecommerce.model.Feedback;
import com.rktechyt.ecommerce.repository.FeedbackRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class FeedbackController {

    private final FeedbackRepository feedbackRepository;

    // Constructor Injection (Best Practice)
    public FeedbackController(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @GetMapping("/feedback")
    public String showFeedbackForm() {
        return "feedback";  // Renders feedback form page
    }

    @PostMapping("/submitFeedback")
    public String submitFeedback(@ModelAttribute Feedback feedback, RedirectAttributes redirectAttributes) {
        feedbackRepository.save(feedback);
        redirectAttributes.addFlashAttribute("message", "Thank you for your feedback!");
        return "redirect:/feedback";
    }

    @GetMapping("/home")
    public String home() {
        return "home"; // Renders home page
    }

    @GetMapping("/feedback/lists")
    public String getFeedbackList(Model model) {
        List<Feedback> feedbackList = feedbackRepository.findAll();
        feedbackList.forEach(feedback -> {
            System.out.println("Rating for feedback: " + feedback.getRating());
        });
        model.addAttribute("feedbackList", feedbackList);

        return "feedback-list"; // Ensure the view file exists
    }
}