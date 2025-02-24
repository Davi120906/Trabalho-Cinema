package daos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cinema.Cinema;
import cinema.Sala;
import exceptions.NomeDuplicadoException;
public class SalaDAO {
    private Connection connection;

    public SalaDAO(Connection connection) {
        this.connection = connection;
    }
    public int getNextId() {
        String query = "SELECT MAX(idSala) AS maxId FROM Salas";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("maxId") + 1; // Retorna o próximo ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // Caso não haja nenhuma sala, começa com ID 1
    }
    public void cadastrarSala(Sala sala){
        String comando = "INSERT INTO Salas (id, nome, capacidade, idCinema) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(comando)) {
            stmt.setInt(1, sala.getId());
            stmt.setString(2, sala.getNome());
            stmt.setInt(3, sala.getCapacidade());
            stmt.setInt(4, sala.getCinemaId());
            stmt.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("erro na execucao do sql " + e.getMessage() );
        }
    }
    //ESSA FUNCAO PEGA TODAS AS SALAS DE UM CINEMA
    public void getSalas(Cinema cinema){
        String comando = "SELECT * FROM Salas WHERE idCinema = ? ORDER BY id";
            try (PreparedStatement stmt = connection.prepareStatement(comando)) {
                stmt.setInt(1, cinema.getId());
                ResultSet resultados = stmt.executeQuery();
                while (resultados.next()) {
                    int id = resultados.getInt("id");
                    String nome = resultados.getString("nome");
                    int capacidade = resultados.getInt("capacidade");
                    try{
                    cinema.criarSala(nome, capacidade, id);
                    } catch(NomeDuplicadoException e){
                        System.out.println("ERRO:" + e.getMessage());
                    }
                    
                    
                }
            } catch (SQLException e) {
                System.out.println("Erro na execução do SQL: " + e.getMessage());
            }

    }
}
