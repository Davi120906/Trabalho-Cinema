package janelas;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cinema.Cinema;
import cinema.Filme;
import cinema.Sala;
import daos.FilmeDao;
import daos.IngressoDAO;
import daos.SalaDAO;
import daos.SessaoDAO;
import exceptions.JanelaFechadaException;
import exceptions.MaximoSalasException;
import exceptions.NomeDuplicadoException;
import exceptions.SalaNaoEncontradoException;


public class JanelaCinema {
    SalaDAO salaDAO;
    public JanelaCinema(int idCinema, SalaDAO salaDAO, FilmeDao filmeDAO, SessaoDAO sessaoDAO, IngressoDAO ingressoDAO) {
        this.salaDAO = salaDAO;
       
        JFrame janela = new JFrame("Gestão do Cinema");
        janela.setSize(800, 600);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        janela.setLayout(null);

       
        JPanel painel = new JPanel();
        painel.setLayout(null);
        painel.setBounds(0, 0, 800, 465);
        janela.add(painel);

        JLabel texto = new JLabel("");
        texto.setBounds(0, -80, 700, 600); 
        painel.add(texto);

        JLabel texto2 = new JLabel("");
        texto2.setBounds(170, -80, 700, 600); 
        painel.add(texto2);

        JLabel texto3 = new JLabel("");
        texto3.setBounds(340, -80, 700, 600); 
        painel.add(texto3);


        JLabel texto4 = new JLabel("");
        texto4.setBounds(510, -80, 700, 600); 
        painel.add(texto4);


        

       
        JButton botaocadastrar = new JButton("SALAS");
        botaocadastrar.setBounds(0, 465, 160, 100); // Tamanho fixo
        janela.add(botaocadastrar);
        botaocadastrar.addActionListener(e -> {

            
            
            String textoComQuebras = Cinema.getCinemabyID(idCinema).listaSalas(0,5);
            String linhatexto2 = Cinema.getCinemabyID(idCinema).listaSalas(5,10);
            String linhatexto3 = Cinema.getCinemabyID(idCinema).listaSalas(10,15);
            String linhatexto4 = Cinema.getCinemabyID(idCinema).listaSalas(15,20);
            
           if(textoComQuebras != null){
                texto.setText("<html>" + textoComQuebras.replace("\n", "<br>") + "</html>");
                texto2.setText("<html>" + linhatexto2.replace("\n", "<br>") + "</html>");
                texto3.setText("<html>" + linhatexto3.replace("\n", "<br>") + "</html>");
                texto4.setText("<html>" + linhatexto4.replace("\n", "<br>") + "</html>");
           }


           
           else{
            texto.setText("<html>" +"Esse Cinema nao tem nenhuma sala cadastrada" + "<html>");
           }
           try {
            if (Cinema.getCinemabyID(idCinema).getSalasSize() > 19) {
                throw new MaximoSalasException("O MÁXIMO DE SALAS É 20");
            }
            JanelaCinema.cadastrarSala(janela, idCinema, salaDAO);


            //ATUALIZA A LISTA COM UM NOVO FILME
            textoComQuebras = Cinema.getCinemabyID(idCinema).listaSalas(0,5);
            linhatexto2 = Cinema.getCinemabyID(idCinema).listaSalas(5,10);
            linhatexto3 = Cinema.getCinemabyID(idCinema).listaSalas(10,15);
            linhatexto4 = Cinema.getCinemabyID(idCinema).listaSalas(15,20);
            //ESSE IF ESTA AQUI PARA CASO O USUARIO CANCELE E A LISTA CONTINUE VAZIA
            if(textoComQuebras != null){
                texto.setText("<html>" + textoComQuebras.replace("\n", "<br>") + "</html>");
                texto2.setText("<html>" + linhatexto2.replace("\n", "<br>") + "</html>");
                texto3.setText("<html>" + linhatexto3.replace("\n", "<br>") + "</html>");
                texto4.setText("<html>" + linhatexto4.replace("\n", "<br>") + "</html>");
           }
           else{
            texto.setText("<html>" +"Esse Cinema nao tem nenhuma sala cadastrada" + "<html>");
           }
        } catch (MaximoSalasException ep) {
            System.out.println("ERRO: " + ep.getMessage());
        }
           
        });

       
        JButton botaolistar = new JButton("SESSÕES");
        botaolistar.setBounds(160, 465, 160, 100); // Tamanho fixo
        janela.add(botaolistar);

        botaolistar.addActionListener(e -> {
            
            int tipo = JanelaSessoes.mostrarOpcoes(janela);

            switch(tipo){
                case 1:
                try{
                    JanelaSessoes.cadastrarSessao(janela,idCinema, sessaoDAO, ingressoDAO);
                }catch(JanelaFechadaException je){
                    System.out.println("ERRO: " + je.getMessage() );
                }
                break;
                case 2:
                String salanome = (JanelaSessoes.pegarSala(janela));
                List<Sala> salas = Cinema.getCinemabyID(idCinema).getSalas();
                Sala sala = null;
                try{
                    for(int i = 0; i < salas.size(); i++){
                        Sala busca = salas.get(i);
                        if(salanome.equals(busca.getNome())){
                            //AGORA SE SABE QUAL A SALA MOSTRAR AS SESSOES
                            sala = busca;
                            break;
                        }
                        //CHEGOU NA ULTIMA SALA E NAO ACHOU 
                        if(i == (salas.size() - 1)){
                            throw new SalaNaoEncontradoException("ERRO SALA NAO ENCONTRADA DIGITE NOVAMENTE");
                        }
                    }
                    
                    String textoComQuebras = sala.listaSessoes(0,5);
                    String linhatexto2 = sala.listaSessoes(5,10);
                    String linhatexto3 = sala.listaSessoes(10,15);
                    String linhatexto4 = sala.listaSessoes(15,20);
                    
                   if(textoComQuebras != null){
                        texto.setText("<html>" + textoComQuebras.replace("\n", "<br>") + "</html>");
                        texto2.setText("<html>" + linhatexto2.replace("\n", "<br>") + "</html>");
                        texto3.setText("<html>" + linhatexto3.replace("\n", "<br>") + "</html>");
                        texto4.setText("<html>" + linhatexto4.replace("\n", "<br>") + "</html>");
                   }
        
        
                   
                   else{
                    texto.setText("<html>" +"Essa sala nao tem nenhuma sessao cadastrada" + "<html>");
                   }


            }catch(SalaNaoEncontradoException pe){
                JOptionPane.showMessageDialog(janela, pe.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

            }
                break;
            }
        });

        // Botão Filmes
        JButton botaofilmes = new JButton("FILMES");
        botaofilmes.setBounds(320, 465, 160, 100); // Tamanho fixo
        janela.add(botaofilmes);
        botaofilmes.addActionListener(e -> {
            


            String textoComQuebras = Filme.listaFilmes(0, 5);
            String linhatexto2 = Filme.listaFilmes(5, 10);
            String linhatexto3 = Filme.listaFilmes(10, 15);
            String linhatexto4 =Filme.listaFilmes(15, 20);
            
           if(textoComQuebras != null){
                texto.setText("<html>" + textoComQuebras.replace("\n", "<br>") + "</html>");
                texto2.setText("<html>" + linhatexto2.replace("\n", "<br>") + "</html>");
                texto3.setText("<html>" + linhatexto3.replace("\n", "<br>") + "</html>");
                texto4.setText("<html>" + linhatexto4.replace("\n", "<br>") + "</html>");
           }
           else{
            texto.setText("<html>" +"Nao tem nenhum filme cadastrado no sistema" + "<html>");
           }
           JanelaCinema.cadastrarFilme(janela, idCinema, filmeDAO);


           //ATUALIZA A LISTA MOSTRADA
           textoComQuebras = Filme.listaFilmes(0, 5);
           linhatexto2 = Filme.listaFilmes(5, 10);
           linhatexto3 = Filme.listaFilmes(10, 15);
           linhatexto4 =Filme.listaFilmes(15, 20);
           if(textoComQuebras != null){
            texto.setText("<html>" + textoComQuebras.replace("\n", "<br>") + "</html>");
            texto2.setText("<html>" + linhatexto2.replace("\n", "<br>") + "</html>");
            texto3.setText("<html>" + linhatexto3.replace("\n", "<br>") + "</html>");
            texto4.setText("<html>" + linhatexto4.replace("\n", "<br>") + "</html>");
       }
       else{
        texto.setText("<html>" +"Nao tem nenhum filme cadastrado no sistema" + "<html>");
       }
        });

        // Botão Ingressos
        JButton botaoingressos = new JButton("INGRESSOS");
        botaoingressos.setBounds(480, 465, 160, 100); // Tamanho fixo
        janela.add(botaoingressos);
        botaoingressos.addActionListener(e -> {
            
            String salanome = (JanelaSessoes.pegarSala(janela));
            List<Sala> salas = Cinema.getCinemabyID(idCinema).getSalas();
            Sala sala = null;
            try{
                for(int i = 0; i < salas.size(); i++){
                    Sala busca = salas.get(i);
                    if(salanome.equals(busca.getNome())){
                        //AGORA SE SABE QUAL A SALA MOSTRAR OS INGRESSOS
                        sala = busca;
                        break;
                    }
                    //CHEGOU NA ULTIMA SALA E NAO ACHOU 
                    if(i == (salas.size() - 1)){
                        throw new SalaNaoEncontradoException("ERRO SALA NAO ENCONTRADA DIGITE NOVAMENTE");
                    }
                }

                    
                String textoComQuebras = sala.listaIngressos(0,5);
                String linhatexto2 = sala.listaIngressos(5,10);
                String linhatexto3 = sala.listaIngressos(10,15);
                String linhatexto4 = sala.listaIngressos(15,20);
                    
                if(textoComQuebras != null){
                    texto.setText("<html>" + textoComQuebras.replace("\n", "<br>") + "</html>");
                    texto2.setText("<html>" + linhatexto2.replace("\n", "<br>") + "</html>");
                    texto3.setText("<html>" + linhatexto3.replace("\n", "<br>") + "</html>");
                    texto4.setText("<html>" + linhatexto4.replace("\n", "<br>") + "</html>");
                }   
                else{
                    texto.setText("<html>" +"Essa sala nao tem nenhuma sessao cadastrada" + "<html>");
                }
                int tipo = JanelaIngressos.mostrarOpcoes(janela);
                if(tipo == 1){
                    JanelaIngressos.atualizarIngresso(janela, sala, ingressoDAO);
                    //ATUALIZA NA FRENTE DO USER
                    textoComQuebras = sala.listaIngressos(0,5);
                    linhatexto2 = sala.listaIngressos(5,10);
                    linhatexto3 = sala.listaIngressos(10,15);
                    linhatexto4 = sala.listaIngressos(15,20);
                    if(textoComQuebras != null){
                    texto.setText("<html>" + textoComQuebras.replace("\n", "<br>") + "</html>");
                    texto2.setText("<html>" + linhatexto2.replace("\n", "<br>") + "</html>");
                    texto3.setText("<html>" + linhatexto3.replace("\n", "<br>") + "</html>");
                    texto4.setText("<html>" + linhatexto4.replace("\n", "<br>") + "</html>");
                    }
                }


            }catch(SalaNaoEncontradoException pe){
                JOptionPane.showMessageDialog(janela, pe.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

            }
        });

        // Botão Voltar
        JButton voltar = new JButton("VOLTAR");
        voltar.setBounds(640, 465, 160, 100); 
        janela.add(voltar);
        voltar.addActionListener(e -> {
            new Janela(salaDAO, filmeDAO, sessaoDAO, ingressoDAO); 
            janela.dispose();
        });

        janela.setVisible(true);
    }





    private static void cadastrarSala(JFrame parentFrame,int id, SalaDAO salaDAO) {
        JDialog dialog = new JDialog(parentFrame, "Insira os dados da sala", true);
        dialog.setSize(300, 200);
        dialog.setLayout(null);
        
        JLabel labelInt = new JLabel("Capacidade:");
        labelInt.setBounds(20, 60, 100, 30);
        dialog.add(labelInt);
        
        JTextField campoInt = new JTextField();
        campoInt.setBounds(130, 60, 100, 30);
        dialog.add(campoInt);
        
        JLabel labelString = new JLabel("Nome:");
        labelString.setBounds(20, 20, 100, 30);
        dialog.add(labelString);
        
        JTextField campoString = new JTextField();
        campoString.setBounds(130, 20, 100, 30);
        dialog.add(campoString);
        
        JButton confirmar = new JButton("Confirmar");
        confirmar.setBounds(50, 120, 100, 30);
        dialog.add(confirmar);
        
        JButton cancelar = new JButton("Cancelar");
        cancelar.setBounds(160, 120, 100, 30);
        dialog.add(cancelar);
        
        confirmar.addActionListener(e -> {
            try {
                int capacidade = Integer.parseInt(campoInt.getText());
                String nome = campoString.getText();
                Cinema.getCinemabyID(id).criarSala(nome, capacidade,Sala.getNumSalas()+1);
                salaDAO.cadastrarSala(Cinema.getCinemabyID(id).getSala(Sala.getNumSalas()));
                
                JOptionPane.showMessageDialog(dialog, "Valores recebidos com sucesso!");
                dialog.dispose(); // Fecha o diálogo
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Por favor, insira um valor válido para o número.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch(NomeDuplicadoException ep){
                JOptionPane.showMessageDialog(dialog, ep.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch(SalaNaoEncontradoException es){
                JOptionPane.showMessageDialog(dialog, es.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelar.addActionListener(e -> dialog.dispose());

        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }






    private static void cadastrarFilme(JFrame parentFrame,int id, FilmeDao filmeDAO) {
        JDialog dialog = new JDialog(parentFrame, "Insira os dados do filme", true);
        dialog.setSize(300, 200);
        dialog.setLayout(null);
        
        JLabel labelInt = new JLabel("Duracao:");
        labelInt.setBounds(20, 60, 100, 30);
        dialog.add(labelInt);
        
        JTextField campoInt = new JTextField();
        campoInt.setBounds(130, 60, 100, 30);
        dialog.add(campoInt);
        
        JLabel labelString = new JLabel("Titulo:");
        labelString.setBounds(20, 20, 100, 30);
        dialog.add(labelString);
        
        JTextField campoString = new JTextField();
        campoString.setBounds(130, 20, 100, 30);
        dialog.add(campoString);
        
        JButton confirmar = new JButton("Confirmar");
        confirmar.setBounds(50, 120, 100, 30);
        dialog.add(confirmar);
        
        JButton cancelar = new JButton("Cancelar");
        cancelar.setBounds(160, 120, 100, 30);
        dialog.add(cancelar);
        
        confirmar.addActionListener(e -> {
            try {
                int captador = Integer.parseInt(campoInt.getText());
                String titulo = campoString.getText();
                long duracao = captador;
                
                Filme filme = new Filme(titulo, duracao);
                filmeDAO.cadastraFilme(filme);
                JOptionPane.showMessageDialog(dialog, "Valores recebidos com sucesso!");
                dialog.dispose(); // Fecha o diálogo
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Por favor, insira um valor válido para o número.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelar.addActionListener(e -> dialog.dispose()); // Fecha o diálogo sem fazer nada
        
        dialog.setLocationRelativeTo(parentFrame); // Centraliza o diálogo na janela principal
        dialog.setVisible(true);
    }
    
    
}
