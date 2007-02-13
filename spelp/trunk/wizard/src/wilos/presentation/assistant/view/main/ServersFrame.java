package wilos.presentation.assistant.view.main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import wilos.presentation.assistant.ressources.Bundle;
import wilos.presentation.assistant.view.panels.LoginPanel;

import wilos.presentation.assistant.control.ServersListParser;
import wilos.presentation.assistant.model.WizardServer;

public class ServersFrame {

	private JDialog Fenetre1 = null;  //  @jve:decl-index=0:visual-constraint="165,55"
	private JPanel Fenetre = null;
	private JButton add = null;
	private JButton delete = null;
	private JButton valider = null;
	private JTable servs = null;
	//private JLabel list_serv = null;
	private JButton cancel = null;
	private DefaultTableModel tables_serv;
	private JScrollPane scrollServ = null;
	private JButton save = null;
	/**
	 * This method initializes Fenetre1	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JDialog getFenetre1() {
		if (Fenetre1 == null) {
			Fenetre1 = new JDialog();
			Fenetre1.setModal(true);
			Fenetre1.setSize(new Dimension(680, 380));
			Fenetre1.setResizable(false);
			Fenetre1.setContentPane(getFenetre());
			Fenetre1.setTitle(Bundle.getText("serversFrame.titre"));
			Fenetre1.setVisible(true);
			
		}
		return Fenetre1;
	}

	/**
	 * This method initializes Fenetre	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getFenetre() {
		if (Fenetre == null) {
			
			//adr.setText(Bundle.getText("wilosServer.address"));
			
			//list_serv = new JLabel();
			//list_serv.setBounds(new Rectangle(56, 12, 208, 17));
			//list_serv.setText(Bundle.getText("serversFrame.serveur"));
			Fenetre = new JPanel();
			Fenetre.setLayout(null);
			Fenetre.add(getAdd(), null);
			Fenetre.add(getDelete(), null);
			Fenetre.add(getValider(), null);
			Fenetre.add(getCancel(), null);
			Fenetre.add(getScrollServ(), null);
			Fenetre.add(getSave(), null);
			//Fenetre.add(list_serv, null);
			
		}
		return Fenetre;
	}
	private JButton getCancel()
	{
		if (cancel ==null)
		{
			cancel = new JButton();
			cancel.setBounds(new Rectangle(350,270,125,35));
			cancel.setText(Bundle.getText("serversFrame.cancel"));
			cancel.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
    			{
					Fenetre1.dispose();
    			}				
			});
		}
		return cancel;
	}
	private JButton getSave()
	{
		if (save ==null)
		{
			save = new JButton();
			save.setBounds(new Rectangle(528,180,125,35));
			save.setText(Bundle.getText("serversFrame.save"));
			save.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
    			{
					ArrayList<WizardServer> new_list_serv = new ArrayList<WizardServer>();
					JOptionPane opt1 = new JOptionPane();
					
					boolean valide = true;
					
					for (int i=0;i<servs.getRowCount()&&valide;i++)
					{
						if ((String)servs.getValueAt(i,1)==null||(String)servs.getValueAt(i,0)==null)
						{
							valide = false;
							opt1.showMessageDialog(opt1,Bundle.getText("serversFrame.error"),"Error",JOptionPane.ERROR_MESSAGE);
							opt1.setVisible(true);
						}
						else
						{
							new_list_serv.add(new WizardServer((String)servs.getValueAt(i,0),(String)servs.getValueAt(i,1),i));
						}
					}
					if (valide)
					{
						LoginPanel.list_serv.saveServersList(new_list_serv);
					}
    			}				
			});
		}
		return save;
	}
	private boolean isURLValide(String address) {
		boolean retour = true ;
		retour = retour && address.startsWith("http://");
		retour = retour && address.contains(":");
		return retour;
	}
	/**
	 * This method initializes add	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAdd() {
		if (add == null) {
			add = new JButton();
			add.setBounds(new Rectangle(528, 60, 125, 35));
			add.setText(Bundle.getText("serversFrame.ajouter"));
			add.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
    			{
					
					String alias = ""; 
					String url = "";
					boolean ok = true;

					for(int i=0;(i<servs.getRowCount())&&(ok == true);i++) {
						alias = (String)servs.getValueAt(i,0);
						url = (String)servs.getValueAt(i,1);
						
						if(       (alias == null) ||  (alias.equals("") ) ||  (url == null) || (url.equals("") )) {
							ok = false;
						}
					}
					
					if(ok == true)
					{
						tables_serv.addRow(new Vector());
						servs.setEditingRow(tables_serv.getRowCount());
					}
    			}				
			});
		}
		return add;
	}
	
	/**
	 * This method initializes delete	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getDelete() {
		if (delete == null) {
			delete = new JButton();
			delete.setBounds(new Rectangle(528, 120, 125, 35));
			delete.setText(Bundle.getText("serversFrame.delete"));
			delete.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
    			{
					if (servs.getSelectedRow() != -1)
						System.out.println(servs.getSelectedRow());
						tables_serv.removeRow((servs.getSelectedRow()));
					servs.setEditingRow(tables_serv.getRowCount());
					System.out.println(servs.getSelectedRow());
    			}				
			});
			
		}
		return delete;		
	}

	/**
	 * This method initializes valider	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getValider() {
		if (valider == null) {
			valider = new JButton();
			valider.setBounds(new Rectangle(200, 270, 125, 35));
			valider.setText(Bundle.getText("serversFrame.ok"));
			valider.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
    			{
					ArrayList<WizardServer> new_list_serv = new ArrayList<WizardServer>();
					JOptionPane opt1 = new JOptionPane();
					
					boolean valide = true;
					
					for (int i=0;i<servs.getRowCount()&&valide;i++)
					{
						if ((String)servs.getValueAt(i,1)==null)
						{
							valide = false;
							opt1.showMessageDialog(opt1,Bundle.getText("serversFrame.error"),"Error",JOptionPane.ERROR_MESSAGE);
							opt1.setVisible(true);
						}
						else
						{
							new_list_serv.add(new WizardServer((String)servs.getValueAt(i,0),(String)servs.getValueAt(i,1),i));
						}
					}
					if (valide)
					{
						LoginPanel.list_serv.saveServersList(new_list_serv);
						Fenetre1.dispose();
					}
    			}
			});
		}
		return valider;
	}

	public ServersFrame()
	{
		getFenetre1();
	}
	/**
	 * This method initializes servs	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getServs() {
		if (this.servs == null) {
						
			ServersListParser listes = new ServersListParser();
			List<WizardServer> serv = listes.getServersList();
			
			Vector<String> rowName = new Vector<String>();
			rowName.add(Bundle.getText("serversFrame.nom"));
			rowName.add(Bundle.getText("serversFrame.address"));
			
			Vector<Vector> data = new Vector<Vector>();
			
			for (int i=0;i<serv.size();i++)
			{
				Vector<String> tmp = new Vector<String> ();
				tmp.add(serv.get(i).getAlias());
				tmp.add(serv.get(i).getAddress());
				data.add(tmp);
			}
			this.tables_serv = new DefaultTableModel(data,rowName);
			this.servs = new JTable(this.tables_serv);
			this.servs.setGridColor(Color.black);
			this.servs.setVisible(true);
			
		}
		return servs;
	}
	
	
	
	private JScrollPane getScrollServ() {
		if (this.scrollServ == null) {
			scrollServ = new JScrollPane();
		//	scrollServ.add(getServs());
			scrollServ.setBounds(new Rectangle(45, 45, 437, 188));
			scrollServ.setViewportView(getServs());
			scrollServ.setVisible(true);
			
			
		}
		return scrollServ;
	}
}
