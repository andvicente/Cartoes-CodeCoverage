package edu.exemplo.testesunitarios.dominio;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class CartaoTest {


    /**
     * Mastercard
     *      Valor <= 1000.00 (7%)
     *      Valor > 1000.00 && Valor <= 3000.00 (5%)
     *      Outros casos: 1%
     * Outras Bandeiras
     *      Qualquer valor: 5%
     */
    @Test
    public void testContaMasterCard(){
        Cartao cartao = new Cartao(Bandeira.MASTERCARD);
        assertEquals(cartao.calculaConta(100.00),107.00,0.01);
        assertEquals(cartao.calculaConta(1001.00),1051.05,0.01);
        assertEquals(cartao.calculaConta(3001.10),3031.11,0.01);
    }

    @Test
    public void testContaAMEX(){
        Cartao cartao = new Cartao(Bandeira.AMEX);
    }

    @Test
    public void testCartaoInvalido(){
        Cartao cartao = new Cartao(Bandeira.AMEX,"388835103034823");

        assertFalse(cartao.validaCartaoLuhn(cartao.numero));
    }

    @Test
    public void testValidaCartaoVisa(){
        Cartao cartao = new Cartao(Bandeira.VISA,"4485767725016414");

        assertTrue(cartao.validaCartaoLuhn(cartao.numero));
        assertTrue("Esperava um cartão " + Bandeira.VISA.toString(), cartao.validaBandeira(cartao));

    }

    @Test
    public void testValidaCartaoVisaInformandoAmex(){

    }

    @Test
    public void testValidaCartaoNaoVisa11Caracteres(){

    }

    @Test
    public void testValidaCartaoNaoVisa17Caracteres(){

    }


    @Test
    public void testValidaCartaoAmex(){

    }

    @Test
    public void testValidaCartaoAmexInformandoMaster(){

    }

    @Test
    public void testValidaCartaoAmexCartaoMaior(){

    }


    @Test
    public void testCartaoDinners(){

    }

    @Test
    public void verificaCartaoHipercard() throws Exception {
        List<Cartao> cartoesHipercard = new ArrayList<>();
        cartoesHipercard.add(new Cartao(Bandeira.HIPERCARD,"6062825624254001"));
        cartoesHipercard.add(new Cartao(Bandeira.HIPERCARD,"3841001111222233334"));
        cartoesHipercard.add(new Cartao(Bandeira.HIPERCARD,"3841401111222233334"));
        cartoesHipercard.add(new Cartao(Bandeira.HIPERCARD,"3841601111222233334"));
        for (Cartao cartao : cartoesHipercard) {
            assertEquals(true,cartao.validaBandeira(cartao));
        }
    }



}
