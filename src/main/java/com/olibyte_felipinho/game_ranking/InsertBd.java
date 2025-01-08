package com.olibyte_felipinho.game_ranking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import io.github.cdimascio.dotenv.Dotenv;

public class InsertBd {

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();
        String dbUrl = dotenv.get("DB_URL");
        String dbUser = dotenv.get("DB_USER");
        String dbPassword = dotenv.get("DB_PASSWORD");

        int estaOk = 0;
        Scanner sc = new Scanner(System.in);

        while (estaOk != 1) {

            Connection conexao = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            System.out.println("Digite o nome:");
            String FirstName = sc.nextLine();
            System.out.println("Digite o sobrenome:");
            String LastName = sc.nextLine();
            System.out.println("Digite o email:");
            String Email = sc.nextLine();
            System.out.println("Digite a senha:");
            String Password = sc.nextLine();

            try {
                // Carregar o driver do MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Estabelecer a conexão com o banco de dados
                conexao = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

                String verificacaoEmail = "SELECT COUNT(*) FROM users WHERE Email = ?";
                pstmt = conexao.prepareStatement(verificacaoEmail);
                pstmt.setString(1, Email);
                rs = pstmt.executeQuery();

                rs.next();
                if (rs.getInt(1) > 0) {
                    System.out.println("Esse email já está cadastrado!");

                } else {
                    estaOk = 1;

                    // Criar a instrução SQL para inserir dados
                    String inserirSQL = "INSERT INTO users (FirstName, LastName, Email, Password) VALUES (?, ?, ?, ?)";

                    // Criar o PreparedStatement
                    pstmt = conexao.prepareStatement(inserirSQL);

                    // Definir os valores dos parâmetros
                    pstmt.setString(1, FirstName); // 1º parâmetro
                    pstmt.setString(2, LastName); // 2º parâmetro
                    pstmt.setString(3, Email); // 3º parâmetro
                    pstmt.setString(4, Password); // 4º parâmetro

                    // Executar a instrução SQL
                    int linhasAfetadas = pstmt.executeUpdate();

                    // Exibir mensagem de sucesso
                    if (linhasAfetadas > 0) {
                        System.out.println("Nova linha inserida com sucesso!");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                    if (conexao != null) {
                        conexao.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        sc.close();
    }
}
