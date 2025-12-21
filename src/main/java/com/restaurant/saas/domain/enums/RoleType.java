package com.restaurant.saas.domain.enums;

public enum RoleType {

    OWNER("Dono do Restaurante", "Acesso total ao sistema"),
    MANAGER("Gerente", "Administração geral, sem acesso a fincanças sensíveis"),
    WAITER("Garçom", "Gestão de pedidos e mesas"),
    KITCHEN("Cozinha", "Visualização e atualização de pedidos da cozinha"),
    CASHIER("Caixa", "Fechamento de contas e relatórios financeiros");

    private final String displayName;
    private final String description;


    RoleType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }


    public String getDisplayName() {
        return displayName;
    }


    public String getDescription() {
        return description;
    }

    public boolean isAdmin() {
        return this == OWNER || this == MANAGER;
    }

    public boolean canAccessFinancial(){
        return this == OWNER || this == CASHIER;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
