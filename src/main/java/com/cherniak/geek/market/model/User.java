package com.cherniak.geek.market.model;


import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "username")
  @NotBlank
  @Size(min = 2)
  private String username;

  @Column(name = "password")
  @NotBlank
  @Size(min = 2)
  private String password;

  @Column(name = "email", unique = true)
  @Email
  private String email;

  @OneToMany(mappedBy = "user")
  @Cascade(org.hibernate.annotations.CascadeType.ALL)
  private List<Order> orders;

  @ManyToMany
  @JoinTable(name = "users_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Collection<Role> roles;

  @OneToOne(mappedBy = "user")
  @Cascade(org.hibernate.annotations.CascadeType.ALL)
  private Profile profile;

}
