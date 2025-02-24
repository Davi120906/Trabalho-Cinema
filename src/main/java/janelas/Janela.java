package janelas;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import cinema.Cinema;
import daos.FilmeDao;
import daos.IngressoDAO;
import daos.SalaDAO;
import daos.SessaoDAO;

public class Janela {
    
    public Janela(SalaDAO salaDAO, FilmeDao filmeDao, SessaoDAO sessaoDAO, IngressoDAO ingressoDAO) {

        JFrame janela = new JFrame("Vários Botões");
        int numBotoes = Cinema.getCinemasSize();
        janela.setLayout(new GridLayout(numBotoes, numBotoes));  

       
        JButton[] botoes = new JButton[numBotoes];

     
        for (int i = 0; i < numBotoes; i++) {
            Cinema cinema = Cinema.getCinema(i);
            botoes[i] = new JButton(cinema.getNome() + " "+ cinema.getLocal()); 
            janela.add(botoes[i]);
            botoes[i].addActionListener(e -> {
                new JanelaCinema(cinema.getId(), salaDAO, filmeDao, sessaoDAO, ingressoDAO);
                janela.dispose();
            });
        }

 
        janela.setSize(800, 600);  
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        janela.setLocationRelativeTo(null);  
        janela.setVisible(true);  
    }

}