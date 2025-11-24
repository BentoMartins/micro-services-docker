-- Cria a tabela de favoritos
CREATE TABLE tb_favorite (
    id SERIAL NOT NULL,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE(user_id, product_id)
);

-- Cria índices para melhorar performance
CREATE INDEX idx_favorite_user_id ON tb_favorite(user_id);
CREATE INDEX idx_favorite_product_id ON tb_favorite(product_id);

