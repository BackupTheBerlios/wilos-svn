/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package wilos.presentation.assistant.view.panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.presentation.assistant.control.WizardControler;
import wilos.presentation.assistant.ressources.Bundle;
import wilos.presentation.assistant.view.main.WizardMainFrame;

public class InfoTpsPanel extends JDialog
{
	private JPanel centre=null;
	private JLabel tps=null;
	private JLabel tps_accomplish=null;
	private JButton valider=null;
	private JButton annuler=null;
	private JButton appliquer=null;
	private HourTextField zone_tps=null;
	private HourTextField zone_tps_Accomplish=null;
	private Long _remaining = null ;
	private Long _accomplish = null ;
	private ConcreteTaskDescriptor ctd = null ;
	private boolean editable = true ;
	
	public InfoTpsPanel(ConcreteTaskDescriptor c , Long remaining,Long accomplish,boolean editAccomplish)
	{
		super(WizardControler.getInstance().getMainFrameForDialogs(),true);
		editable = editAccomplish ;
		_remaining = remaining ;
		_accomplish = accomplish ;
		ctd = c ;
		System.out.println(_remaining + " " + _accomplish);
		this.setTitle(Bundle.getText("infopanel.modifier"));
		this.setModal(true);
		this.setContentPane(getPanel());
		this.setPreferredSize(new Dimension(320,150));
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}
	public void appliquer () {
		try {
			zone_tps.commitEdit();
			zone_tps_Accomplish.commitEdit();
			float remaining = getRemainingTimeForUpload();
			float accomplish = getAccomplishTimeForUpload() ;
			WizardControler.getInstance().saveTimes(ctd, remaining, accomplish,editable);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public JLabel getTps()
	{
		if (tps==null)
		{
			tps = new JLabel(Bundle.getText("infopanel.remaining"));
			tps.setBounds(10,30,150,20);
		}
		return tps;
	}
	private JLabel getAccomplishTime() {
		if (tps_accomplish==null)
		{
			tps_accomplish = new JLabel(Bundle.getText("infopanel.accomplish"));
			tps_accomplish.setBounds(10,50,150,20);
		}
		return tps_accomplish;
	}
	private HourTextField getAreaAccomplishTps() {
		// TODO Auto-generated method stub
		if (zone_tps_Accomplish == null){
			zone_tps_Accomplish = new HourTextField(new HourFormat());
			zone_tps_Accomplish.setValue(_accomplish);
			zone_tps_Accomplish.setBounds(130,50,100,20);
			zone_tps_Accomplish.setEditable(editable);
		}
		return zone_tps_Accomplish;
	}
	public HourTextField getAreaTps()
	{
		if (zone_tps==null)
		{
			zone_tps = new HourTextField(new HourFormat());
			zone_tps.setValue(_remaining);
			zone_tps.setBounds(130,30,100,20);
		}
		return zone_tps;
	}
	public JButton getValider()
	{
		if (valider==null)
		{
			valider = new JButton(Bundle.getText("serversFrame.ok"));
			valider.setBounds(10,90,90,23);
			valider.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					appliquer() ;
					InfoTpsPanel.this.dispose();
				}
			});
		}
		return valider;
	}
	public JButton getAppliquer()
	{
		if (appliquer==null)
		{
			appliquer = new JButton(Bundle.getText("serversFrame.apply"));
			appliquer.setBounds(210,90,90,23);
			appliquer.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					appliquer() ;
					
				}
			});
		}
		return appliquer;
	}
	
	public JButton getAnnuler()
	{
		if (annuler==null)
		{
			annuler = new JButton(Bundle.getText("serversFrame.cancel"));
			annuler.setBounds(110,90,90,23);
			annuler.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					InfoTpsPanel.this.dispose() ;
				}
			});
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
			centre.add(getAccomplishTime());
			centre.add(getAreaTps());
			centre.add(getAreaAccomplishTps());
			centre.add(getValider());
			centre.add(getAnnuler());
			centre.add(getAppliquer());
		}
		return centre;
	}
	
	public float getRemainingTimeForUpload(){
		long val = (Long)zone_tps.getValue() ;
		float retour = (float)val/3600;
		return retour ;
	}
	
	
	public float getAccomplishTimeForUpload(){
		long val = (Long)zone_tps_Accomplish.getValue() ;
		float retour = (float)val/3600;
		return retour ;
	}
	
}
