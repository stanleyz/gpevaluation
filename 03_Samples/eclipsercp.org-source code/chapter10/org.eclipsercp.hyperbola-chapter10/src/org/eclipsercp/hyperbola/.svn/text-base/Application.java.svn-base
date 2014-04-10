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

import org.eclipse.core.runtime.IPlatformRunnable;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipsercp.hyperbola.model.ConnectionDetails;
import org.eclipsercp.hyperbola.model.Session;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class Application implements IPlatformRunnable {

	public static final String PLUGIN_ID = "org.eclipsercp.hyperbola";

	public Object run(Object args) throws Exception {
		Display display = PlatformUI.createDisplay();
		try {
			final Session session = Session.getInstance();
			if (!login(session))
				return IPlatformRunnable.EXIT_OK;
			int returnCode = PlatformUI.createAndRunWorkbench(display,
					new ApplicationWorkbenchAdvisor());
			if (returnCode == PlatformUI.RETURN_RESTART)
				return IPlatformRunnable.EXIT_RESTART;
			return IPlatformRunnable.EXIT_OK;
		} finally {
			display.dispose();
		}
	}

	private boolean login(final Session session) {
		try {
			ConnectionDetails d = new ConnectionDetails("reader",
					"eclipsercp.org", "secret");
			XMPPConnection con = new XMPPConnection(d.getServer());
			con.login(d.getUserId(), d.getPassword(), d.getResource());
			session.setConnection(con);
			session.setConnectionDetails(d);
		} catch (XMPPException e) {
			return false;
		}
		return true;
	}
}
