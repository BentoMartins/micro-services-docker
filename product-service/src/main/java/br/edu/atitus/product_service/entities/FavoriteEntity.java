package br.edu.atitus.product_service.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "tb_favorite", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"user_id", "product_id"})
})
public class FavoriteEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_id", nullable = false)
	private Long userId;
	
	@Column(name = "product_id", nullable = false)
	private Long productId;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	public FavoriteEntity() {
		this.createdAt = LocalDateTime.now();
	}
	
	public FavoriteEntity(Long userId, Long productId) {
		this();
		this.userId = userId;
		this.productId = productId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}

