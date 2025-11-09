-- Usuários
INSERT INTO user (id, username, password, role)
VALUES
  (UUID(), 'admin', '$2a$10$adminhashsenha', 'ROLE_ADMIN'),
  (UUID(), 'cliente1', '$2a$10$cliente1hashsenha', 'ROLE_USER'),
  (UUID(), 'cliente2', '$2a$10$cliente2hashsenha', 'ROLE_USER');

-- Produtos
INSERT INTO produto (id, nome, descricao, preco, categoria, estoque, criado_em, atualizado_em)
VALUES
  (UUID(), 'Notebook Gamer', 'RTX 3060, 16GB RAM', 5500.00, 'Eletrônicos', 10, NOW(), NOW()),
  (UUID(), 'Smartphone X', '128GB, Câmera 64MP', 3200.00, 'Eletrônicos', 20, NOW(), NOW()),
  (UUID(), 'Fone Bluetooth', 'Noise Cancelling', 450.00, 'Acessórios', 50, NOW(), NOW());

-- Pedidos (exemplo com status PAGO)
INSERT INTO pedido (id, usuario_id, valor_total, status, criado_em)
VALUES
  (UUID(), (SELECT id FROM user WHERE username = 'cliente1'), 5950.00, 'PAGO', NOW()),
  (UUID(), (SELECT id FROM user WHERE username = 'cliente2'), 450.00, 'PAGO', NOW());

-- Itens de Pedido
INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario)
VALUES
  (UUID(), (SELECT id FROM pedido LIMIT 1), (SELECT id FROM produto WHERE nome = 'Notebook Gamer'), 1, 5500.00),
  (UUID(), (SELECT id FROM pedido LIMIT 1), (SELECT id FROM produto WHERE nome = 'Fone Bluetooth'), 1, 450.00),
  (UUID(), (SELECT id FROM pedido LIMIT 1 OFFSET 1), (SELECT id FROM produto WHERE nome = 'Fone Bluetooth'), 1, 450.00);