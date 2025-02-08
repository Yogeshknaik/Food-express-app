package com.rktechyt.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.rktechyt.ecommerce.global.GlobalData;
import com.rktechyt.ecommerce.model.Order;
import com.rktechyt.ecommerce.model.Product;
import com.rktechyt.ecommerce.repository.OrderRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PaymentController {

    @Autowired
    private OrderRepository orderRepository; // Inject OrderRepository

    @PostMapping("/payNow")
    public String orderPlaced(Model model) {
        int total = (int)GlobalData.cart.stream().mapToDouble(Product::getPrice).sum();
        int platformFee = 10;
        int handlingFee = (int) ((int) total * 0.05); // 5% handling fee
        int discount = (int) (total * 0.20); // 20% discount
        int finalAmount = total + platformFee + handlingFee - discount;
        int savings = discount;

        // Create and save the order
        for (Product product : GlobalData.cart) {
            Order order = new Order();
            order.setProduct(product); // Set the product
            order.setQuantity(1); // Assuming 1 quantity for each product in the cart
            order.setTotalPrice(product.getPrice()); // Set the total price for this product
            order.setOrderDate(LocalDate.now()); // Set the current date
            orderRepository.save(order); // Save the order to the database
        }

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