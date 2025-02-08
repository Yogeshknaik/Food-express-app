package com.rktechyt.ecommerce.repository;

import com.rktechyt.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT SUM(o.totalPrice) FROM Order o")
    Double getTotalRevenue();

    @Query("SELECT COUNT(o) FROM Order o")
    Long getTotalOrders();

    @Query("SELECT o.product.name, SUM(o.quantity) FROM Order o GROUP BY o.product.name ORDER BY SUM(o.quantity) DESC")
    List<Object[]> getTopSellingProducts();

    @Query("SELECT FUNCTION('DATE', o.orderDate), SUM(o.totalPrice) FROM Order o GROUP BY FUNCTION('DATE', o.orderDate) ORDER BY FUNCTION('DATE', o.orderDate)")
    List<Object[]> getDailySales();
}