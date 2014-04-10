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

import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	private Image statusImage;

	private TrayItem trayItem;

	private Image trayImage;

	private ApplicationActionBarAdvisor actionBarAdvisor;
	
	private Control toolbar;

	private Control page;

	private Control statusline;

	private Control searchPanel;

	public ApplicationWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	public ActionBarAdvisor createActionBarAdvisor(
			IActionBarConfigurer configurer) {
		actionBarAdvisor = new ApplicationActionBarAdvisor(this, configurer);
		return actionBarAdvisor;
	}

	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(400, 300));
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(true);
		configurer.setShowMenuBar(true);
	}

	public void dispose() {
		if(statusImage != null)
			statusImage.dispose();
		if(trayImage != null)
			trayImage.dispose();
		if(trayItem != null)
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
		actionBarAdvisor.postWindowCreate();
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
		IStatusLineManager statuslineManager = getWindowConfigurer()
				.getActionBarConfigurer().getStatusLineManager();
		statuslineManager.setMessage(statusImage, "Online");
	}

	public void createWindowContents(Shell shell) {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		Menu menu = configurer.createMenuBar();
		shell.setMenuBar(menu);
		shell.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_BLUE));
		FormLayout layout = new FormLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		shell.setLayout(layout);
		toolbar = configurer.createCoolBarControl(shell);
		page = configurer.createPageComposite(shell);
		statusline = configurer.createStatusLineControl(shell);
		layoutNormal();
	}

	private void layoutNormal() {
		// TOOLBAR
		FormData data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		toolbar.setLayoutData(data);
		// STATUS LINE
		data = new FormData();
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		statusline.setLayoutData(data);
		// PAGE CONTENTS
		data = new FormData();
		data.top = new FormAttachment(toolbar);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.bottom = new FormAttachment(statusline);
		page.setLayoutData(data);
		layout();
	}

	private void layout() {
		getWindowConfigurer().getWindow().getShell().layout(true);
		if (page != null) {
			((Composite) page).layout(true);
		}
	}

	public void setShowSearchPanel(boolean visible) {
		if (visible) {
			if (searchPanel != null)
				return;
			searchPanel = new QuickSearchPanel(getWindowConfigurer()
					.getWindow().getShell(), null);
			FormData data = (FormData) page.getLayoutData();
			data.top = data.top;
			data.left = data.left;
			data.right = data.right;
			data.bottom = new FormAttachment(searchPanel, 0);
			page.setLayoutData(data);
			data = new FormData();
			data.left = new FormAttachment(0, 0);
			data.right = new FormAttachment(100, 0);
			if (statusline.isVisible())
				data.bottom = new FormAttachment(statusline, 0);
			else
				data.bottom = new FormAttachment(100, 0);
			searchPanel.setLayoutData(data);
			searchPanel.setFocus();
		} else {
			if (searchPanel == null)
				return;
			searchPanel.dispose();
			searchPanel = null;
			FormData data = (FormData) page.getLayoutData();
			data.top = data.top;
			data.left = data.left;
			data.right = data.right;
			if (statusline.isVisible())
				data.bottom = new FormAttachment(statusline, 0);
			else
				data.bottom = new FormAttachment(100, 0);
			page.setLayoutData(data);
		}
		layout();
	}

	public boolean getShowSearchPanel() {
		return searchPanel != null && searchPanel != null;
	}

	public void setShowToolbar(boolean visible) {
		if (visible) {
			if (toolbar.isVisible())
				return;
			FormData data = (FormData) page.getLayoutData();
			data.top = new FormAttachment(toolbar, 0);
			page.setLayoutData(data);
			toolbar.setVisible(true);
		} else {
			if (!toolbar.isVisible())
				return;
			FormData data = (FormData) page.getLayoutData();
			data.top = new FormAttachment(0, 0);
			page.setLayoutData(data);
			toolbar.setVisible(false);
		}
		layout();
	}

	public boolean getShowToolbar() {
		return toolbar != null && toolbar.isVisible();
	}

	public void setShowStatusline(boolean visible) {
		if (visible) {
			if (statusline.isVisible())
				return;
			if (searchPanel != null && searchPanel.isVisible()) {
				FormData data = (FormData) searchPanel.getLayoutData();
				data.bottom = new FormAttachment(statusline, 0);
				searchPanel.setLayoutData(data);
			} else {
				FormData data = (FormData) page.getLayoutData();
				data.bottom = new FormAttachment(statusline, 0);
				page.setLayoutData(data);
			}
			statusline.setVisible(true);
		} else {
			if (!statusline.isVisible())
				return;
			if (searchPanel != null && searchPanel.isVisible()) {
				FormData data = (FormData) searchPanel.getLayoutData();
				data.bottom = new FormAttachment(100, 0);
				searchPanel.setLayoutData(data);
			} else {
				FormData data = (FormData) page.getLayoutData();
				data.bottom = new FormAttachment(100, 0);
				page.setLayoutData(data);
			}
			statusline.setVisible(false);
		}
		layout();
	}

	public boolean getShowStatusline() {
		return statusline != null && statusline.isVisible();
	}
}
