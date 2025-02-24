package daos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cinema.Filme;

public class FilmeDao {
    private Connection connection;

    public FilmeDao(Connection connection) {
        this.connection = connection;
    }
    public void cadastraFilme(Filme filme){
        String comando = "INSERT INTO Filmes (id, titulo, duracao) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(comando)) {
            stmt.setInt(1,filme.getId());
            stmt.setString(2,filme.getTitulo());
            stmt.setLong(3,filme.getDuration());
            stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println("erro na execucao do sql " + e.getMessage() );
        }
    }
    public void getFilmes(){
        String comando = "SELECT * FROM Filmes";
        try (PreparedStatement stmt = connection.prepareStatement(comando)) {
            ResultSet resultados = stmt.executeQuery();
            while (resultados.next()) {
                String nome = resultados.getString("titulo");
                long duration = resultados.getLong("duracao");
                new Filme(nome, duration);
                
            }
        }catch(SQLException e){
            System.out.println("ERRO:" + e.getMessage());
        }
    }
}
