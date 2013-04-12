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
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dozedoff.db.Persistence;
import com.github.dozedoff.sources.Webpage;

public class SourceEditor extends JFrame {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(SourceEditor.class);
	
	JScrollPane sourceScroll;
	JList<Webpage> sourceList;
	JPopupMenu sourcePopup;
	JMenuItem sourceCreateMenuItem, sourceDeleteMenuItem;
	DefaultListModel<Webpage> sourceListModel;
	
	public SourceEditor() {
		logger.info("Creating window {}", this.getClass().getCanonicalName());
		setupFrame();
		setupSourceList();
		setupPopupmenu();
		populateSourceList();
		
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
	
	private void setupSourceList() {
		sourceListModel = new DefaultListModel<>();
		sourceList  = new JList<>(sourceListModel);
		sourceScroll = new JScrollPane(sourceList);
		this.add(sourceScroll, "w 100!, h 50!");
	}
	
	private void populateSourceList() {
		List<Webpage> pages = Persistence.getInstance().loadWebpage();
		
		for(Webpage page : pages) {
			sourceListModel.addElement(page);
		}
	}
	
	private void createAddSourceDialog() {
		JTextField sourceName = new JTextField();
		JTextField sourceUrl = new JTextField();
		JTextField cssSelector = new JTextField();
		Object[] message = {"Source name: ", sourceName, "Source URL: ", sourceUrl, "CSS selector: ", cssSelector};
		JOptionPane pane = new JOptionPane(message,  JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		JDialog getTopicDialog =  pane.createDialog(null, "Add source");
		getTopicDialog.setVisible(true);
		
		if(pane.getValue() != null && (int)pane.getValue() == JOptionPane.OK_OPTION) {
			logger.info("Adding new source {} with URL {}", sourceName.getText(), sourceUrl.getText());
			//TODO add input validation
			//TODO update dialog for other parameters
			Webpage page = new Webpage(sourceName.getText(), sourceUrl.getText(), "", 1, cssSelector.getText());
			try {
				Persistence.getInstance().saveWebpage(page);
				sourceListModel.addElement(page);
				sourceList.repaint();
			} catch (SQLException e) {
				logger.error("Failed to save entry {}", e);
			}
		} else {
			logger.info("User aborted source entry");
		}
	}
	
	private void setupPopupmenu() {
		sourcePopup = new JPopupMenu();
		sourceCreateMenuItem = new JMenuItem("Create");
		sourceCreateMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createAddSourceDialog();
			}
		});
		
		sourceDeleteMenuItem = new JMenuItem("Delete");
		sourceDeleteMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = sourceList.getSelectedIndex();
				if (index >= 0 && index <= sourceListModel.getSize()) {
					Webpage selected = sourceListModel.get(index);
					Persistence.getInstance().deleteWebpage(selected);
					sourceListModel.remove(index);
				}
			}
		});
		
		sourcePopup.add(sourceCreateMenuItem);
		sourcePopup.add(sourceDeleteMenuItem);
		
		sourceList.setComponentPopupMenu(sourcePopup);
	}
}
