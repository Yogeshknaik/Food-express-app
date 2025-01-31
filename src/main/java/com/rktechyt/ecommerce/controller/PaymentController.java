package com.rktechyt.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.rktechyt.ecommerce.global.GlobalData;
import com.rktechyt.ecommerce.model.Product;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PaymentController {

    @PostMapping("/payNow")
    public String orderPlaced(Model model) {
        double total = GlobalData.cart.stream().mapToDouble(Product::getPrice).sum();
        double platformFee = 10;
        double handlingFee = total * 0.05; // 5% handling fee
        double discount = total * 0.20; // 20% discount
        double finalAmount = total + platformFee + handlingFee - discount;
        double savings = discount;

        model.addAttribute("cart", GlobalData.cart);
        model.addAttribute("total", total);
        model.addAttribute("platformFee", platformFee);
        model.addAttribute("handlingFee", handlingFee);
        model.addAttribute("discount", discount);
        model.addAttribute("finalAmount", finalAmount);
        model.addAttribute("savings", savings);

        // Payment methods
        Map<String, String> paymentMethods = new HashMap<>();
        paymentMethods.put("UPI Scanner", "Scan the QR code to pay");
        paymentMethods.put("PhonePe", "Pay using PhonePe");
        paymentMethods.put("GPay", "Pay using GPay");
        paymentMethods.put("Visa", "Pay using Visa card");
        model.addAttribute("paymentMethods", paymentMethods);

        return "orderPlaced";
    }
}
