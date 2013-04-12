/*  Copyright (C) 2013  Nicholas Wright
    
    This file is part of mmut - Multi-media update tracker
    
    mmut is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.github.dozedoff.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import net.miginfocom.swing.MigLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SourceEditor extends JFrame {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(SourceEditor.class);
	
	JPanel sourcePanel;
	JPopupMenu sourcePopup;
	JMenuItem sourceCreateMenuItem, sourceDeleteMenuItem;
	
	public SourceEditor() {
		logger.info("Creating window {}", this.getClass().getCanonicalName());
		setupFrame();
		setupSourcePanel();
		setupPopupmenu();
		
		this.revalidate();
		this.setVisible(true);
		this.repaint();
	}
	
	private void setupFrame() {
		this.setSize(600, 400);
		this.setLayout(new MigLayout());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Sources");
	}
	
	private void setupSourcePanel() {
		sourcePanel  = new JPanel(new MigLayout());
		this.add(sourcePanel, "wrap");
		
		sourcePanel.revalidate();
	}
	
	private void setupPopupmenu() {
		sourcePopup = new JPopupMenu();
		sourceCreateMenuItem = new JMenuItem("Create");
		sourceCreateMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddSource().setVisible(true);
			}
		});
		
		sourceDeleteMenuItem = new JMenuItem("Delete");
		sourceDeleteMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		sourcePopup.add(sourceCreateMenuItem);
		sourcePopup.add(sourceDeleteMenuItem);
		
		sourcePanel.setComponentPopupMenu(sourcePopup);
	}
}
