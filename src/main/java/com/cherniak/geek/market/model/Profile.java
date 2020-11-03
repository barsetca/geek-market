package com.cherniak.geek.market.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profiles")
@NoArgsConstructor
@Data
public class Profile {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "firstname")
  @Size(min = 2)
  private String firstname;

  @Column(name = "surname")
  @Size(min = 2)
  private String surname;

  @Column(name = "phone")
  @Size(min = 3)
  private String phone;


  @Column(name = "birthday")
  @Past
  private LocalDate birthday;

  @Size(min = 2)
  @Column(name = "sex")
  private String sex;

  @Size(min = 2)
  @Column(name = "city")
  private String city;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;
}
