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

/**
 * @author ?? Fenêtre "A propos"
 */
public class AboutPanel extends JDialog implements ActionListener
{
    /** Bouton OK */
    private JButton pbOk = null;

    /**
     * Constructeur d'un AboutDialog, ayant pour fenêtre appelante parentFrame
     * 
     * @param parentFrame
     *            fenêtre appelante du AboutDialog
     * @param sVersion
     *            Version de l'application sous forme de chaîne
     */
    public AboutPanel(JFrame parentFrame)
    {
        // construire boite de dialogue, de titre à récupérer dans la locale
        super(parentFrame,"About Spelp", true);
//this.setResizable(true);

        this.pbOk = new JButton("OK");
        this.setPreferredSize(new Dimension(550,300));
        this.pack();
        GridBagConstraints gridBagConstraints;

        final JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        final JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));

        // Boutton par défaut : bouton "OK" (=> touche ENTER = bouton OK)
        this.pbOk.setDefaultCapable(true);
        this.getRootPane().setDefaultButton(this.pbOk);

        this.pbOk.addActionListener(this);

        southPanel.add(this.pbOk);

        // contenu du about
        JLabel lbl;

        lbl = new JLabel("Spelp assistant");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        centerPanel.add(lbl, gridBagConstraints);

        lbl = new JLabel(new ImageIcon("images.wilos"));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        centerPanel.add(lbl, gridBagConstraints);

        lbl = new JLabel("Version 0.8");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.insets = new Insets(10, 0, 0, 0);
        centerPanel.add(lbl, gridBagConstraints);

        lbl = new JLabel("Auteurs: IUP ISI");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        centerPanel.add(lbl, gridBagConstraints);

        lbl = new JLabel("Component");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        centerPanel.add(lbl, gridBagConstraints);

        lbl = new JLabel("Version?");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new Insets(0, 0, 10, 0);
        centerPanel.add(lbl, gridBagConstraints);

        this.getContentPane().add(southPanel, BorderLayout.SOUTH);
        this.getContentPane().add(centerPanel, BorderLayout.CENTER);
        // boîte de dialogue modale et centrée par rapport à l'appelant

        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(parentFrame);
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e)
    {
        // clic sur OK --> fermer la fenêtre
        if (e.getSource() == this.pbOk)
            dispose();
    }
}