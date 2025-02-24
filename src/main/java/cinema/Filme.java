package cinema;
import java.util.ArrayList;
import java.util.List;

public class Filme {
    private int id;
    private String titulo;
    private static List<Filme> filmes = new ArrayList<>();
    //DURACAO EM SEGUNDOS
    private long duration;

    public Filme(String titulo, long duration){
        this.id = filmes.size() + 1;
        this.titulo = titulo;
        this.duration = duration;
        filmes.add(this);
    }
    public long getDuration(){
        return this.duration;
    }
    public int getId(){
        return this.id;
    }
    public String getTitulo(){
        return this.titulo;
    }
    public static String listaFilmes(int comeco, int fim) {
        StringBuilder sb = new StringBuilder();
        if(filmes.isEmpty()){
            return null;
        }
        if (fim > filmes.size()) {
            fim = filmes.size();
        }
        for (int i = comeco; i < fim; i++) {
            Filme filme = filmes.get(i);
            sb.append(filme.toString());
            sb.append("\n-------------------------\n");
        }
        return sb.toString(); 
    }

    @Override
    public String toString() {
        return "Filme:\n" +
                "ID do filme: " + id + "\n" +
                "Titulo: " + titulo + "\n" +
                "Duracao: " + duration;
    }


    public static Filme getFilmeById(int id){
        for(Filme filme : filmes){
            if (filme.getId() == id){
                return filme;
            }
        }
        return null;
    }

}
