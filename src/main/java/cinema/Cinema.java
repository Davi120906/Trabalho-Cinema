package cinema;
import java.util.ArrayList;
import java.util.List;

import exceptions.CinemaNaoEncontradoException;
import exceptions.IdExistenteException;
import exceptions.NomeDuplicadoException;
import exceptions.SalaNaoEncontradoException;

public abstract class Cinema {
    private int id;
    private String nome;
    private String local;
    private List<Sala> salas = new ArrayList<>();
    private static List<Cinema> cinemas = new ArrayList<>();
    public Cinema(int id, String nome, String local) {
        try{
        for (Cinema cinema : cinemas) {
            if(id == cinema.getId()){
                throw new IdExistenteException("Esse id ja esta cadastrado");
            }
        }
        this.id = id;
        this.nome = nome;
        this.local = local;
        }catch(IdExistenteException e){
            System.out.println("ERRO: " + e.getMessage());
        }
        cinemas.add(this);
    }
    public static void listarCinemas(){
        for (Cinema cinema : cinemas) {
            System.out.println(cinema.toString());
            System.out.println("-------------------------");
        }
    }
    public static Cinema getCinema(int i){
        return cinemas.get(i);
    }
    public static int getCinemasSize(){
        return cinemas.size();
    }
    public static Cinema getCinemabyID(int id){
        try{
            for (Cinema cinema : cinemas) {
                if(cinema.getId() == id){
                    return cinema;
                }
            }
            throw new CinemaNaoEncontradoException("ERRO ESSE ID ESTA FORA DO SISTEMA");
        }catch(CinemaNaoEncontradoException e){
            System.out.println("ERRO:" + e.getMessage());
            return null;

        }
        

    }
    @Override
    public String toString() {
        return "Cinema:\n" +
                "ID: " + id + "\n" +
                "Nome: " + nome + "\n" +
                "Local: " + local;
    }
    public int getId(){
        return this.id;
    }
    public String getNome(){
        return this.nome;
    }
    public String getLocal(){
        return this.local;
    }
    public void criarSala(String nome, int capacidade, int idSala) throws  NomeDuplicadoException{
            if(this.NomeValido(nome) == true){
                Sala sala = new Sala(nome, capacidade, this.id, idSala);
                salas.add(sala);
            }
            else{
                throw new NomeDuplicadoException("ERRO ESSE DE SALA JA ESTA NO CINEMA");
            }

    }
    public void criarSala(String nome, int idSala) throws  NomeDuplicadoException{
        if(this.NomeValido(nome) == true){
            Sala sala = new Sala(nome, 150, this.id, idSala);
            salas.add(sala);
        }
        else{
            throw new NomeDuplicadoException("ERRO ESSE DE SALA JA ESTA NO CINEMA");
        }

}
    //METODO SOBRECARREGADO SEM CAPACIDADE
    public Sala getSala(int id) throws SalaNaoEncontradoException{
            for (Sala sala : salas) {
                if (sala.getId() == id) {
                    return sala;
                }
            }
            throw new SalaNaoEncontradoException("Essa sala nao esta cadastrada no sistema");

    }
    public Sala getSalaByOrder(int id){
        return salas.get(id);
    }

    public int getSalasSize(){
        return this.salas.size();
    }

    public String listaSalas(int comeco, int fim) {
        StringBuilder sb = new StringBuilder();
        if(salas.isEmpty()){
            return null;
        }
        if (fim > salas.size()) {
            fim = salas.size();
        }
        for (int i = comeco; i < fim; i++) {
            Sala sala = salas.get(i);
            sb.append(sala.toString());
            sb.append("\n-------------------------\n");
        }
        return sb.toString(); 
    }
    //METODO PRA IMPRIMIR TODAS AS SALAS NO TERMINAL
    public void listarSalas(){
        for(Sala sala: salas){
            System.out.println(sala.toString());
            System.out.println("\n---------------------------------------------------------------\n");
        }

    }
      
    public List<Sala> getSalas(){
        return this.salas;
    }
    public boolean NomeValido(String nome){
        for(Sala sala: salas){
            if (sala.getNome().equals(nome))
            return false;
        }
        return true;
    }

    
}