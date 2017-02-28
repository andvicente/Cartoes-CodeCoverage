package edu.exemplo.testesunitarios;

import edu.exemplo.testesunitarios.dominio.Bandeira;
import edu.exemplo.testesunitarios.dominio.Cartao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class Main {

    public static void main(String[] args) {
        final Double valorConta = 100.00;
        NumberFormat nf = NumberFormat.getCurrencyInstance();

	    Cartao cartao = new Cartao(Bandeira.MASTERCARD);
        BigDecimal conta = BigDecimal.valueOf(cartao.calculaConta(valorConta));
        Double valotTotalContaComTaxas = conta.setScale(2,RoundingMode.HALF_UP).doubleValue();
        String valorTotalContaReais = nf.format(valotTotalContaComTaxas);

        if (valotTotalContaComTaxas == 107.00){
            System.out.println(valorTotalContaReais + " (7% de taxa)");
        }
        else
            System.out.println(valorTotalContaReais + " (Outra taxa)");

    }
}
