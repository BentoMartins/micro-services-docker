-- Adiciona as colunas category e condition na tabela tb_product
ALTER TABLE tb_product
ADD COLUMN category VARCHAR(50),
ADD COLUMN condition VARCHAR(50);

-- Atualiza os registros existentes com valores padrão (opcional)
-- UPDATE tb_product SET category = 'OTHERS', condition = 'NEW' WHERE category IS NULL;

