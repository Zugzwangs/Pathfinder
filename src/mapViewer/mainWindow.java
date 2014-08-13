/*
               LICENCE PUBLIQUE RIEN À BRANLER
                     Version 1, Mars 2009

Copyright (C) 2014 Zugzwang

La copie et la distribution de copies exactes de cette licence sont
autorisées, et toute modification est permise à condition de changer
le nom de la licence. 

        CONDITIONS DE COPIE, DISTRIBUTON ET MODIFICATION
              DE LA LICENCE PUBLIQUE RIEN À BRANLER

 0. Faites ce que vous voulez, j’en ai RIEN À BRANLER.
 */
package mapViewer;
import java.awt.Color; 
import java.util.HashSet;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 */
public class mainWindow extends JFrame {
    
public mainWindow(){
    this.setTitle("Pathfinding monitor");
    this.setSize(600, 400);
    //Nous demandons maintenant à notre objet de se positionner au centre
    this.setLocationRelativeTo(null);
    //Termine le processus lorsqu'on clique sur la croix rouge
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    //Instanciation d'un objet JPanel, def de sa couleur et de son parent
    JPanel mainPanel = new JPanel();
    mainPanel.setBackground(Color.WHITE);        
    this.setContentPane(mainPanel);
    
    //Instanciation du panel contenant la map:
    JPanel mapPanel = new JPanel();
    mapPanel.setBackground(Color.BLACK);
    mapPanel.setSize(100, 100);
    
    mainPanel.add(mapPanel);
    
    //Et enfin, la rendre visible        
    this.setVisible(true);
}

public void finalize() {
    System.out.println("on quitte la fenetre ptincipale");
}

}
