package edu.exemplo.testesunitarios.dominio;

/**
 * Referências:
 *      http://codereview.stackexchange.com/questions/59521/credit-card-validator-using-luhns-algorithm
 *      https://github.com/moip/credit-card-validator
 */
public class Cartao {

    public Bandeira bandeira;
    public String numero;

    public Cartao(Bandeira bandeira){
        this.bandeira = bandeira;
    }

    public Cartao(Bandeira bandeira, String numero){
        this.bandeira = bandeira;
        this.numero = numero;
    }

    public double calculaConta(double valor){

        if (this.bandeira.equals(Bandeira.MASTERCARD)){
            if (valor <= 1000.00)
                return valor + (valor * 0.07);
            else if (valor > 1000.00 && valor <= 3000.00)
                return valor + (valor * 0.05);
            else
                return valor + (valor * 0.01);
        }
        else
            return valor + (valor * 0.05);

    }


    public static boolean validaCartaoLuhn(String numeroCartao) {
        String reverse = new StringBuilder().append(numeroCartao).reverse().toString();
        int sum = 0;
        for (int i = 0; i < numeroCartao.length(); i++) {
            int digit = Integer.parseInt("" + reverse.charAt(i));
            if (i % 2 == 1) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }
        return sum % 10 == 0;

    }

    public boolean validaBandeira(Cartao cartao) {

        final String numeroCartao = cartao.numero;
        final int tamanhoCartao = numeroCartao.length();

        /**
         * Cartão precisa passar pelo Algoritmo de Luhn
         */
//        if (!validaCartaoLuhn(numeroCartao) || tamanhoCartao < 13) {
//            return false;
//        }

        int primeiroCaracter = Integer.parseInt(numeroCartao.substring(0, 1));
        int primeiros2Caracteres = Integer.parseInt(numeroCartao.substring(0, 2));
        int primeiros3Caracteres = Integer.parseInt(numeroCartao.substring(0, 3));
        int primeiros4Caracteres = Integer.parseInt(numeroCartao.substring(0, 4));
        int primeiros6Caracteres = Integer.parseInt(numeroCartao.substring(0, 6));

        Bandeira bandeira = Bandeira.valueOf(cartao.bandeira.nome().toUpperCase());

        switch (bandeira) {
            case VISA:
                if (primeiroCaracter == 4) {
                    if (tamanhoCartao >= 13 && tamanhoCartao <= 16) {
                        return true;
                    }
                }
                break;
            case MASTERCARD:
                if (primeiros2Caracteres >= 51 && primeiros2Caracteres <= 55) {
                    if (tamanhoCartao >= 16 && tamanhoCartao <= 19) {
                        return true;
                    }
                }
                break;
            case AMEX: //number.matches("3(7|4)[0-9]{13}");
                if (primeiros2Caracteres == 34 || primeiros2Caracteres == 37) {
                    if (tamanhoCartao == 15) {
                        return true;
                    }
                }
                break;
            case HIPERCARD:
                if (numeroCartao.matches("^606282[0-9]{10}$") || numeroCartao.matches("^3841(0|4|6)0[0-9]{13}$")){
                    return true;
                }
                break;
            case DINNERS:
                break;
            case DESCONHECIDA:
                return false;

        }
        return false;
    }

}
