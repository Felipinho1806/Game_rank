

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import io.github.cdimascio.dotenv.Dotenv;

public class Conexao {
    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();
        String dbUrl = dotenv.get("DB_URL");
        String dbUser = dotenv.get("DB_USER");
        String dbPassword = dotenv.get("DB_PASSWORD");

        int estaOk = 0;
        Scanner sc = new Scanner(System.in);

        while (estaOk != 1) {
            try (Connection conexao = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {

                // Solicitar email e senha ao usuário
                System.out.println("Digite o email: ");
                String email = sc.nextLine();
                System.out.println("Digite a senha: ");
                String password = sc.nextLine();

                if (email.isEmpty() || password.isEmpty()) {
                    System.out.println("Email e senha são obrigatórios.");
                    continue;
                }

                // Consulta SQL
                String sql = "SELECT * FROM users WHERE Email = '" + email + "' AND Password = '" + password + "'";

                ResultSet resClient = conexao.createStatement().executeQuery(sql);
                if (resClient.next()) {
                    estaOk = 1;
                    System.out.println("Seja bem-vindo " + resClient.getString("FirstName"));
                } else {
                    System.out.println("Dados incorretos.");
                }

            } catch (SQLException ex) {
                System.out.println("Ocorreu um erro ao acessar o banco de dados: " + ex.getMessage());
            }
        }
        sc.close();
    }
}