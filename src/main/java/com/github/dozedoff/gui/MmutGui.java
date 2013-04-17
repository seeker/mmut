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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dozedoff.db.Persistence;
import com.github.dozedoff.media.MediaFinder;

import net.miginfocom.swing.MigLayout;

public class MmutGui extends JFrame {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(MmutGui.class);
	
	SourceEditor sourceEditor;
	
	JMenuBar menuBar;
	JMenuItem sourcesMenuItem, vaccumMenuItem;
	JMenu settingsMenu, dbMenu;
	
	JButton processAll;
	
	int windowWidth = 500, windowHight = 500;
	
	public MmutGui() {
		logger.info("Creating window {}", this.getClass().getCanonicalName());
		setupFrame();
		setupMenuBar();
		setupButton();
		this.setVisible(true);
	}
	
	private void setupFrame() {
		this.setLayout(new MigLayout());
		this.setTitle("Multi-Media update tracker");
		this.setSize(windowWidth, windowHight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void setupMenuBar() {
		menuBar  = new JMenuBar();
		settingsMenu = new JMenu("Settings");
		dbMenu = new JMenu("DB");
		
		sourcesMenuItem = new JMenuItem("Sources");
		vaccumMenuItem = new JMenuItem("Vacuum");
		
		sourcesMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sourceEditor = new SourceEditor();
			}
		});
		
		vaccumMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Persistence.getInstance().vacuumDb();
			}
		});
		
		menuBar.add(settingsMenu);
		menuBar.add(dbMenu);
		
		settingsMenu.add(sourcesMenuItem);
		dbMenu.add(vaccumMenuItem);
		
		this.setJMenuBar(menuBar);
		menuBar.validate();
		menuBar.setVisible(true);
	}
	
	private void setupButton() {
		processAll = new JButton("Process All");
		processAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MediaFinder.findAll();
			}
		});
		
		this.add(processAll);
	}
}
