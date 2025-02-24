package cinema;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import daos.IngressoDAO;
import daos.SessaoDAO;
import exceptions.FilmeNaoEncontradoException;
import exceptions.SalaOcupadaException;
import exceptions.SessaoNaoEncontradoException;
public class Sala {
    private int idSala;
    private String nome;
    private int capacidade;
    private int idCinema;
    private static int numSalas = 0;
    private List<Sessao> sessoes = new ArrayList<>();
    private List<Ingresso> ingressos = new ArrayList<>();
  
    

    public Sala(String nome, int capacidade, int idCinema, int idSala){
        this.idSala = idSala;
        numSalas++;
        this.nome = nome;
        this.capacidade = capacidade;
        this.idCinema = idCinema;
        this.getSessoes();
        this.getIngressos();

    }
    public int getId(){
        return this.idSala;
    }
    
    public String getNome(){
        return this.nome;
    }
    public int getCapacidade(){
        return this.capacidade;        
    }
    public int getCinemaId(){
        return this.idCinema;
    }
    @Override
    public String toString() {
        return "Sala:\n" +
                "ID da Sala: " + idSala + "\n" +
                "Nome: " + nome + "\n" +
                "Capacidade: " + capacidade + "\n" +
                "ID do Cinema: " + idCinema;
    }
    public void criarSessao(int idfilme, LocalDateTime horario, SessaoDAO sessaoDAO, IngressoDAO ingressoDAO) throws SalaOcupadaException, FilmeNaoEncontradoException{
        if(Sessao.isHorarioValido(horario, this.sessoes)){
            Filme filme = Filme.getFilmeById(idfilme);
            if (filme == null){
             throw new FilmeNaoEncontradoException("ERRO ESSE FILME NAO ESTA CADASTRADO");
        }
            
            Sessao sessao = new Sessao(filme, this.idSala, horario, this.capacidade);
            System.out.println(sessao.toString());
            System.out.println("\n------------------------\n");
            sessaoDAO.cadastraSessao(sessao);
            sessoes.add(sessao);
            this.ordenarSessoes();
            Random random = new Random();
            //CRIA UM VALOR DE INGRESSOS ALEATORIO E UM VALOR ATORIO DE QUANTOS VENDERAM MENOR QUE A CAPACIDADE
            Ingresso ingresso = new Ingresso(random.nextInt(capacidade + 1), sessao.getId(),  (random.nextFloat(10) + 20));
            ingressos.add(ingresso);
            ingressoDAO.cadastraIngresso(ingresso);
            
        }
        else{
            throw new SalaOcupadaException("ESSA SALA JA TEM UMA SESSAO NESSE HORARIO");
        }
            
        
    }

    //METODO SOBRECARREGADO QUE CRIA UMA SESSAO 30 MINUTOS POS A ULTIMA
    public void criarSessao(int idfilme, SessaoDAO sessaoDAO, IngressoDAO ingressoDAO) throws  FilmeNaoEncontradoException{
        
        //Como a lista é ordenada pelo horario ultimo elemento sera o ultimo horario
        Sessao ultimasessao = sessoes.get(sessoes.size()-1);
        LocalDateTime horario = ultimasessao.getHorario().plusSeconds(ultimasessao.getFilme().getDuration());
        horario.plusMinutes(30);
        Sessao sessao = new Sessao(Filme.getFilmeById(idfilme), this.idSala, horario, this.capacidade);
        //IMPRIME A NOVA SESSAO CRIADA NO TERMINAL
        System.out.println(sessao.toString());
        System.out.println("\n------------------------\n");
        sessaoDAO.cadastraSessao(sessao);
        sessoes.add(sessao);
        this.ordenarSessoes();
        Random random = new Random();
        Ingresso ingresso = new Ingresso(random.nextInt(capacidade + 1), sessao.getId(),  (random.nextFloat(10) + 20));
        ingressos.add(ingresso);
        ingressoDAO.cadastraIngresso(ingresso);
            
        
    }
    //PEGA TODAS AS SESSOES QUE SAO DA SALA
    public void getSessoes(){
     
        for(Sessao sessao: Sessao.getSessoes()){
            if(sessao.getIdSala() == this.idSala){
                this.sessoes.add(sessao);
            }
        }

        this.ordenarSessoes();
    }
    public void getIngressos(){ 

        for(Ingresso ingresso: Ingresso.getIngressos()){
            for(Sessao sessao: sessoes){
                if(ingresso.getId() == sessao.getId()){
                    ingressos.add(ingresso);
                }
            }
        }
    }

    public Sessao getSessao(int id) throws SessaoNaoEncontradoException{
        for(Sessao sessao: sessoes){
            if(sessao.getId() == id){
                return sessao;
            }
        }
        throw new SessaoNaoEncontradoException("ERRO ESSE ID E INVALIDO");
        
    }
     public void ordenarSessoes(){
        sessoes.sort(Comparator.comparing(Sessao::getHorario));
     }
     public int getSessoesSize(){
        return this.sessoes.size();
     }


    public String listaSessoes(int comeco, int fim) {
        StringBuilder sb = new StringBuilder();
        if(sessoes.isEmpty()){
            return null;
        }
        if (fim > sessoes.size()) {
            fim = sessoes.size();
        }
        for (int i = comeco; i < fim; i++) {
            Sessao sessao = sessoes.get(i);
            sb.append(sessao.toString());
            sb.append("\n-------------------------\n");
        }
        return sb.toString(); 
    }
    public String listaIngressos(int comeco, int fim) {
        StringBuilder sb = new StringBuilder();
        if(ingressos.isEmpty()){
            return null;
        }
        if (fim > ingressos.size()) {
            fim = ingressos.size();
        }
        for (int i = comeco; i < fim; i++) {
            Ingresso ingresso = ingressos.get(i);
            sb.append(ingresso.toString());
            sb.append("\n-------------------------\n");
        }
        return sb.toString(); 
    }

    
    public static int getNumSalas(){
        return numSalas;
    }
}
