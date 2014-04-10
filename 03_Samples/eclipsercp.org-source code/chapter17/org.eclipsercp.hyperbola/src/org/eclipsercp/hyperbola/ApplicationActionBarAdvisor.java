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
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.ControlContribution;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipsercp.hyperbola.actions.SearchField;
import org.eclipsercp.hyperbola.actions.StatusLineContribution;
import org.eclipsercp.hyperbola.model.Session;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	private IWorkbenchAction exitAction;

	private IWorkbenchAction aboutAction;

	private AddContactAction addContactAction;

	private ChatAction chatAction;

	private IWorkbenchAction preferencesAction;

	private IWorkbenchAction helpAction;

	private UpdateAction updateAction;

	private ManageExtensionsAction manageAction;

	private AddExtensionsAction addExtensionAction;

	private ActionContributionItem addContactCI;

	private ActionContributionItem removeContactCI;

	private ActionContributionItem chatActionCI;

	private Action toggleToolbarTextAction;

	private ControlContribution searchCI;

	private ControlContribution comboCI;

	private StatusLineContribution statusContribution;

	private ImageRegistry images = new ImageRegistry();

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

		searchCI = new ControlContribution("control1") {
			protected Control createControl(Composite parent) {
				return new SearchField(parent, 150);
			}
		};

		comboCI = new ControlContribution("control2") {
			protected Control createControl(Composite parent) {
				Combo c = new Combo(parent, SWT.READ_ONLY);
				c.add("one");
				c.add("two");
				c.add("three");
				return c;
			}
		};

		statusContribution = new StatusLineContribution("statusline", 20);
		// Include current user's info in window title
		final Session session = Session.getInstance();
		session.getConnection().addPacketWriterListener(new PacketListener() {
			public void processPacket(Packet packet) {
				Presence presence = (Presence) packet;
				updateStatusLine(presence);
			}
		}, new PacketTypeFilter(Presence.class));
		updateStatusLine(new Presence(Presence.Type.AVAILABLE, "Online", 0,
				Presence.Mode.AVAILABLE));
	}

	protected void fillMenuBar(IMenuManager menuBar) {
		MenuManager hyperbolaMenu = new MenuManager("&Hyperbola", "hyperbola");
		hyperbolaMenu.add(addContactAction);
		hyperbolaMenu.add(chatAction);
		hyperbolaMenu
				.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		hyperbolaMenu.add(new Separator());
		hyperbolaMenu.add(preferencesAction);
		hyperbolaMenu.add(new Separator());
		hyperbolaMenu.add(exitAction);
		MenuManager helpMenu = new MenuManager("&Help", "help");
		helpMenu.add(helpAction);
		helpMenu.add(aboutAction);
		helpMenu.add(new Separator());
		helpMenu.add(updateAction);
		helpMenu.add(addExtensionAction);
		helpMenu.add(manageAction);

		menuBar.add(hyperbolaMenu);
		menuBar.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		menuBar.add(helpMenu);
	}

	protected void fillCoolBar(ICoolBarManager coolBar) {
		IToolBarManager toolbar = new ToolBarManager(coolBar.getStyle()
				| SWT.BOTTOM);
		coolBar.add(toolbar);

		addContactCI = new ActionContributionItem(addContactAction);
		addContactCI.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		toolbar.add(addContactCI);

		chatActionCI = new ActionContributionItem(chatAction);
		chatActionCI.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		toolbar.add(chatActionCI);

		IToolBarManager toolbar2 = new ToolBarManager(coolBar.getStyle());
		coolBar.add(toolbar2);
		toolbar2.add(searchCI);

		IToolBarManager toolbar3 = new ToolBarManager(coolBar.getStyle());
		coolBar.add(toolbar3);
		toolbar3.add(comboCI);

		coolBar.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

	}

	private void updateStatusLine(final Presence presence) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				statusContribution
						.setText("".equals(presence.getStatus()) ? "Online"
								: presence.getStatus());
				String key = AdapterFactory.presenceToKey(presence);				
				Image image = images.get(key);
				if(image == null) {
					ImageDescriptor imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(
							Application.PLUGIN_ID, key);
					images.put(key, imageDescriptor);
					image = images.get(key);
				}
				statusContribution.setImage(image);
			}
		});
	}

	protected void fillStatusLine(IStatusLineManager statusLine) {
		statusLine.add(statusContribution);
	}

	protected void fillTrayItem(IMenuManager trayItem) {
		trayItem.add(aboutAction);
		trayItem.add(exitAction);
	}

	public void dispose() {
		images.dispose();
	}
}
