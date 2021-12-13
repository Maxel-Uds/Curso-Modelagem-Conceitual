package com.maxel.cursomc.domain.enums;

public enum Perfil {

    ADMIN(1, "ROLE_ADMIN"),
    CLIENTE(2, "ROLE_CLIENTE");

    private Integer cod;
    private String descricao;

    private Perfil(Integer cod, String descricao) {
        this.descricao = descricao;
        this.cod = cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getCod() {
        return cod;
    }

    public static Perfil toEnum(Integer cod) {
        if(cod == null) {
            return null;
        }

        for(Perfil x : Perfil.values()) {
            if(cod.equals(x.getCod())) {
                return  x;
            }
        }

        throw  new IllegalArgumentException("ID inválido: " + cod);
    }
}
