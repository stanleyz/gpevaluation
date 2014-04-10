/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.ui.PlatformUI;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;
import org.xopen.gpevaluation.rcp.Activator;
import org.xopen.gpevaluation.rcp.ui.LoginDialog;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class Logout extends AbstractHandler implements IHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		Preferences preferences = new ConfigurationScope()
				.getNode(Activator.PLUGIN_ID);
		preferences.put(LoginDialog.AUTO_LOGIN, "");
		try {
			preferences.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
		PlatformUI.getWorkbench().restart();
		return null;
	}
}
