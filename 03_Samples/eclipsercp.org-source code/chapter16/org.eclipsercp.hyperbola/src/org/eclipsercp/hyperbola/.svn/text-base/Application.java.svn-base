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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IPlatformRunnable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipsercp.hyperbola.model.ConnectionDetails;
import org.eclipsercp.hyperbola.model.Session;
import org.jivesoftware.smack.XMPPException;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IPlatformRunnable {

	public static final String PLUGIN_ID = "org.eclipsercp.hyperbola";

	public Object run(Object args) throws Exception {
		Display display = PlatformUI.createDisplay();
		try {
			final Session session = Session.getInstance();
			Platform.endSplash();
			if (!login(session))
				return IPlatformRunnable.EXIT_OK;

			int returnCode = PlatformUI.createAndRunWorkbench(display,
					new ApplicationWorkbenchAdvisor());
			if (returnCode == PlatformUI.RETURN_RESTART) {
				return IPlatformRunnable.EXIT_RESTART;
			}
			return IPlatformRunnable.EXIT_OK;
		} finally {
			display.dispose();
		}
	}

	private boolean login(final Session session) {
		boolean firstTry = true;
		LoginDialog loginDialog = new LoginDialog(null);
		while (session.getConnection() == null
				|| !session.getConnection().isAuthenticated()) {
			IPreferencesService service = Platform.getPreferencesService();
			boolean auto_login = service.getBoolean(Application.PLUGIN_ID,
					GeneralPreferencePage.AUTO_LOGIN, true, null);
			ConnectionDetails details = loginDialog.getConnectionDetails();
			if (!auto_login || details == null || !firstTry) {
				if (loginDialog.open() != Window.OK)
					return false;
				details = loginDialog.getConnectionDetails();
			}
			firstTry = false;
			session.setConnectionDetails(details);
			connectWithProgress(session);
		}
		return true;
	}

	private void connectWithProgress(final Session session) {
		ProgressMonitorDialog progress = new ProgressMonitorDialog(null);
		progress.setCancelable(true);
		try {
			progress.run(true, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException {
					try {
						session.connectAndLogin(monitor);
					} catch (XMPPException e) {
						throw new InvocationTargetException(e);
					}
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// do nothing
		}
	}
}
