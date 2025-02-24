import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/DataBaseCinema";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    public static void criarTabelaSeNaoExistir(Connection connection) {
        String sqlCriaTabelaCinema = "CREATE TABLE IF NOT EXISTS Cinemas (" +
                                     "id INT NOT NULL PRIMARY KEY, " +
                                     "nome VARCHAR(100) NOT NULL, "+
                                     "local VARCHAR(100) NOT NULL )";
        String sqlCriaTabelaSala = "CREATE TABLE IF NOT EXISTS Salas (" +
                                     "id INT NOT NULL PRIMARY KEY, " +
                                     "nome VARCHAR(100) NOT NULL, "+
                                     "capacidade INT NOT NULL, " +
                                     "idCinema INT, " +
                                     "FOREIGN KEY (idCinema) REFERENCES Cinemas(id) ON DELETE CASCADE)";
        String sqlCriaTabelaFilme = "CREATE TABLE IF NOT EXISTS Filmes ("+
                                    "id INT NOT NULL PRIMARY KEY, " +
                                    "titulo VARCHAR(100) NOT NULL, "+
                                    "duracao INT NOT NULL )";
        String sqlCriaTabelaSessoes = "CREATE TABLE IF NOT EXISTS Sessoes (" +
                                    "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                                    "idSala INT NOT NULL, " +
                                    "capacidade INT NOT NULL, " +
                                    "idFilme INT NOT NULL, " +
                                    "horarioFilme DATETIME NOT NULL, " +
                                    "FOREIGN KEY (idSala) REFERENCES Salas(id) ON DELETE CASCADE, " +
                                    "FOREIGN KEY (idFilme) REFERENCES Filmes(id) ON DELETE CASCADE" +
                                    ");";
        String sqlCriaTabelaIngressos = "CREATE TABLE IF NOT EXISTS Ingressos (" +
                                    "idSessao INT NOT NULL, " +
                                    "quantidade INT NOT NULL, " +
                                    "valor FLOAT NOT NULL, "+
                                    "FOREIGN KEY (idSessao) REFERENCES Sessoes(id) ON DELETE CASCADE" +
                                    ");";
    
        try (PreparedStatement stmtCriaCinema = connection.prepareStatement(sqlCriaTabelaCinema);
            PreparedStatement stmtCriaSala = connection.prepareStatement(sqlCriaTabelaSala);
            PreparedStatement stmtCriaFilme = connection.prepareStatement(sqlCriaTabelaFilme);
            PreparedStatement stmtCriaSessao = connection.prepareStatement(sqlCriaTabelaSessoes);
            PreparedStatement stmtCriaIngresso = connection.prepareStatement(sqlCriaTabelaIngressos)) {
            stmtCriaCinema.executeUpdate();
            stmtCriaSala.executeUpdate();
            stmtCriaFilme.executeUpdate();
            stmtCriaSessao.executeUpdate();
            stmtCriaIngresso.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao criar as tabelas: " + e.getMessage());
        }
    }

    public static Connection getConnection(){
        try{
        return DriverManager.getConnection(URL, USER, PASSWORD);
        }catch(SQLException e){
            System.out.println("Erro ao conectar: " + e.getMessage());
            return null;
        }
    }
}
