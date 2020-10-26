package com.cherniak.geek.market.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "categories")
public class Category {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    @Size(min = 1, max = 100)
    private String title;

    @OneToMany(mappedBy = "category")
    List<Product> products;

}
