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

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class MmutGui extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel mainPanel = new JPanel(new MigLayout());
	int windowWidth = 500, windowHight = 500;
	
	public MmutGui() {
		this.setTitle("Multi-Media update tracker");
		this.setSize(windowWidth, windowHight);
		
		this.add(mainPanel);
		
		this.setVisible(true);
	}
}
