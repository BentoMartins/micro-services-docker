package br.edu.atitus.product_service.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.product_service.entities.FavoriteEntity;
import br.edu.atitus.product_service.entities.ProductEntity;
import br.edu.atitus.product_service.repositories.FavoriteRepository;
import br.edu.atitus.product_service.repositories.ProductRepository;

@RestController
@RequestMapping("/ws/favorites")
public class FavoriteController {
	
	private final FavoriteRepository favoriteRepository;
	private final ProductRepository productRepository;

	public FavoriteController(FavoriteRepository favoriteRepository, ProductRepository productRepository) {
		this.favoriteRepository = favoriteRepository;
		this.productRepository = productRepository;
	}

	/**
	 * Lista todos os produtos favoritados do usuário
	 */
	@GetMapping
	public ResponseEntity<List<ProductEntity>> getFavorites(
			@RequestHeader("X-User-Id") Long userId) {
		
		List<FavoriteEntity> favorites = favoriteRepository.findByUserId(userId);
		
		List<Long> productIds = favorites.stream()
				.map(FavoriteEntity::getProductId)
				.collect(Collectors.toList());
		
		List<ProductEntity> products = productRepository.findAllById(productIds);
		
		return ResponseEntity.ok(products);
	}

	/**
	 * Adiciona um produto aos favoritos
	 */
	@PostMapping("/{productId}")
	public ResponseEntity<Void> addFavorite(
			@PathVariable Long productId,
			@RequestHeader("X-User-Id") Long userId) {
		
		// Verifica se o produto existe
		if (!productRepository.existsById(productId)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		// Verifica se já está favoritado
		if (favoriteRepository.existsByUserIdAndProductId(userId, productId)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
		// Adiciona aos favoritos
		FavoriteEntity favorite = new FavoriteEntity(userId, productId);
		favoriteRepository.save(favorite);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	/**
	 * Remove um produto dos favoritos
	 */
	@DeleteMapping("/{productId}")
	public ResponseEntity<Void> removeFavorite(
			@PathVariable Long productId,
			@RequestHeader("X-User-Id") Long userId) {
		
		try {
			// Busca o favorito
			var favoriteOpt = favoriteRepository.findByUserIdAndProductId(userId, productId);
			
			if (favoriteOpt.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			
			// Remove dos favoritos
			favoriteRepository.delete(favoriteOpt.get());
			
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			// Log do erro para debug
			System.err.println("Erro ao remover favorito: " + e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * Verifica se um produto está favoritado
	 */
	@GetMapping("/{productId}/check")
	public ResponseEntity<Boolean> isFavorite(
			@PathVariable Long productId,
			@RequestHeader("X-User-Id") Long userId) {
		
		boolean isFavorite = favoriteRepository.existsByUserIdAndProductId(userId, productId);
		
		return ResponseEntity.ok(isFavorite);
	}
}

