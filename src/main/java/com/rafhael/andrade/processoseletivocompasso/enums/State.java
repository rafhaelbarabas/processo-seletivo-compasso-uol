package com.rafhael.andrade.processoseletivocompasso.enums;

public enum State {

    AC("Acre"),
    AL("Alagoas"),
    AP("Amapá"),
    AM("Amazonas"),
    BA("Bahia"),
    CE("Ceará"),
    DF("Distrito Federal"),
    ES("Espirito Santo"),
    GO("Goias"),
    MA("Maranhão"),
    MT("Mato Grosso"),
    MS("Mato Grosso do Sul"),
    MG("Minas Gerais"),
    PA("Para"),
    PB("Paraiba"),
    PR("Parana"),
    PE("Pernanbuco"),
    PI("Piaui"),
    RJ("Rio de Janeiro"),
    RN("Rio Grande do Norte"),
    RS("Rio Grande do Sul"),
    RO("Rondonia"),
    RR("Roraima"),
    SC("Santa Catarina"),
    SP("Sao Paulo"),
    SE("Sergipe"),
    TO("Tocantins");

    private String description;

    State(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString(){
        return this.description;
    }
}
