/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	private IWorkbenchAction saveAction;
	private IWorkbenchAction saveAllAction;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	protected void makeActions(IWorkbenchWindow window) {
		saveAction = ActionFactory.SAVE.create(window);
		register(saveAction);

		saveAllAction = ActionFactory.SAVE_ALL.create(window);
		register(saveAllAction);
	}

}
