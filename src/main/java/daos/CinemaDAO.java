package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cinema.CineartBelvedere;
import cinema.Cinema;
import cinema.CinemaPampulhaMall;
import cinema.CinemarketDelRey;
import cinema.Cineplex;
import exceptions.CinemaNaoEncontradoException;
public class CinemaDAO {
    Connection connection;
    public CinemaDAO(Connection connection){
        this.connection = connection;
    }
    public void cadastraCinema(Cinema cinema){
        String comando = "INSERT INTO Cinemas (id, nome, local) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(comando)) {
            stmt.setInt(1, cinema.getId());
            stmt.setString(2, cinema.getNome());
            stmt.setString(3, cinema.getLocal());
            stmt.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("erro na execucao do sql " + e.getMessage() );
        }
    }
    


    public CinemarketDelRey getCinemaDelRey(int id) {
        try{
            String comando = "SELECT * FROM Cinemas WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(comando)) {
                stmt.setInt(1, id);
                ResultSet resultados = stmt.executeQuery();
                if (resultados.next()) {
                    
                    String nome = resultados.getString("nome");
                    String local = resultados.getString("local");
                    CinemarketDelRey cinema = new CinemarketDelRey(nome, local);
                    return cinema;
                }else{
                    throw new CinemaNaoEncontradoException("Erro: esse cinema não está no programa!");
                }
            } catch (SQLException e) {
                System.out.println("Erro na execução do SQL: " + e.getMessage());
                return null;
            }
        } catch(CinemaNaoEncontradoException e){
            System.out.println("ERRO: " + e.getMessage());
            return null;
        }
    }
    public CineartBelvedere getCineartBelvedere(int id) {
        try{
            String comando = "SELECT * FROM Cinemas WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(comando)) {
                stmt.setInt(1, id);
                ResultSet resultados = stmt.executeQuery();
                if (resultados.next()) {
                    
                    String nome = resultados.getString("nome");
                    String local = resultados.getString("local");
                    CineartBelvedere cinema = new CineartBelvedere(nome, local);
                    return cinema;
                }else{
                    throw new CinemaNaoEncontradoException("Erro: esse cinema não está no programa!");
                }
            } catch (SQLException e) {
                System.out.println("Erro na execução do SQL: " + e.getMessage());
                return null;
            }
        } catch(CinemaNaoEncontradoException e){
            System.out.println("ERRO: " + e.getMessage());
            return null;
        }
    }

    public Cineplex getCineplex(int id) {
        try{
            String comando = "SELECT * FROM Cinemas WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(comando)) {
                stmt.setInt(1, id);
                ResultSet resultados = stmt.executeQuery();
                if (resultados.next()) {
                    
                    String nome = resultados.getString("nome");
                    String local = resultados.getString("local");
                    Cineplex cinema = new Cineplex(nome, local);
                    return cinema;
                }else{
                    throw new CinemaNaoEncontradoException("Erro: esse cinema não está no programa!");
                }
            } catch (SQLException e) {
                System.out.println("Erro na execução do SQL: " + e.getMessage());
                return null;
            }
        } catch(CinemaNaoEncontradoException e){
            System.out.println("ERRO: " + e.getMessage());
            return null;
        }
    }
    public CinemaPampulhaMall getCinemaPampulhaMall(int id) {
        try{
            String comando = "SELECT * FROM Cinemas WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(comando)) {
                stmt.setInt(1, id);
                ResultSet resultados = stmt.executeQuery();
                if (resultados.next()) {
                    
                    String nome = resultados.getString("nome");
                    String local = resultados.getString("local");
                    CinemaPampulhaMall cinema = new CinemaPampulhaMall(nome, local);
                    return cinema;
                }else{
                    throw new CinemaNaoEncontradoException("Erro: esse cinema não está no programa!");
                }
            } catch (SQLException e) {
                System.out.println("Erro na execução do SQL: " + e.getMessage());
                return null;
            }
        } catch(CinemaNaoEncontradoException e){
            System.out.println("ERRO: " + e.getMessage());
            return null;
        }
    }
    
    
}




