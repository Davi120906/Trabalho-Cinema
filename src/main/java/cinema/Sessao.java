package cinema;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import exceptions.IdExistenteException;

public class Sessao {
    private int idSala;
    private Filme filme;
    private int idFilme;
    private LocalDateTime horariofilme;
    private int idSessao;
    private static List<Sessao> Sessoes = new ArrayList<>();
    
    private int capacidade;
    

    public Sessao(Filme filme,int idSala, LocalDateTime horario, int capacidade){
        this.filme = filme;
        this.idSala = idSala;
        this.idFilme = filme.getId(); 
        this.horariofilme = horario;
        this.idSessao = Sessoes.size() + 1;
        try {
            this.isIdValido();
        } catch (IdExistenteException e) {
            System.out.println("ERRO:" + e.getMessage());
        }
        Sessoes.add(this);
        
        this.capacidade = capacidade;
        
    }
    public void isIdValido()throws IdExistenteException{
        for(Sessao sessao: Sessoes){
            if(sessao.getId() == this.idSessao){
                throw new IdExistenteException("ESSE ID DE SESSAO JA EXISTE");
            }
        }
    }
    public int getId(){
        return this.idSessao;
    }
    public int getIdSala(){
        return this.idSala;
    }
    public int getIdFilme(){
        return this.idFilme;
    }
    public LocalDateTime getHorario(){
        return horariofilme;
    }
    public static boolean  isHorarioValido(LocalDateTime horario, List<Sessao> sessoes){
        for(Sessao sessao: sessoes){
            LocalDateTime horariodotermino = sessao.getHorario().plusSeconds(sessao.filme.getDuration());
            if(horario.isAfter(sessao.getHorario()) && horario.isBefore(horariodotermino)){
                
             return false;
                
            }
        }
        return true;
    }

    public Filme getFilme(){
        return this.filme;
    }
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
       
        return "Sessao:\n" +
                "ID: " +this.idSessao + "\n" +
                "SALA ID: " + this.idSala + "\n" +
                "HORARIO: " +  this.horariofilme.format(formatter) + "\n" +
                "Filme: " + filme.getTitulo();
    }
    public static List<Sessao> getSessoes(){
        return Sessoes;
    }
    public int getCapacidade(){
        return this.capacidade;
    }
}
