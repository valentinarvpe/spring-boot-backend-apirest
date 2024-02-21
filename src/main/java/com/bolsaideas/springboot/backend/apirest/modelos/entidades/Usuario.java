package com.bolsaideas.springboot.backend.apirest.modelos.entidades;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.ManyToAny;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="users")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Size(min=2, max=18)
	@Column(name="username", unique=true)
	private String nombreUsuario;
	
	@NotBlank
	@Size(min=5, max=60)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name="password")
	private String contrasena;
	
	@Transient
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private boolean admin;

	@Column(name = "enabled")
	private boolean activo;

	@ManyToMany
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="role_id"),
			uniqueConstraints = { @UniqueConstraint(columnNames = {"user_id", "role_id"})}
	)
	private List<Rol> roles;

	@PrePersist
	public void prePersist(){
		activo = true;
	}
}
