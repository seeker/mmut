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
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dozedoff.db.Persistence;
import com.github.dozedoff.media.MediaDefinition;
import com.github.dozedoff.media.TargetType;
import com.github.dozedoff.sources.Webpage;

public class SourceEditor extends JFrame {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(SourceEditor.class);
	private enum popupMenuType {Create, Edit, Delete}
	
	JList<Webpage> sourceList;
	JList<MediaDefinition> definitionList;
	DefaultListModel<Webpage> sourceListModel;
	DefaultListModel<MediaDefinition> definitionListModel;
	
	public SourceEditor() {
		logger.info("Creating window {}", this.getClass().getCanonicalName());
		setupFrame();
		setupSourceList();
		setupDefinitionList();
		setupSourcePopupMenu();
		setupDefinitionPopupMenu();
		populateSourceList();
		
		this.revalidate();
		this.setVisible(true);
		this.repaint();
	}
	
	private void setupFrame() {
		this.setSize(600, 400);
		this.setLayout(new MigLayout("wrap 5"));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Sources");
	}
	
	private void setupSourceList() {
		sourceListModel = new DefaultListModel<>();
		sourceList  = new JList<>(sourceListModel);
		this.add(new JScrollPane(sourceList), "span 2, growy");
		
		sourceList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				int index = sourceList.getSelectedIndex();
				
				if(isValidIndex(index, sourceListModel)) {
					Webpage page = sourceListModel.get(index);
					definitionListModel.clear();
					List<MediaDefinition> definitions = page.getDefinitions();
					
					for(MediaDefinition def : definitions) {
						definitionListModel.addElement(def);
					}
				}
			}
		});
	}
	
	private void setupDefinitionList() {
		definitionListModel = new DefaultListModel<>();
		definitionList = new JList<>(definitionListModel);
		this.add(new JScrollPane(definitionList), "span 3, grow");
	}
	
	private void populateSourceList() {
		List<Webpage> pages = Persistence.getInstance().loadWebpage();
		
		for(Webpage page : pages) {
			sourceListModel.addElement(page);
		}
	}
	
	private void createSourceDialog(Webpage page) {
		if(page == null) {
			page = new Webpage("", "", "", 0, "");
		}
		
		JTextField sourceName = new JTextField(page.getName());
		JTextField sourceUrl = new JTextField(page.getBaseUrl());
		JTextField cssSelector = new JTextField(page.getElementRegex());
		
		Object[] message = {"Source name: ", sourceName, "Source URL: ", sourceUrl, "CSS selector: ", cssSelector};
		JOptionPane pane = new JOptionPane(message,  JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		JDialog getTopicDialog =  pane.createDialog(null, "Add source");
		getTopicDialog.setVisible(true);
		
		if(pane.getValue() != null && (int)pane.getValue() == JOptionPane.OK_OPTION) {
			logger.info("Adding new source {} with URL {}", sourceName.getText(), sourceUrl.getText());
			//TODO add input validation
			//TODO update dialog for other parameters
			page.setName(sourceName.getText());
			page.setBaseUrl(sourceUrl.getText());
			page.setElementRegex(cssSelector.getText());
			
			try {
				Persistence.getInstance().saveWebpage(page);
				sourceListModel.removeElement(page);
				sourceListModel.addElement(page);
				sourceList.repaint();
			} catch (SQLException e) {
				logger.error("Failed to save entry {}", e);
			}
		} else {
			logger.info("User aborted source entry");
		}
	}
	
	private void createDefinitionDialog(Webpage parent, MediaDefinition definition) {
		if(definition == null) {
			definition = new MediaDefinition("", TargetType.MAGNET_LINK, parent);
		}
		
		JTextField name = new JTextField(definition.getName());
		JTextField whitelist = new JTextField(definition.getWhitelist());
		JTextField blacklist = new JTextField(definition.getBlacklist());
		JComboBox<TargetType> type = new JComboBox<>(TargetType.values());
		type.setEditable(false);
		type.setSelectedItem(definition.getType());
		
		Object[] message = {"Name: ", name, "Whitelist: ", whitelist, "Blacklist: ", blacklist, "Target Type: ", type};
		JOptionPane pane = new JOptionPane(message,  JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		JDialog getTopicDialog =  pane.createDialog(null, "Add source");
		getTopicDialog.setVisible(true);
		
		if(pane.getValue() != null && (int)pane.getValue() == JOptionPane.OK_OPTION) {
			logger.info("Adding new definition for {}", parent.getName());
			//TODO add input validation
			//TODO update dialog for other parameters
			definition.setBlacklist(blacklist.getText());
			definition.setWhitelist(whitelist.getText());
			definition.setName(name.getText());
			definition.setType((TargetType)type.getSelectedItem());

			Persistence.getInstance().saveDefinition(definition);
			definitionListModel.removeElement(definition);
			definitionListModel.addElement(definition);
			sourceList.repaint();
		} else {
			logger.info("User aborted source entry");
		}
	}
	
	private void setupSourcePopupMenu() {
		HashMap<popupMenuType, ActionListener> actions = new HashMap<>();
		actions.put(popupMenuType.Create, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createSourceDialog(null);
			}
		});
		
		actions.put(popupMenuType.Edit, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int index = sourceList.getSelectedIndex();
				if (isValidIndex(index, sourceListModel)) {
					Webpage selected = sourceListModel.get(index);
					createSourceDialog(selected);
				}
			}
		});
		
		actions.put(popupMenuType.Delete, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = sourceList.getSelectedIndex();
				if (isValidIndex(index, sourceListModel)) {
					Webpage selected = sourceListModel.get(index);
					Persistence.getInstance().deleteWebpage(selected);
					sourceListModel.remove(index);
				}
			}
		});
		
		setupPopupmenu(sourceList, actions);
	}
	
	private void setupDefinitionPopupMenu() {
		HashMap<popupMenuType, ActionListener> actions = new HashMap<>();
		actions.put(popupMenuType.Create, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = sourceList.getSelectedIndex();
				if (isValidIndex(index, sourceListModel)) {
					Webpage selected = sourceListModel.get(index);
					createDefinitionDialog(selected, null);
				}
			}
		});
		
		actions.put(popupMenuType.Edit, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int sourceIndex = sourceList.getSelectedIndex();
				if (isValidIndex(sourceIndex, sourceListModel)) {
					Webpage page = sourceListModel.get(sourceIndex);
					int definitionIndex = definitionList.getSelectedIndex();
					if(isValidIndex(definitionIndex, definitionListModel)){
						MediaDefinition definition = definitionListModel.get(definitionIndex);
						createDefinitionDialog(page, definition);
					}
				}
			}
		});
		
		actions.put(popupMenuType.Delete, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = definitionList.getSelectedIndex();
				if (isValidIndex(index,definitionListModel)) {
					MediaDefinition selected = definitionListModel.get(index);
					Persistence.getInstance().deleteDefinition(selected);
					definitionListModel.remove(index);
				}
			}
		});
		
		setupPopupmenu(definitionList, actions);
	}
	
	private void setupPopupmenu(JComponent comp, HashMap<popupMenuType, ActionListener> actions) {
		JPopupMenu popupMenu = new JPopupMenu();
		
		for(popupMenuType type : popupMenuType.values()) {
			ActionListener listener = actions.get(type);
			
			if(listener != null) {
				JMenuItem jmi = new JMenuItem(type.toString());
				jmi.addActionListener(listener);
				popupMenu.add(jmi);
			}
		}

		comp.setComponentPopupMenu(popupMenu);
	}
	
	private boolean isValidIndex(int index, DefaultListModel<?> model) {
		return (index >= 0) && (index <= model.getSize());
	}
}
