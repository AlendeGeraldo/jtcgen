package br.com.caelum.leilao.servico;

public class Palindromo {

    public boolean ehPalindromo(String frase) {

        String fraseFiltrada = frase
                .toUpperCase().replace(" ", "").replace("-", "");

        for(int i = 1; i < fraseFiltrada.length(); i++) {
            if(fraseFiltrada.charAt(i-1) != 
                    fraseFiltrada.charAt(fraseFiltrada.length() - i)) {
                return false;
            }
        }

        return true;
    }
}