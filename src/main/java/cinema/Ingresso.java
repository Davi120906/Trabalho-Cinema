package cinema;
import java.util.ArrayList;
import java.util.List;

import exceptions.IngressoNaoEncontradoException;


public class Ingresso {
    private static List<Ingresso> ingressos = new ArrayList<>();
    private int vendas;
    private float valortotal;
    private float preco;
    private int idSessao;
    public Ingresso(int vendas, int idSessao, float preco){
        this.vendas = vendas;
        this.preco = preco;
        this.valortotal = vendas*preco;
        this.idSessao = idSessao;
        ingressos.add(this);
    }
    public int getId(){
        return this.idSessao;
    }
    public int getVendas(){
        return this.vendas;
    }
    public float getValor(){
        return this.preco;
    }
    public static List<Ingresso> getIngressos(){
        return ingressos;
    }
    public static Ingresso getIngressoById(int id) throws IngressoNaoEncontradoException{
        for(Ingresso ingresso: ingressos){
            if(ingresso.getId() == id){
                return ingresso;
            }
        }
        throw new IngressoNaoEncontradoException("ERRO REGISTRO DE INGRESSO NAO ENCONTRADO");
    }

    @Override
    public String toString() {
        String valorFormatado = String.format("%.2f", this.valortotal);
        String precoFormatado = String.format("%.2f", this.preco);
        return "Ingresso:\n" +
                "ID: " +this.idSessao + "\n" +
                "VALOR DO INGRESSO: " + precoFormatado + "\n" +
                "QUANTIDADE VENDIDA: " + this.vendas + "\n" +
                "Valor arrecado: " + valorFormatado;
    }
    public void setPreco(float valor){
        this.preco = valor;
        this.valortotal = this.preco * this.vendas;
    }
    public void setVendas(int quantidade){
        this.vendas = quantidade;
        this.valortotal = this.preco * this.vendas;
    }
}
