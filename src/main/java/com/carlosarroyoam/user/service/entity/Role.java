package com.carlosarroyoam.user.service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad JPA que representa un rol persistida en la tabla {@code roles}, junto con los {@link User
 * usuarios} que lo tienen asignado.
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "title", length = 32, nullable = false)
  private String title;

  @Column(name = "description", length = 128, nullable = false)
  private String description;

  @Builder.Default
  @OneToMany(mappedBy = "role")
  private List<User> users = new ArrayList<>();
}
