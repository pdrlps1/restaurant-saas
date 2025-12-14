CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE public.tenants (

    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    schema_name VARCHAR(63) NOT NULL UNIQUE,

    company_name VARCHAR(255) NOT NULL,

    subdomain VARCHAR(63) NOT NULL UNIQUE,

    is_active BOOLEAN NOT NULL DEFAULT true,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT subdomain_format CHECK (subdomain ~ '^[a-z0-9-]+$'),
    CONSTRAINT schema_name_format CHECK (schema_name ~ '^tenant_[0-9]+$')

);

CREATE INDEX idx_tenants_subdomain ON public.tenants(subdomain);
CREATE INDEX idx_tenants_schema_name ON public.tenants(schema_name);
CREATE INDEX idx_tenants_active ON public.tenants(is_active) WHERE is_active = true;

COMMENT ON TABLE public.tenants IS
    'Cadastro de todos os restaurantes (tenants) do sistema SaaS';

COMMENT ON COLUMN public.tenants.schema_name IS
    'Nome do schema PostgreSQL onde ficarão os dados deste tenant';

COMMENT ON COLUMN public.tenants.subdomain IS
    'Subdomínio único para acesso web';

INSERT INTO public.tenants (schema_name, company_name, subdomain) VALUES
    ('tenant_001', 'Restaurante Bella Vista', 'bellavista'),
    ('tenant_002', 'Bar do João', 'bardojoao');

CREATE SCHEMA IF NOT EXISTS tenant_001;
COMMENT ON SCHEMA tenant_001 IS 'Dados do Restaurante Bella Vista';

CREATE SCHEMA IF NOT EXISTS tenant_002;
COMMENT ON SCHEMA tenant_002 IS 'Dados do Bar do João';