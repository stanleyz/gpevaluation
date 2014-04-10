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

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	private IWorkbenchAction exitAction, openWindowAction;

	private IWorkbenchAction aboutAction;

	private AddContactAction addContactAction;

	private ChatAction chatAction;

	private IWorkbenchAction preferencesAction;

	private IWorkbenchAction helpAction;

	private UpdateAction updateAction;

	private ManageExtensionsAction manageAction;

	private AddExtensionsAction addExtensionAction;

	private IContributionItem perspectivesMenu;

	private IContributionItem openWindowsMenu;

	private IAction openDefaultPerspective, openFreeMovingPerspective,
			openDebugPerspective;

	private IContributionItem openViews;

	private IAction openContactsViewAction;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
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
		openWindowAction = ActionFactory.OPEN_NEW_WINDOW.create(window);
		register(openWindowAction);
		openContactsViewAction = new OpenViewAction(window, ContactsView.ID);
		openContactsViewAction.setText("&New Contacts View");

		openDefaultPerspective = new SwitchPerspectiveAction(window,
				Perspective.ID);
		openDebugPerspective = new SwitchPerspectiveAction(window,
				PerspectiveDebug.ID);
		openFreeMovingPerspective = new SwitchPerspectiveAction(window,
				PerspectiveFreeMoving.ID);

		perspectivesMenu = ContributionItemFactory.PERSPECTIVES_SHORTLIST
				.create(window);
		openWindowsMenu = ContributionItemFactory.OPEN_WINDOWS.create(window);
		openViews = ContributionItemFactory.VIEWS_SHORTLIST.create(window);
	}

	protected void fillMenuBar(IMenuManager menuBar) {
		MenuManager hyperbolaMenu = new MenuManager("&Hyperbola", "hyperbola");
		hyperbolaMenu.add(addContactAction);
		hyperbolaMenu.add(chatAction);
		hyperbolaMenu.add(new Separator());

		hyperbolaMenu.add(openContactsViewAction);
		MenuManager viewsMenu = new MenuManager("Views", "views");
		hyperbolaMenu.add(viewsMenu);
		viewsMenu.add(openViews);
		MenuManager layoutMenu = new MenuManager("Switch Layout", "layout");
		hyperbolaMenu.add(layoutMenu);
		layoutMenu.add(perspectivesMenu);
		MenuManager layout2Menu = new MenuManager("Switch Layout Programmatic",
				"layout2");
		hyperbolaMenu.add(layout2Menu);
		layout2Menu.add(openDefaultPerspective);
		layout2Menu.add(openDebugPerspective);
		layout2Menu.add(openFreeMovingPerspective);

		hyperbolaMenu.add(new Separator());
		hyperbolaMenu.add(preferencesAction);
		hyperbolaMenu.add(new Separator());
		hyperbolaMenu.add(exitAction);		

		MenuManager windowsMenu = new MenuManager("Windows", "windows");

		windowsMenu.add(openWindowAction);
		windowsMenu.add(openWindowsMenu);

		MenuManager helpMenu = new MenuManager("&Help", "help");
		helpMenu.add(helpAction);
		helpMenu.add(aboutAction);
		helpMenu.add(new Separator());
		helpMenu.add(updateAction);
		helpMenu.add(addExtensionAction);
		helpMenu.add(manageAction);

		menuBar.add(hyperbolaMenu);
		menuBar.add(windowsMenu);
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
}
