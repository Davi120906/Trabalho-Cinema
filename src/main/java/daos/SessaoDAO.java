package daos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import cinema.Filme;
import cinema.Sessao;
public class SessaoDAO {
    private Connection connection;
    public SessaoDAO(Connection connection){
        this.connection = connection;
    }
    public void cadastraSessao(Sessao sessao){
        String comando = "INSERT INTO Sessoes (id, idSala,idFilme, horarioFilme, capacidade) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(comando)) {
            stmt.setInt(1,sessao.getId());
            stmt.setInt(2,sessao.getIdSala());
            stmt.setInt(3,sessao.getIdFilme());
            
            stmt.setTimestamp(4,Timestamp.valueOf(sessao.getHorario()));
            stmt.setInt(5,sessao.getCapacidade());
            stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println("erro na execucao do sql " + e.getMessage() );
        }
    }
    public void getSessoes(){
        String comando = "SELECT * FROM Sessoes";
        try (PreparedStatement stmt = connection.prepareStatement(comando)) {
            ResultSet resultados = stmt.executeQuery();
           
            while (resultados.next()) {
                int id = resultados.getInt("id");
                int idSala = resultados.getInt("idSala");
                int idFilme = resultados.getInt("idFilme");
                int capacidade = resultados.getInt("capacidade");
                Timestamp horas = resultados.getTimestamp("horarioFilme");
                LocalDateTime horario = horas.toLocalDateTime();
                Sessao sessao = new Sessao(Filme.getFilmeById(idFilme), idSala, horario, capacidade);
            }
        }catch(SQLException e){
            System.out.println("ERRO:" + e.getMessage());
        }
        
    }
}

