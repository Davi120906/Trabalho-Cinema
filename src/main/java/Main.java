import java.sql.Connection;
import java.time.LocalDateTime;

import cinema.*;
import daos.*;
import exceptions.*;
import janelas.Janela;


public class Main {
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection(); 
        DatabaseConnection.criarTabelaSeNaoExistir(connection);
        System.out.println("Conexao bem sucedida!");
        CinemaDAO cinemaDAO = new CinemaDAO(connection);
        SalaDAO salaDAO  = new SalaDAO(connection);
        FilmeDao filmeDAO = new FilmeDao(connection);
        SessaoDAO sessaoDAO = new SessaoDAO(connection);
        IngressoDAO ingressoDAO = new IngressoDAO(connection);
        CineartBelvedere cinemao;
        Cineplex cinepao;
        CinemaPampulhaMall cinemapamps;
        CinemarketDelRey cineminha = cinemaDAO.getCinemaDelRey(1402);
        //SE E NULO PROGRAMA NAO TEM NADA IGNORAR O ERRO
        if(cineminha == null){
            try{
                cineminha = new CinemarketDelRey("CineMarket", "Shopping Del Rey");
                cinemao = new CineartBelvedere("CineArt", "Shopping Bel Vedere");
                cinepao = new Cineplex("CinePlex", "Shopping DIAMOND");
                cinemapamps = new CinemaPampulhaMall("CinePamps", "Shopping Pampulha mall");
                cinemaDAO.cadastraCinema(cinemao);
                cinemaDAO.cadastraCinema(cineminha);
                cinemaDAO.cadastraCinema(cinemapamps);
                cinemaDAO.cadastraCinema(cinepao);
                //CRIA ALGUMAS SALAS
                
                cineminha.criarSala("Sala 1",Sala.getNumSalas()+1);
                cineminha.criarSala("Sala 2 VIP", 60,Sala.getNumSalas()+1);
                cinemao.criarSala("SALA 1", 200,Sala.getNumSalas()+1);
                cinemapamps.criarSala("SALA A", 134,Sala.getNumSalas()+1);
                cinepao.criarSala("SALA LESTE",Sala.getNumSalas()+1);
                //CADASTRA TODAS
                for(Sala sala: cineminha.getSalas()){
                    salaDAO.cadastrarSala(sala);
                }
                for(Sala sala: cinemao.getSalas()){
                    
                    salaDAO.cadastrarSala(sala);
                }
                for(Sala sala: cinepao.getSalas()){
                    salaDAO.cadastrarSala(sala);
                }
                for(Sala sala: cinemapamps.getSalas()){
                    salaDAO.cadastrarSala(sala);
                }
                
            //CRIA E CADASTRA ALGUNS FILMES
            filmeDAO.cadastraFilme(new Filme("Blade Runner 2049", 9120));
            filmeDAO.cadastraFilme(new Filme("TITANIC", 11640));
            filmeDAO.cadastraFilme(new Filme("ESQUECERAM DE MIM", 6180));
            filmeDAO.cadastraFilme(new Filme("E o vento levou",14400));

            //CRIA ALGUMAS SESSOES
            //A PRIMEIRA SESSAO E CRIADA AGORA E A SEGUNDA 30 MINUTOS DEPOIS DO TERMINO DA PRIMEIRA
            cineminha.getSalaByOrder(1).criarSessao(1, LocalDateTime.now(),sessaoDAO, ingressoDAO);
            cineminha.getSalaByOrder(1).criarSessao(2, sessaoDAO, ingressoDAO);


            cinepao.getSalaByOrder(0).criarSessao(3, LocalDateTime.now(),sessaoDAO, ingressoDAO);
            cinemao.getSalaByOrder(0).criarSessao(2, LocalDateTime.now(),sessaoDAO, ingressoDAO);
            cinemapamps.getSalaByOrder(0).criarSessao(1, LocalDateTime.now(),sessaoDAO, ingressoDAO);
            cinemapamps.getSalaByOrder(0).criarSessao(4,sessaoDAO, ingressoDAO);
            }catch(NomeDuplicadoException e){
                System.out.println("ERRO:" + e.getMessage());
            }catch(FilmeNaoEncontradoException ea){
                System.out.println("ERRO:" + ea.getMessage());
            }catch(SalaOcupadaException es){
                System.out.println("ERRO:" + es.getMessage());
            }
        }
        else{
            cinemao = cinemaDAO.getCineartBelvedere(325);
            cinemapamps = cinemaDAO.getCinemaPampulhaMall(2005);
            cinepao = cinemaDAO.getCineplex(1209);
            
            ingressoDAO.getIngressos();
            filmeDAO.getFilmes();
            sessaoDAO.getSessoes();
            
            salaDAO.getSalas(cineminha);
            salaDAO.getSalas(cinemao);
            salaDAO.getSalas(cinemapamps);
            salaDAO.getSalas(cinepao);
            
        }
        //CHAMANDO ALGUMAS FUNCOES PRA IMPRIMIR NO TERMINAL
        Cinema.listarCinemas();
        //VAI IMPRIMIR AS SALAS DO SHOPPING DEL REY
        Cinema.getCinemabyID(1402).listarSalas();
        new Janela(salaDAO, filmeDAO, sessaoDAO, ingressoDAO);

        
        
        
        
       

        

    }
}
