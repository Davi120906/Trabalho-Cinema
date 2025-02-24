package exceptions;


public class IngressoNaoEncontradoException extends Exception{
    public IngressoNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}