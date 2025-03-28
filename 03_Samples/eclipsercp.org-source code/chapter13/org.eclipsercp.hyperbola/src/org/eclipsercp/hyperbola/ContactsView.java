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

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipsercp.hyperbola.model.Session;
import org.jivesoftware.smack.RosterListener;

public class ContactsView extends ViewPart {

	public static final String ID = "org.eclipsercp.hyperbola.views.contacts";

	private TreeViewer treeViewer;

	public ContactsView() {
		super();
	}

	public void createPartControl(Composite parent) {
		treeViewer = new TreeViewer(parent, SWT.BORDER | SWT.MULTI
				| SWT.V_SCROLL);
		getSite().setSelectionProvider(treeViewer);
		treeViewer.setLabelProvider(new HyperbolaLabelProvider());
		treeViewer.setContentProvider(new HyperbolaContentProvider());
		treeViewer.setInput(Session.getInstance().getConnection().getRoster());
		Session.getInstance().getConnection().getRoster().addRosterListener(new RosterListener() {
			public void presenceChanged(String arg0) {
				refresh();
			};
			public void rosterModified() {
				refresh();
			};
		});
		PlatformUI.getWorkbench().getHelpSystem().setHelp(
			      treeViewer.getTree(), "org.eclipsercp.hyperbola.contactsView");
	}
	
	private void refresh() {
		getSite().getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				treeViewer.refresh();
			}
		});
	}

	public void setFocus() {
		treeViewer.getControl().setFocus();
	}
}
