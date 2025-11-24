package br.edu.atitus.product_service.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.atitus.product_service.entities.FavoriteEntity;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {
	
	List<FavoriteEntity> findByUserId(Long userId);
	
	Optional<FavoriteEntity> findByUserIdAndProductId(Long userId, Long productId);
	
	void deleteByUserIdAndProductId(Long userId, Long productId);
	
	boolean existsByUserIdAndProductId(Long userId, Long productId);
}

