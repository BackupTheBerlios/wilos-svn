package wilos.presentation.assistant.view.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.*;

public class InfoTpsPanel extends JDialog
{
	private JPanel centre;
	private JLabel tps;
	private JButton valider;
	private JButton annuler;
	private JButton appliquer;
	private JTextField zone_tps;
	
	public InfoTpsPanel()
	{
		this.setTitle("Modifier");
		this.setModal(true);
		this.setContentPane(getPanel());
		this.setPreferredSize(new Dimension(320,190));
		this.setResizable(true);
		this.pack();
		this.setVisible(true);
	}
	public JLabel getTps()
	{
		if (tps==null)
		{
			tps = new JLabel("Temps:");
			tps.setBounds(10,10,70,20);
		}
		return tps;
	}
	public JButton getValider()
	{
		if (valider==null)
		{
			valider = new JButton("Valider");
			valider.setBounds(10,90,90,23);
		}
		return valider;
	}
	public JButton getAppliquer()
	{
		if (appliquer==null)
		{
			appliquer = new JButton("Appliquer");
			appliquer.setBounds(210,90,90,23);
		}
		return appliquer;
	}
	public JTextField getAreaTps()
	{
		if (zone_tps==null)
		{
			zone_tps = new JTextField();
			zone_tps.setText("0");
			zone_tps.setBounds(110,10,60,20);
		}
		return zone_tps;
	}
	public JButton getAnnuler()
	{
		if (annuler==null)
		{
			annuler = new JButton("Annuler");
			annuler.setBounds(110,90,90,23);
		}
		return annuler;
	}
	public JPanel getPanel()
	{
		if (centre==null)
		{
			centre = new JPanel();
			centre.setLayout(null);
			centre.add(getTps());
			centre.add(getAreaTps());
			centre.add(getValider());
			centre.add(getAnnuler());
			centre.add(getAppliquer());
		}
		return centre;
	}
	
}
