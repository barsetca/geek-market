package com.cherniak.geek.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cost")
    private int cost;

    @Column(name = "total_cost")
    private int totalCost;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem(Product product){
        this.product = product;
        this.quantity = 1;
        this.cost = product.getCost();
        this.totalCost=product.getCost();
    }
    public void incrementQuantity() {
        quantity++;
        totalCost = product.getCost()*quantity;

    }

    public void decrementQuantity() {
        quantity--;
        totalCost = product.getCost()*quantity;

    }
}
