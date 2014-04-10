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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

public class SwitchPerspectiveAction extends Action {
	private final IWorkbenchWindow window;

	private final String id;

	private IPerspectiveDescriptor desc;

	public SwitchPerspectiveAction(IWorkbenchWindow window, String id) {
		this.window = window;
		this.id = id;
		this.desc = PlatformUI.getWorkbench().getPerspectiveRegistry()
				.findPerspectiveWithId(id);
		if (desc != null) {
			setText(desc.getLabel());
			setImageDescriptor(desc.getImageDescriptor());
		}
		setId(id);
	}

	public void run() {
		try {
			PlatformUI.getWorkbench().showPerspective(id, window);
		} catch (WorkbenchException e) {
			MessageDialog.openError(window.getShell(), "Error",
					"Error opening perspective:" + e.getMessage());
		}
	}
}
