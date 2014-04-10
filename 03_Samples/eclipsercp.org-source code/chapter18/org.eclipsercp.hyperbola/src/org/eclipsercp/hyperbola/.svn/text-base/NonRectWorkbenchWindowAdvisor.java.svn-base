/*******************************************************************************
 * Copyright (c) 2005 Jean-Michel Lemieux, Jeff McAffer and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Hyperbola is an RCP application developed for the book
 *     Eclipse Rich Client Platform - 
 *         Designing, Coding, and Packaging Java Applications
 * See http://eclipsercp.org
 *
 * Contributors:
 *     Jean-Michel Lemieux and Jeff McAffer - initial API and implementation
 *******************************************************************************/
package org.eclipsercp.hyperbola;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class NonRectWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	private Image statusImage;

	private TrayItem trayItem;

	private Image trayImage;

	private NonRectActionBarAdvisor actionBarAdvisor;
	
	private ImageData[] datas = null;
	private Image[] images = null;
	private Region[] regions = null;
	
	private Region currentRegion;
	private Image currentImage;
	private ImageData currentData;
	
	private Image closeImage = null;
	private Color bkgColor = null;
	private Control coolBar;
	private Menu toggleToolbar;

	public NonRectWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	public ActionBarAdvisor createActionBarAdvisor(
			IActionBarConfigurer configurer) {
		actionBarAdvisor = new NonRectActionBarAdvisor(configurer);
		return actionBarAdvisor;
	}

	public void createWindowContents(final Shell shell) {				
		int x = 19;
		int y = 56;
						
		Combo box = new Combo(shell, SWT.DROP_DOWN);
		box.setItems(new String[] {"www.eclipse.org", "lemieux.com"});
		box.setBounds(34, 31, 150, 20);
		
		Control page = getWindowConfigurer().createPageComposite(shell);
		page.setBounds(x, y, 274 - x, 253 - y);
		
		toggleToolbar = new Menu(shell, SWT.POP_UP);
		final MenuItem toggleItem = new MenuItem(toggleToolbar, SWT.CHECK);
		toggleItem.setText("Show Toolbar");
		toggleItem.setSelection(true);
		toggleItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if(! toggleItem.getSelection()) {
					coolBar.setVisible(false);
					currentRegion = regions[1];
					currentImage = images[1];
					currentData = datas[1];
				} else {
					coolBar.setVisible(true);
					currentRegion = regions[0];
					currentImage = images[0];
					currentData = datas[0];
				}
				shell.setRegion(currentRegion);			
				shell.redraw();
			}
		});
		shell.setMenu(toggleToolbar);
		
		coolBar = getWindowConfigurer().createCoolBarControl(shell);
		bkgColor = new Color(coolBar.getDisplay(), new RGB(1,69,146));
		coolBar.setBackground(bkgColor);
		coolBar.setBounds(15, 291, 250, 21);
		
		ToolBar closeBar = new ToolBar(shell, SWT.FLAT);
		ToolItem closeItem = new ToolItem(closeBar, SWT.PUSH);
		closeImage = AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, "icons/shapes/close.gif").createImage(true);
		closeItem.setImage(closeImage);
		closeItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				 PlatformUI.getWorkbench().close();
			}
		});
		closeBar.setBounds(256, 20, 28, 22);
		closeBar.setBackground(bkgColor);
	}
	
	public void postWindowCreate() {
		URL imageURL = Platform.getBundle(Application.PLUGIN_ID).getEntry("/icons/shapes/blue.gif");
		InputStream imageInput = null; 
		try {
            imageInput = new BufferedInputStream(imageURL.openStream());
        } catch (IOException e) {
            imageInput = null;
        }		
		datas = new ImageLoader().load(imageInput);
		images = new Image[datas.length];
		regions = new Region[datas.length];
		final Shell shell = getWindowConfigurer().getWindow().getShell();
		ImageUtilities.loadImages(shell.getDisplay(), datas, images, regions);
		
		Listener listener = new Listener() {

			int startX, startY;

			public void handleEvent(Event e) {
				if (e.type == SWT.MouseDown && e.button == 1) {
					startX = e.x;
					startY = e.y;
				}
				if (e.type == SWT.MouseMove && (e.stateMask & SWT.BUTTON1) != 0) {
					Point p = shell.toDisplay(e.x, e.y);
					p.x -= startX;
					p.y -= startY;
					shell.setLocation(p);
				}
				if (e.type == SWT.Paint) {
					ImageData data = datas[0];
					e.gc.drawImage(currentImage, data.x, data.y);
				}
				if (e.type == SWT.KeyDown && e.character == SWT.ESC) {
					shell.dispose();
				}
			}
		};
		shell.addListener(SWT.KeyDown, listener);
		shell.addListener(SWT.MouseDown, listener);
		shell.addListener(SWT.MouseMove, listener);
		shell.addListener(SWT.Paint, listener);
		
		currentImage = images[0];
		currentRegion = regions[0];
		currentData = datas[0];
				
		shell.setSize(currentData.x + currentData.width, currentData.y + currentData.height);
		if(regions.length > 0)
			shell.setRegion(currentRegion);
	}
	
    public void postWindowClose() {
		for (int i = 0; i < images.length; i++) {
			regions[i].dispose();
			images[i].dispose();			
		}
		closeImage.dispose();
		bkgColor.dispose();
		toggleToolbar.dispose();
	}
	
	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(400, 300));
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(true);
		configurer.setShowMenuBar(true);
	    configurer.setShowProgressIndicator(true);
	    
	    getWindowConfigurer().setShellStyle(SWT.NO_TRIM);
	}

	public void dispose() {
		statusImage.dispose();
		trayImage.dispose();
		trayItem.dispose();
	}

	public void postWindowOpen() {
		initStatusLine();
		final IWorkbenchWindow window = getWindowConfigurer().getWindow();
		trayItem = initTaskItem(window);
		if (trayItem != null) {
			hookPopupMenu(window);
			hookMinimize(window);
		}
	}

	private void hookMinimize(final IWorkbenchWindow window) {
		window.getShell().addShellListener(new ShellAdapter() {
			public void shellIconified(ShellEvent e) {
				window.getShell().setVisible(false);
			}
		});
		trayItem.addListener(SWT.DefaultSelection, new Listener() {
			public void handleEvent(Event event) {
				Shell shell = window.getShell();
				if (!shell.isVisible()) {
					shell.setVisible(true);
					window.getShell().setMinimized(false);
				}
			}
		});
	}

	private void hookPopupMenu(final IWorkbenchWindow window) {
		// Add listener for menu pop-up
		trayItem.addListener(SWT.MenuDetect, new Listener() {
			public void handleEvent(Event event) {
				MenuManager trayMenu = new MenuManager();
				Menu menu = trayMenu.createContextMenu(window.getShell());
				actionBarAdvisor.fillTrayItem(trayMenu);
				menu.setVisible(true);
			}
		});
	}

	private TrayItem initTaskItem(IWorkbenchWindow window) {
		final Tray tray = window.getShell().getDisplay().getSystemTray();
		if (tray == null)
			return null;
		trayItem = new TrayItem(tray, SWT.NONE);
		trayImage = AbstractUIPlugin.imageDescriptorFromPlugin(
				"org.eclipsercp.hyperbola", IImageKeys.ONLINE).createImage();
		trayItem.setImage(trayImage);
		trayItem.setToolTipText("Hyperbola");
		return trayItem;
	}

	private void initStatusLine() {
		statusImage = AbstractUIPlugin.imageDescriptorFromPlugin(
				"org.eclipsercp.hyperbola", IImageKeys.ONLINE).createImage();
		IStatusLineManager statusline = getWindowConfigurer()
				.getActionBarConfigurer().getStatusLineManager();
		statusline.setMessage(statusImage, "Online");
	}
}
