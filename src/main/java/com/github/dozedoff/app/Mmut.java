/* The MIT License (MIT)
 * Copyright (c) 2014 Nicholas Wright
 * http://opensource.org/licenses/MIT
 */

package com.github.dozedoff.app;

import com.github.dozedoff.gui.MmutGui;

public class Mmut {
	public static void main(String[] args) {
		new Mmut().init();
	}

	MmutGui mainWindow;

	public void init() {
		mainWindow = new MmutGui();
	}
}
