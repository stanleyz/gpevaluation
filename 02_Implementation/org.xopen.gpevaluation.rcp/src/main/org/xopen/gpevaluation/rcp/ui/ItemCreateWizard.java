/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.ui;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.xopen.gpevaluation.rcp.SystemConstants;
import org.xopen.gpevaluation.rcp.handler.CommandIds;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class ItemCreateWizard extends Wizard implements INewWizard {
	private CreateOtherPage otherPage;
	private CreateSchemePage schemePage;
	private CreateEvaluationPage evalPage;
	private String wizard;

	public ItemCreateWizard() {
		super();
	}
	
	public ItemCreateWizard(String wizardType) {
		super();
		this.wizard = wizardType;
		setNeedsProgressMonitor(true);
	}

	public boolean performFinish() {
		return false;
	}

	public void addPages() {
		if (wizard.equals(CommandIds.WIZARD_TYPE_SCHEME)) {
			schemePage = new CreateSchemePage();
			addPage(schemePage);
		} else if (wizard.equals(CommandIds.WIZARD_TYPE_PRODUCTS)) {
			evalPage = new CreateEvaluationPage();
			addPage(evalPage);
		} else if (wizard.equals(CommandIds.WIZARD_TYPE_OTHER)) {
			otherPage = new CreateOtherPage();
			addPage(otherPage);
			setForcePreviousAndNextButtons(true);
		}
	}

	public IWizardPage getNextPage(IWizardPage page) {
		if (otherPage.getSelection().getText().equals(
				SystemConstants.ITEM_CREATE_SCHEME)) {
			schemePage = new CreateSchemePage();
			addPage(schemePage);
		} else {
			evalPage = new CreateEvaluationPage();
			addPage(evalPage);
		}
		return super.getNextPage(page);
	}

	public boolean canFinish() {
		if (getContainer().getCurrentPage() instanceof CreateOtherPage) {
			return false;
		}
		else {
			return super.canFinish();
		}
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		System.out.println("fuck");
	}
}
