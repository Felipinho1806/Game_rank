package com.olibyte_felipinho.game_ranking;

import java.util.Scanner;

public class MyApp {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Já possui cadastro? (S/N)");
        String resposta = sc.nextLine();

        sc.close();

        if (resposta.toLowerCase().equals("n")) {
            InsertBd.main(args);
        } else {
            Conexao.main(args);
        }
    }
}