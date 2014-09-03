/* The MIT License (MIT)
 * Copyright (c) 2014 Nicholas Wright
 * http://opensource.org/licenses/MIT
 */

package com.github.dozedoff.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import net.miginfocom.swing.MigLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dozedoff.db.Persistence;
import com.github.dozedoff.media.MediaFinder;

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
		menuBar = new JMenuBar();
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
