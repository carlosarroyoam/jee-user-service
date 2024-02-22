package com.carlosarroyoam.user.service.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "users")
@NamedQuery(name = User.FIND_ALL, query = "SELECT u FROM User u")
@NamedQuery(name = User.FIND_BY_USERNAME, query = "SELECT u FROM User u WHERE u.username = :username")
@NamedQuery(name = User.FIND_BY_EMAIL, query = "SELECT u FROM User u WHERE u.email = :email")
@Data
public class User {

	public static final String FIND_ALL = "User.findAll";
	public static final String FIND_BY_USERNAME = "User.findByUsername";
	public static final String FIND_BY_EMAIL = "User.findByEmail";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 128, nullable = false)
	private String name;

	@Column(name = "age", nullable = true)
	private Byte age;

	@Column(name = "email", length = 128, nullable = false, unique = true)
	private String email;

	@Column(name = "username", length = 128, nullable = false, unique = true)
	private String username;

	@Column(name = "password", length = 128, nullable = false)
	private String password;

	@Column(name = "is_active", nullable = false)
	private Boolean isActive;

	@Column(name = "role_id", nullable = false)
	private Long roleId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Role role;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

}
