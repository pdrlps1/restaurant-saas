ALTER TABLE tenant_001.users
    ADD COLUMN IF NOT EXISTS phone VARCHAR(20),
    ADD COLUMN IF NOT EXISTS avatar_url VARCHAR(500),
    ADD COLUMN IF NOT EXISTS last_login TIMESTAMP,
    ADD COLUMN IF NOT EXISTS email_verified BOOLEAN DEFAULT false,
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

CREATE INDEX IF NOT EXISTS idx_users_email ON tenant_001.users(email);
CREATE INDEX IF NOT EXISTS idx_users_is_active ON tenant_001.users(is_active) WHERE is_active = true;

ALTER TABLE tenant_001.roles
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

COMMENT ON TABLE tenant_001.users IS 'Usuários do restaurante';
COMMENT ON TABLE tenant_001.roles IS 'Papéis/funções dos usuários';
COMMENT ON TABLE tenant_001.user_roles IS 'Relacionamento N:N entre usuários e roles';

COMMENT ON COLUMN tenant_001.users.id IS 'Identificador único do usuário (UUID)';
COMMENT ON COLUMN tenant_001.users.name IS 'Nome completo do usuário';
COMMENT ON COLUMN tenant_001.users.email IS 'Email do usuário (único por tenant)';
COMMENT ON COLUMN tenant_001.users.password_hash IS 'Senha criptografada (BCrypt)';
COMMENT ON COLUMN tenant_001.users.phone IS 'Telefone de contato';
COMMENT ON COLUMN tenant_001.users.avatar_url IS 'URL da foto de perfil';
COMMENT ON COLUMN tenant_001.users.is_active IS 'Indica se o usuário está ativo';
COMMENT ON COLUMN tenant_001.users.last_login IS 'Data/hora do último login';
COMMENT ON COLUMN tenant_001.users.email_verified IS 'Indica se o email foi verificado';

ALTER TABLE tenant_002.users
    ADD COLUMN IF NOT EXISTS phone VARCHAR(20),
    ADD COLUMN IF NOT EXISTS avatar_url VARCHAR(500),
    ADD COLUMN IF NOT EXISTS last_login TIMESTAMP,
    ADD COLUMN IF NOT EXISTS email_verified BOOLEAN DEFAULT false,
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

CREATE INDEX IF NOT EXISTS idx_users_email ON tenant_002.users(email);
CREATE INDEX IF NOT EXISTS idx_users_is_active ON tenant_002.users(is_active) WHERE is_active = true;

ALTER TABLE tenant_002.roles
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

COMMENT ON TABLE tenant_002.users IS 'Usuários do restaurante (garçons, gerentes, cozinha, etc)';
COMMENT ON TABLE tenant_002.roles IS 'Papéis/funções dos usuários (OWNER, MANAGER, WAITER, etc)';
COMMENT ON TABLE tenant_002.user_roles IS 'Relacionamento N:N entre usuários e roles';

COMMENT ON COLUMN tenant_002.users.id IS 'Identificador único do usuário (UUID)';
COMMENT ON COLUMN tenant_002.users.name IS 'Nome completo do usuário';
COMMENT ON COLUMN tenant_002.users.email IS 'Email do usuário (único por tenant)';
COMMENT ON COLUMN tenant_002.users.password_hash IS 'Senha criptografada (BCrypt)';
COMMENT ON COLUMN tenant_002.users.phone IS 'Telefone de contato';
COMMENT ON COLUMN tenant_002.users.avatar_url IS 'URL da foto de perfil';
COMMENT ON COLUMN tenant_002.users.is_active IS 'Indica se o usuário está ativo';
COMMENT ON COLUMN tenant_002.users.last_login IS 'Data/hora do último login';
COMMENT ON COLUMN tenant_002.users.email_verified IS 'Indica se o email foi verificado';

INSERT INTO tenant_001.users (id, name, email, password_hash, is_active, email_verified)
VALUES (
           gen_random_uuid(),
           'João Silva',
           'joao@bellavista.com',
           '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', -- senha123
           true,
           true
       ) ON CONFLICT (email) DO NOTHING;

INSERT INTO tenant_001.user_roles (user_id, role_id)
SELECT
    u.id,
    r.id
FROM tenant_001.users u
         CROSS JOIN tenant_001.roles r
WHERE u.email = 'joao@bellavista.com'
  AND r.name = 'OWNER'
    ON CONFLICT DO NOTHING;

INSERT INTO tenant_001.users (id, name, email, password_hash, is_active, email_verified)
VALUES (
           gen_random_uuid(),
           'Maria Santos',
           'maria@bellavista.com',
           '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', -- senha123
           true,
           true
       ) ON CONFLICT (email) DO NOTHING;

INSERT INTO tenant_001.user_roles (user_id, role_id)
SELECT
    u.id,
    r.id
FROM tenant_001.users u
         CROSS JOIN tenant_001.roles r
WHERE u.email = 'maria@bellavista.com'
  AND r.name = 'MANAGER'
    ON CONFLICT DO NOTHING;

INSERT INTO tenant_002.users (id, name, email, password_hash, is_active, email_verified)
VALUES (
           gen_random_uuid(),
           'João Barbosa',
           'joao@bardojoao.com',
           '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', -- senha123
           true,
           true
       ) ON CONFLICT (email) DO NOTHING;

INSERT INTO tenant_002.user_roles (user_id, role_id)
SELECT
    u.id,
    r.id
FROM tenant_002.users u
         CROSS JOIN tenant_002.roles r
WHERE u.email = 'joao@bardojoao.com'
  AND r.name = 'OWNER'
    ON CONFLICT DO NOTHING;