package src.applicantion;

import src.db.DB;
import src.db.DbException;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Connection conn = null;
        PreparedStatement st = null;
        /*O PreparedStatement é uma interface no Java que estende a interface Statement.
         Ele é utilizado para executar comandos SQL pré-compilados com parâmetros dinâmicos,
          o que o torna mais seguro e eficiente em comparação ao Statement. Vamos detalhar como ele funciona.*/
        try {
            // EXAMPLE 1:

            /*conn = DB.getConnection(); // Estabelece a conexão com o banco de dados
            st = conn.prepareStatement(
                    "INSERT INTO SELLER " // SQL para inserir um novo vendedor
                            + "(Name, Email, Birthdate, BaseSalary, DepartmentId) "
                            + "VALUES (?, ?, ?, ?, ?)" ,// ? são placeholders para os valores
                            + Statement.RETURN_GENERATED_KEYS);// Inserção com recuperação de Id

            st.setString(1, "Carl Purple"); // Atribui o valor "Carl Purple" ao primeiro ?
            st.setString(2, "carl@gmail.com"); // Atribui o valor "carl@gmail.com" ao segundo ?
            LocalDate birthDate = LocalDate.parse("22/04/1985", fmt); // Converte a data para LocalDate
            st.setDate(3, Date.valueOf(birthDate));  // Atribui a data convertida ao terceiro ?
            st.setDouble(4, 3000.0); // Atribui o valor 3000.0 ao quarto ?
            st.setInt(5, 4); // Atribui o valor 4 ao quinto ?*/


            // EXAMPLE 2:
            st = conn.prepareStatement(
                    "insert into department (Name) values ('D1'),('D2')",
                    Statement.RETURN_GENERATED_KEYS); // Inserção de dois departamentos e retorno das chaves geradas automaticamente

            int rowsAffected = st.executeUpdate(); // Executa a inserção e retorna o número de linhas afetadas

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys(); // Pega as chaves geradas automaticamente
                while (rs.next()) {
                    int id = rs.getInt(1); // Pega o valor da primeira coluna, que é o ID gerado
                    System.out.println("Done! Id = " + id); // Imprime o ID gerado
                }
            } else {
                System.out.println("No row affected!"); // Se nenhuma linha foi afetada, imprime essa mensagem
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Em caso de erro de SQL, imprime o stack trace
        } finally {
            DB.closeStatement(st); // Fecha o PreparedStatement
            DB.closeConnection(); // Fecha a conexão
        }
    }
}
