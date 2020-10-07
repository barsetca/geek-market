package com.cherniak.geek.market.model;

import com.cherniak.geek.market.util.Cart;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDate date = LocalDate.now();

    @Column(name = "cost")
    private int cost;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "order")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<OrderItem> items;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Order(User user, Cart cart, String phone, String address) {
        this.phone = phone;
        this.address = address;
        this.user = user;
        this.cost = cart.getPrice();
        this.items = new ArrayList<>();
        cart.getItems().forEach(io -> {
            io.setOrder(this);
            items.add(io);
        });
        cart.clear();
     }
}
