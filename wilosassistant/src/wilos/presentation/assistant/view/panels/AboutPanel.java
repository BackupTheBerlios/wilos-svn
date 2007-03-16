package wilos.presentation.assistant.view.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import wilos.presentation.assistant.ressources.Bundle;
import wilos.presentation.assistant.ressources.ImagesService;

public class AboutPanel extends JDialog implements ActionListener
{
    
    private JButton pbOk = null;

       public AboutPanel(JFrame parentFrame)
    {
        
        this.setTitle(Bundle.getText("aboutPanel.mainTitle"));
        this.setModal(true);
        this.setPreferredSize(new Dimension(300,250));
        this.setVisible(true);
        this.pack();
       

        
        
        JPanel main = new JPanel();
         main.setLayout(null);
        this.pbOk = new JButton("OK");
        this.pbOk.setDefaultCapable(true);
        this.getRootPane().setDefaultButton(this.pbOk);
        this.pbOk.addActionListener(this);
        this.pbOk.setBounds(108, 165, 70,25);
        main.add(this.pbOk);

        // contenu du about
        JLabel lbl1;
        JLabel lbl2;
        JLabel lbl3;
        JLabel lbl4;
        JLabel lbl5;
        JLabel lbl6;
        lbl1 = new JLabel(Bundle.getText("aboutPanel.title"));
       
        lbl1.setBounds(70,10,120,30);
        main.add(lbl1 );

        lbl2 = new JLabel(ImagesService.getImageIcon(Bundle.getText("images.frameIcon")));
        lbl2.setBounds(5, 5,64,64);
        lbl2.setPreferredSize(new Dimension(30,30));
        
        
        main .add(lbl2);

        lbl3 = new JLabel("Version 0.8");
        lbl3.setBounds(73, 40, 120, 30);
        main.add(lbl3);

        lbl4 = new JLabel(Bundle.getText("aboutPanel.auteur"));
        lbl4.setBounds(73, 70, 120, 30);
        main.add(lbl4);

//        lbl5 = new JLabel("Component");
//        lbl5.setBounds(70, 100, 120, 30);
//        main.add(lbl5);

        lbl6 = new JLabel(Bundle.getText("aboutPanel.url"));
        lbl6.setBounds(73, 110, 250, 30);
        main.add(lbl6 );

        this.add(main);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(parentFrame);
    }

     public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.pbOk)
            dispose();
    }
}