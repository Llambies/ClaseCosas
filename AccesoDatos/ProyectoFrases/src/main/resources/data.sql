INSERT INTO categoria (nombre) VALUES
('Filosofia'),
('Motivacion'),
('Ciencia')
ON CONFLICT (nombre) DO NOTHING;

INSERT INTO autor (nombre, anio_nacimiento, anio_fallecimiento, profesion) VALUES
('Aristoteles', -0384, -0322, 'Filosofo'),
('Confucio', -0551, -0479, 'Filosofo'),
('Albert Einstein', 1879, 1955, 'Fisico'),
('Marie Curie', 1867, 1934, 'Fisica y quimica'),
('Maya Angelou', 1928, 2014, 'Escritora'),
('Nelson Mandela', 1918, 2013, 'Politico')
ON CONFLICT (nombre) DO NOTHING;

INSERT INTO frase (texto, fecha_programada, autor_id, categoria_id) VALUES
('La excelencia no es un acto, sino un habito.', CURRENT_DATE, (SELECT id FROM autor WHERE nombre = 'Aristoteles'), (SELECT id FROM categoria WHERE nombre = 'Filosofia')),
('Saber lo que es correcto y no hacerlo es la peor cobardia.', CURRENT_DATE + INTERVAL '1 day', (SELECT id FROM autor WHERE nombre = 'Confucio'), (SELECT id FROM categoria WHERE nombre = 'Filosofia')),
('La imaginacion es mas importante que el conocimiento.', CURRENT_DATE + INTERVAL '2 day', (SELECT id FROM autor WHERE nombre = 'Albert Einstein'), (SELECT id FROM categoria WHERE nombre = 'Ciencia')),
('Nada en la vida debe ser temido, solo comprendido.', CURRENT_DATE + INTERVAL '3 day', (SELECT id FROM autor WHERE nombre = 'Marie Curie'), (SELECT id FROM categoria WHERE nombre = 'Ciencia')),
('Si no te gusta algo, cambialo. Si no puedes cambiarlo, cambia tu actitud.', CURRENT_DATE + INTERVAL '4 day', (SELECT id FROM autor WHERE nombre = 'Maya Angelou'), (SELECT id FROM categoria WHERE nombre = 'Motivacion')),
('La educacion es el arma mas poderosa para cambiar el mundo.', CURRENT_DATE + INTERVAL '5 day', (SELECT id FROM autor WHERE nombre = 'Nelson Mandela'), (SELECT id FROM categoria WHERE nombre = 'Motivacion')),
('Conocerse a si mismo es el comienzo de toda sabiduria.', CURRENT_DATE + INTERVAL '6 day', (SELECT id FROM autor WHERE nombre = 'Aristoteles'), (SELECT id FROM categoria WHERE nombre = 'Filosofia')),
('El hombre superior es modesto en el hablar, pero abundante en el obrar.', CURRENT_DATE + INTERVAL '7 day', (SELECT id FROM autor WHERE nombre = 'Confucio'), (SELECT id FROM categoria WHERE nombre = 'Filosofia')),
('La vida es como andar en bicicleta. Para mantener el equilibrio, debes seguir moviendote.', CURRENT_DATE + INTERVAL '8 day', (SELECT id FROM autor WHERE nombre = 'Albert Einstein'), (SELECT id FROM categoria WHERE nombre = 'Motivacion')),
('No hay que temer a nada en la vida, solo tratar de comprender.', CURRENT_DATE + INTERVAL '9 day', (SELECT id FROM autor WHERE nombre = 'Marie Curie'), (SELECT id FROM categoria WHERE nombre = 'Ciencia')),
('Hacemos lo mejor que podemos con lo que sabemos. Cuando sabemos mas, lo hacemos mejor.', CURRENT_DATE + INTERVAL '10 day', (SELECT id FROM autor WHERE nombre = 'Maya Angelou'), (SELECT id FROM categoria WHERE nombre = 'Motivacion')),
('Parece imposible hasta que se hace.', CURRENT_DATE + INTERVAL '11 day', (SELECT id FROM autor WHERE nombre = 'Nelson Mandela'), (SELECT id FROM categoria WHERE nombre = 'Motivacion'))
ON CONFLICT (fecha_programada) DO NOTHING;

INSERT INTO app_user (username, password, role) VALUES
('admin', '$2y$10$sPQbAXp3ncu5IGoslJopo.HlvwBqXwiFgSUCUMdcJiU5/VCiGeFqq', 'ADMIN'),
('standard', '$2y$10$sPQbAXp3ncu5IGoslJopo.HlvwBqXwiFgSUCUMdcJiU5/VCiGeFqq', 'STANDARD')
ON CONFLICT (username) DO UPDATE
SET password = EXCLUDED.password,
    role = EXCLUDED.role;
