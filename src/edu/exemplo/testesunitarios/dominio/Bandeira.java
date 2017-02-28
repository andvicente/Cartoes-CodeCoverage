package edu.exemplo.testesunitarios.dominio;

/**
 * Created by andvicente on 29/10/16.
 */
public enum Bandeira {

    MASTERCARD("master"),
    VISA("visa"),
    AMEX("amex"),
    DINNERS("dinners"),
    HIPERCARD("hipercard"),
    DESCONHECIDA("desconhecida");

    private final String nome;

    Bandeira(String nome) {
        this.nome = nome;
    }

    public String nome() {
        return nome;
    }
}
