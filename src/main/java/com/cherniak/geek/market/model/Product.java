package com.cherniak.geek.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "cost")
    private Integer cost;

    public Product(String title, int cost) {
        this(null, title, cost);
    }

    public Product(Long id, String title, Integer cost) {
        this.id = id;
        this.title = title;
        this.cost = cost;
    }
}
