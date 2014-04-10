/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.handler;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.xopen.gpevaluation.rcp.model.ISimpleNode;
import org.xopen.gpevaluation.rcp.ui.NavigationView;
import org.xopen.gpevaluation.rcp.ui.RenameDialog;
import org.xopen.gpevaluation.rcp.ui.ViewIds;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class RenameHandler extends AbstractHandler implements IHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();
		NavigationView navigationView = (NavigationView) page
				.findView(ViewIds.NAVIGATION_VIEW);
		Object selection = ((IStructuredSelection) navigationView.getViewer()
				.getSelection()).getFirstElement();

		RenameDialog d = new RenameDialog(PlatformUI.getWorkbench()
				.getDisplay().getActiveShell(), ((ISimpleNode) selection)
				.getName());
		String name = "";
		if (d.open() == Window.OK)
			name = d.getNewName();
		if (StringUtils.isBlank(name))
			return null;

		try {
			((ISimpleNode) selection).setName(name);
		} catch (ConfigurationException e) {
			MessageDialog.openError(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(), Messages.RenameHandler_1,
					Messages.RenameHandler_2);
		}

		navigationView.getViewer().update(selection, null);
		
		/*
		 * Will update the active editor in the following code.
		 */

		return null;
	}
}
