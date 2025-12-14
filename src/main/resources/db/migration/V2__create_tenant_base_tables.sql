CREATE TABLE tenant_001.users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT email_format CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
);

CREATE TABLE tenant_001.roles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT role_name_format CHECK (name ~ '^[A-Z_]+$')
);

CREATE TABLE tenant_001.user_roles (
    user_id UUID NOT NULL REFERENCES tenant_001.users(id) ON DELETE CASCADE,
    role_id UUID NOT NULL REFERENCES tenant_001.roles(id) ON DELETE CASCADE,
    assigned_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, role_id)
);

CREATE INDEX idx_users_email ON tenant_001.users(email);
CREATE INDEX idx_users_active ON tenant_001.users(is_active) WHERE is_active = true;
CREATE INDEX idx_roles_name ON tenant_001.roles(name);
CREATE INDEX idx_user_roles_user_id ON tenant_001.user_roles(user_id);
CREATE INDEX idx_user_roles_role_id ON tenant_001.user_roles(role_id);

INSERT INTO tenant_001.roles (name, description) VALUES
    ('OWNER', 'Dono do restaurante - acesso total ao sistema'),
    ('MANAGER', 'Gerente - administração geral'),
    ('WAITER', 'Garçom - gestão de pedidos e mesas'),
    ('KITCHEN', 'Cozinha - visualização e atualização de pedidos'),
    ('CASHIER', 'Caixa - fechamento de contas');

COMMENT ON TABLE tenant_001.users IS 'Usuários que trabalham no restaurante';
COMMENT ON TABLE tenant_001.roles IS 'Papéis/funções no sistema';
COMMENT ON TABLE tenant_001.user_roles IS 'Relacionamento N:N entre usuários e roles';

CREATE TABLE tenant_002.users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT email_format CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
);

CREATE TABLE tenant_002.roles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT role_name_format CHECK (name ~ '^[A-Z_]+$')
);

CREATE TABLE tenant_002.user_roles (
    user_id UUID NOT NULL REFERENCES tenant_002.users(id) ON DELETE CASCADE,
    role_id UUID NOT NULL REFERENCES tenant_002.roles(id) ON DELETE CASCADE,
    assigned_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, role_id)
);

CREATE INDEX idx_users_email ON tenant_002.users(email);
CREATE INDEX idx_users_active ON tenant_002.users(is_active) WHERE is_active = true;
CREATE INDEX idx_roles_name ON tenant_002.roles(name);
CREATE INDEX idx_user_roles_user_id ON tenant_002.user_roles(user_id);
CREATE INDEX idx_user_roles_role_id ON tenant_002.user_roles(role_id);

INSERT INTO tenant_002.roles (name, description) VALUES
    ('OWNER', 'Dono do restaurante - acesso total ao sistema'),
    ('MANAGER', 'Gerente - administração geral'),
    ('WAITER', 'Garçom - gestão de pedidos e mesas'),
    ('KITCHEN', 'Cozinha - visualização e atualização de pedidos'),
    ('CASHIER', 'Caixa - fechamento de contas');