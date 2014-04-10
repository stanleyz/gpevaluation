/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.handlers.HandlerUtil;
import org.xopen.gpevaluation.rcp.ui.CreateSchemeWizard;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class ShowSchemeWizard extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		Wizard wizard = new CreateSchemeWizard();
		WizardDialog dialog = new WizardDialog(HandlerUtil
				.getActiveShell(event), wizard);
		dialog.open();
		return null;
	}
}
