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

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	
	public final static String TOGGLE_FIND_ACTION_ID = "org.eclipsercp.hyperbola.toggleFindArea";
	
	private IWorkbenchAction exitAction;

	private IWorkbenchAction aboutAction;

	private AddContactAction addContactAction;

	private ChatAction chatAction;

	private IWorkbenchAction preferencesAction;

	private IWorkbenchAction helpAction;

	private UpdateAction updateAction;

	private ManageExtensionsAction manageAction;

	private AddExtensionsAction addExtensionAction;

	private Action toggleQuickSearch;

	private Action toggleToolbar;

	private Action toggleStatusLine;

	private final ApplicationWorkbenchWindowAdvisor windowAdvisor;

	public ApplicationActionBarAdvisor(
			ApplicationWorkbenchWindowAdvisor windowAdvisor,
			IActionBarConfigurer configurer) {
		super(configurer);
		this.windowAdvisor = windowAdvisor;
	}

	protected void makeActions(IWorkbenchWindow window) {
		exitAction = ActionFactory.QUIT.create(window);
		register(exitAction);
		aboutAction = ActionFactory.ABOUT.create(window);
		register(aboutAction);
		helpAction = ActionFactory.HELP_CONTENTS.create(window);
		updateAction = new UpdateAction(window);
		manageAction = new ManageExtensionsAction(window);
		addExtensionAction = new AddExtensionsAction(window);
		addContactAction = new AddContactAction(window);
		register(addContactAction);
		chatAction = new ChatAction(window);
		register(chatAction);
		preferencesAction = ActionFactory.PREFERENCES.create(window);
		register(preferencesAction);

		toggleQuickSearch = new Action("Search Panel", IAction.AS_CHECK_BOX) {
			public void run() {
				windowAdvisor.setShowSearchPanel(!windowAdvisor
						.getShowSearchPanel());
				updateEnablements();
			}
		};
		toggleQuickSearch.setActionDefinitionId(TOGGLE_FIND_ACTION_ID);	
		toggleQuickSearch.setId(TOGGLE_FIND_ACTION_ID);
		register(toggleQuickSearch);
		
		toggleToolbar = new Action("Toolbar", IAction.AS_CHECK_BOX) {
			public void run() {
				windowAdvisor.setShowToolbar(!windowAdvisor.getShowToolbar());
				updateEnablements();
			}
		};

		toggleStatusLine = new Action("Status Line", IAction.AS_CHECK_BOX) {
			public void run() {
				windowAdvisor.setShowStatusline(!windowAdvisor
						.getShowStatusline());
				updateEnablements();
			}
		};
	}

	protected void fillMenuBar(IMenuManager menuBar) {
		MenuManager hyperbolaMenu = new MenuManager("&Hyperbola", "hyperbola");
		hyperbolaMenu.add(addContactAction);
		hyperbolaMenu.add(chatAction);
		hyperbolaMenu.add(new Separator());
		hyperbolaMenu.add(preferencesAction);
		hyperbolaMenu.add(new Separator());
		hyperbolaMenu.add(exitAction);

		IMenuManager viewMenu = new MenuManager("&View", "view");
		viewMenu.add(toggleQuickSearch);
		viewMenu.add(toggleToolbar);
		viewMenu.add(toggleStatusLine);

		MenuManager helpMenu = new MenuManager("&Help", "help");
		helpMenu.add(helpAction);
		helpMenu.add(aboutAction);
		helpMenu.add(new Separator());
		helpMenu.add(updateAction);
		helpMenu.add(addExtensionAction);
		helpMenu.add(manageAction);

		menuBar.add(hyperbolaMenu);
		menuBar.add(viewMenu);
		menuBar.add(helpMenu);
	}

	protected void fillCoolBar(ICoolBarManager coolBar) {
		IToolBarManager toolbar = new ToolBarManager(coolBar.getStyle());
		coolBar.add(toolbar);
		toolbar.add(addContactAction);
		toolbar.add(chatAction);
	}

	protected void fillTrayItem(IMenuManager trayItem) {
		trayItem.add(aboutAction);
		trayItem.add(exitAction);
	}

	public void postWindowCreate() {
		updateEnablements();
	}

	private void updateEnablements() {
		toggleToolbar.setChecked(windowAdvisor.getShowToolbar());
		toggleStatusLine.setChecked(windowAdvisor.getShowStatusline());
		toggleQuickSearch.setChecked(windowAdvisor.getShowSearchPanel());
	}
}
