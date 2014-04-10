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
import org.xopen.gpevaluation.rcp.ui.CreateEvaluationWizard;
import org.xopen.gpevaluation.rcp.ui.CreateSchemeWizard;
import org.xopen.gpevaluation.rcp.ui.ItemCreateWizard;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class ShowCreateWizard extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		String wizardType = event.getParameter(CommandIds.WIZARD_TYPE);
		Wizard wizard = null;
		if (wizardType.equals(CommandIds.WIZARD_TYPE_SCHEME)) {
			wizard = new CreateSchemeWizard();
		} else if (wizardType.equals(CommandIds.WIZARD_TYPE_PRODUCTS)) {
			wizard = new CreateEvaluationWizard();
		} else {
			wizard = new ItemCreateWizard();
		}
		WizardDialog dialog = new WizardDialog(HandlerUtil
				.getActiveShell(event), wizard);
		dialog.open();
		return null;
	}
}
