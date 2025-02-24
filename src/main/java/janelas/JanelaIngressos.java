package janelas;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import cinema.Ingresso;
import cinema.Sala;
import daos.IngressoDAO;
import exceptions.CapacidadeMaximaSala;
import exceptions.IngressoNaoEncontradoException;
import exceptions.SessaoNaoEncontradoException;

public class JanelaIngressos {
    private static int returno;

    public static int mostrarOpcoes(JFrame parentFrame) {
        returno = 0;
        JDialog dialog = new JDialog(parentFrame, "Escolha uma Opcao", true);
        dialog.setSize(600, 300);
        dialog.setLayout(null);

        JLabel mensagem = new JLabel("DESEJA ATUALIZAR OS INGRESSOS?");
        mensagem.setBounds(100, 40, 400, 60);
        dialog.add(mensagem);

        JButton botao1 = new JButton("SIM");
        botao1.setBounds(100, 140, 160, 60);
        dialog.add(botao1);
        botao1.addActionListener(e -> {
            definereturno(1);
            dialog.dispose();
        });

        JButton botao2 = new JButton("NAO");
        botao2.setBounds(300, 140, 160, 60);
        dialog.add(botao2);
        botao2.addActionListener(e -> {
            definereturno(2);
            dialog.dispose();
        });

        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);

        return returno;
    }
    public static void definereturno(int numero) {
        returno = numero;
    }
    
    public static void atualizarIngresso(JFrame frame, Sala sala, IngressoDAO ingressoDAO) {

       
        JDialog dialog = new JDialog(frame, "Informe os dados", true);
        dialog.setLayout(new GridLayout(4, 2));  
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(frame); 

      
        JLabel labelInt1 = new JLabel("ID DA SESSAO: ");
        JTextField campoInt1 = new JTextField();
        JLabel labelFloat = new JLabel("PREÇO DO INGRESSO: ");
        JTextField campoFloat = new JTextField();
        JLabel labelInt2 = new JLabel("QUANTIDADE VENDIDA: ");
        JTextField campoInt2 = new JTextField();

   
        JButton botaoOk = new JButton("OK");

   
        botaoOk.addActionListener(e -> {
            try {
             
                int idIngresso  = Integer.parseInt(campoInt1.getText());
                float valor = Float.parseFloat(campoFloat.getText());
                int vendas = Integer.parseInt(campoInt2.getText());
                
                if(vendas > sala.getSessao(idIngresso).getCapacidade()){
                    throw new CapacidadeMaximaSala("Voce Inseriu uma venda de ingressos maior que a capacidade da sala:" + sala.getCapacidade() );
                }
                Ingresso ingresso = Ingresso.getIngressoById(idIngresso);
                ingresso.setPreco(valor);
                ingresso.setVendas(vendas);
                ingressoDAO.atualizarIngresso(ingresso);

                

                

              
                dialog.dispose();
            } catch (NumberFormatException ex) {
               
                JOptionPane.showMessageDialog(dialog, "Por favor, insira valores válidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (SessaoNaoEncontradoException es){
                JOptionPane.showMessageDialog(dialog, es.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (CapacidadeMaximaSala ea){
                JOptionPane.showMessageDialog(dialog, ea.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (IngressoNaoEncontradoException ed){
                JOptionPane.showMessageDialog(dialog, ed.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

       
        dialog.add(labelInt1);
        dialog.add(campoInt1);
        dialog.add(labelFloat);
        dialog.add(campoFloat);
        dialog.add(labelInt2);
        dialog.add(campoInt2);
        dialog.add(botaoOk);

        dialog.setVisible(true);
    }

}
