package com.cherniak.geek.market.model;

import com.cherniak.geek.market.util.Cart;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
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
    @FutureOrPresent
    private LocalDate date = LocalDate.now();

    @Column(name = "cost")
    private int cost;
    @Size(min = 2)
    @Column(name = "receiver")
    private String receiver;

    @Column(name = "phone")
    @Size(min = 2)
    private String phone;

    @Column(name = "address")
    @Size(min = 6)
    private String address;

    @OneToMany(mappedBy = "order")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<OrderItem> items;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Order(User user, Cart cart, String receiver, String phone, String address) {
        this.phone = phone;
        this.address = address;
        this.receiver = receiver;
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
