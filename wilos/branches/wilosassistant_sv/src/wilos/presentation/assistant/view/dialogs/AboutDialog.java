package wilos.presentation.assistant.view.dialogs;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import wilos.presentation.assistant.ressources.Bundle;
import wilos.presentation.assistant.ressources.ImagesService;

public class AboutDialog extends JDialog implements ActionListener
{
    
    private JButton pbOk = null;

       public AboutDialog(Frame parentFrame)
    {
        
        this.setTitle(Bundle.getText("aboutPanel.mainTitle"));
        this.setModal(true);
        this.setPreferredSize(new Dimension(300,220));

       

        
        
        JPanel main = new JPanel();
         main.setLayout(null);
        this.pbOk = new JButton("OK");
        this.pbOk.setDefaultCapable(true);
        this.getRootPane().setDefaultButton(this.pbOk);
        this.pbOk.addActionListener(this);
        this.pbOk.setBounds(108, 155, 70,25);
        main.add(this.pbOk);

        // contenu du about
        JLabel lbl1;
        JLabel lbl2;
        JLabel lbl3;
        JLabel lbl4;
        JLabel lbl5;
        JLabel lbl6;
        lbl1 = new JLabel(Bundle.getText("aboutPanel.title"));
       
        lbl1.setBounds(20,10,120,30);
        main.add(lbl1 );

        lbl2 = new JLabel(ImagesService.getImageIcon("aboutPanel.logo"));
        lbl2.setBounds(150, 25,100,73);
        lbl2.setPreferredSize(new Dimension(30,30));
        
        
        main .add(lbl2);

        lbl3 = new JLabel(Bundle.getText("aboutPanel.version"));
        lbl3.setBounds(20, 40, 120, 30);
        main.add(lbl3);

        lbl4 = new JLabel(Bundle.getText("aboutPanel.auteur"));
        lbl4.setBounds(20, 70, 120, 30);
        main.add(lbl4);

//        lbl5 = new JLabel("Component");
//        lbl5.setBounds(70, 100, 120, 30);
//        main.add(lbl5);

        lbl6 = new JLabel(Bundle.getText("aboutPanel.url"));
        lbl6.setBounds(20, 110, 250, 30);
        main.add(lbl6 );

        this.add(main);
        this.setResizable(false);
      
        this.pack();
        this.setLocationRelativeTo(parentFrame);
        this.setVisible(true);
    }

     public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.pbOk)
            dispose();
    }
}