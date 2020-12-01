package com.cherniak.geek.market.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  public Category(Long id,
      @Size(min = 1, max = 100) String title) {
    this.id = id;
    this.title = title;
  }
}
