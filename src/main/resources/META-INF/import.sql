INSERT INTO roles(id, name, description)
	VALUES(1, 'App//Admin', 'Role for admins users');

INSERT INTO roles(id, name, description)
	VALUES(2, 'App//User', 'Role for normal users');

INSERT INTO users(id, name, age, username, email, password, role_id, is_active, created_at, updated_at)
	VALUES (1, 'Carlos Alberto Arroyo Martínez', 28, "carroyom", "carroyom@mail.com", "$2y$10$nl0PdraJnpNFSBzidF5CC.oSLTH1LUT5XI0nnLJjZ28ysvN6OZH5G", 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);