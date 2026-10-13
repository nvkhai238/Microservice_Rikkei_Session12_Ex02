INSERT INTO inventory_products (id,stock) VALUES (1,100) ON CONFLICT (id) DO NOTHING;
