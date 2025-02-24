package janelas;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import cinema.Cinema;
import daos.IngressoDAO;
import daos.SessaoDAO;
import exceptions.FilmeNaoEncontradoException;
import exceptions.JanelaFechadaException;
import exceptions.MaximoSessoesException;
import exceptions.SalaNaoEncontradoException;
import exceptions.SalaOcupadaException;
public class JanelaSessoes {
    private static int returno;

    public static int mostrarOpcoes(JFrame parentFrame) {
        returno = 0;
        JDialog dialog = new JDialog(parentFrame, "Escolha uma Opcao", true);
        dialog.setSize(600, 300);
        dialog.setLayout(null);

        JLabel mensagem = new JLabel("Escolha uma opção:");
        mensagem.setBounds(100, 40, 400, 60);
        dialog.add(mensagem);

        JButton botao1 = new JButton("CADASTRAR");
        botao1.setBounds(100, 140, 160, 60);
        dialog.add(botao1);
        botao1.addActionListener(e -> {
            definereturno(1);
            dialog.dispose();
        });

        JButton botao2 = new JButton("LISTAR SESSOES");
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


    public static String pegarSala(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Insira a Sala", true);
        dialog.setSize(400, 200);
        dialog.setLayout(null);

        JLabel label = new JLabel("Digite o nome da sala:");
        label.setBounds(0, 30, 150, 30);
        dialog.add(label);

        JTextField campoTexto = new JTextField();
        campoTexto.setBounds(150, 30, 200, 30);
        dialog.add(campoTexto);

        JButton confirmar = new JButton("Confirmar");
        confirmar.setBounds(100, 100, 100, 30);
        dialog.add(confirmar);
        confirmar.addActionListener(e -> {
            dialog.dispose();
        });

        JButton cancelar = new JButton("Cancelar");
        cancelar.setBounds(220, 100, 100, 30);
        dialog.add(cancelar);
        cancelar.addActionListener(e -> {
            campoTexto.setText("");
            dialog.dispose();
        });

        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);

        return campoTexto.getText();
    }

    public static void cadastrarSessao(JFrame parentFrame, int idcinema, SessaoDAO sessaoDAO, IngressoDAO ingressoDAO) 
        throws JanelaFechadaException {
    
    JDialog dialog = new JDialog(parentFrame, "Inserir Dados", true);
    dialog.setSize(400, 250);
    dialog.setLayout(new BorderLayout());

    // Painel principal com os campos de entrada
    JPanel painel = new JPanel();
    painel.setLayout(new GridLayout(3, 2));

    JLabel labelInt1 = new JLabel("ID DA SALA:");
    JTextField campoInt1 = new JTextField();
    painel.add(labelInt1);
    painel.add(campoInt1);

    JLabel labelData = new JLabel("Data e Hora (dd/MM/yyyy HH:mm):");
    JSpinner spinnerData = new JSpinner(new SpinnerDateModel());
    JSpinner.DateEditor editorData = new JSpinner.DateEditor(spinnerData, "dd/MM/yyyy HH:mm");
    spinnerData.setEditor(editorData);
    painel.add(labelData);
    painel.add(spinnerData);

    JLabel labelInt2 = new JLabel("ID DO FILME:");
    JTextField campoInt2 = new JTextField();
    painel.add(labelInt2);
    painel.add(campoInt2);

    dialog.add(painel, BorderLayout.CENTER);

    JPanel painelBotoes = new JPanel();
    JButton confirmar = new JButton("Confirmar");
    JButton cancelar = new JButton("Cancelar");
    painelBotoes.add(confirmar);
    painelBotoes.add(cancelar);
    dialog.add(painelBotoes, BorderLayout.SOUTH);

    
    final boolean[] foiConfirmado = {false};

    confirmar.addActionListener(e -> {
        foiConfirmado[0] = true; 
        dialog.dispose();
    });

    cancelar.addActionListener(e -> dialog.dispose());

 
    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
            dialog.dispose();
        }
    });

    dialog.setLocationRelativeTo(parentFrame);
    dialog.setVisible(true);

   
    if (!foiConfirmado[0]) {
        throw new JanelaFechadaException("A janela foi fechada ou o usuário cancelou a operação.");
    }

    // Obtendo os valores inseridos
    int idSala = Integer.parseInt(campoInt1.getText());
    LocalDateTime dataHoraSelecionada = ((java.util.Date) spinnerData.getValue())
            .toInstant()
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalDateTime();
    int idfilme = Integer.parseInt(campoInt2.getText());

    try {
        if (Cinema.getCinemabyID(idcinema).getSala(idSala).getSessoesSize() < 20) {
            Cinema.getCinemabyID(idcinema).getSala(idSala).criarSessao(idfilme, dataHoraSelecionada, sessaoDAO, ingressoDAO);
        } else {
            throw new MaximoSessoesException("ERRO: Você não pode ter mais de 20 sessões registradas em uma sala.");
        }
    } catch (SalaNaoEncontradoException e) {
        JOptionPane.showMessageDialog(null, "Nao Encontramos essa sala nesse cinema.", "Erro", JOptionPane.ERROR_MESSAGE);
    } catch (SalaOcupadaException ep) {
        JOptionPane.showMessageDialog(null, ep.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    } catch (FilmeNaoEncontradoException esa) {
        JOptionPane.showMessageDialog(null, esa.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    } catch (MaximoSessoesException ema) {
        JOptionPane.showMessageDialog(null, ema.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
}




    public static void definereturno(int numero) {
        returno = numero;
    }
}
