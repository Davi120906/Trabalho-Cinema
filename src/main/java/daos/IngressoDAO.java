
package daos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cinema.Ingresso;


public class IngressoDAO {
    private Connection connection;
    public IngressoDAO(Connection connection){
        this.connection = connection;
    }
    public void cadastraIngresso(Ingresso ingresso){
        String comando = "INSERT INTO Ingressos (idSessao, quantidade, valor) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(comando)) {
            stmt.setInt(1,ingresso.getId());
            stmt.setInt(2,ingresso.getVendas());
            stmt.setFloat(3,ingresso.getValor());
            stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println("erro na execucao do sql " + e.getMessage() );
        }
    }
    public void getIngressos(){
        String comando = "SELECT * FROM Ingressos";
        try (PreparedStatement stmt = connection.prepareStatement(comando)) {
            ResultSet resultados = stmt.executeQuery();
           
            while (resultados.next()) {
                int id = resultados.getInt("idSessao");
                int quantidade = resultados.getInt("quantidade");
                float valor = resultados.getFloat("valor");
                Ingresso ingresso = new Ingresso(quantidade, id, valor);
    
            }
        }catch(SQLException e){
            System.out.println("ERRO:" + e.getMessage());
        }
    }
    public void atualizarIngresso(Ingresso ingresso){
        String sql = "UPDATE Ingressos SET quantidade = ?, valor = ? WHERE idSessao = ? ";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(3,ingresso.getId());
            stmt.setInt(1,ingresso.getVendas());
            stmt.setFloat(2,ingresso.getValor());
            stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println("erro na execucao do sql " + e.getMessage() );
        }
    }
}
