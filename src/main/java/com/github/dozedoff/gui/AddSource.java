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

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.miginfocom.swing.MigLayout;

public class AddSource extends JDialog {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(AddSource.class);
	
	public AddSource() {
		logger.info("Creating window {}", this.getClass().getCanonicalName());
		setupDialog();
		this.setVisible(true);
	}
	
	private void setupDialog() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new MigLayout());
		this.setSize(300, 300);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
	}
}
