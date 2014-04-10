/*******************************************************************************
 * Copyright (c) 2006 Spirit Link GmbH
 * All rights reserved.
 * 
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package de.spiritlink.editor.printing.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.spiritlink.editor.printing.model.ExampleModel;

/**
 * 
 * @author Tom Seidel - tom.seidel@spiritlink.de
 */
public class PrintAction extends Action {

	private ExampleModel model;

	/**
	 * @param model
	 */
	public PrintAction(ExampleModel model) {
		this.model = model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		PrintWizard wizard = new PrintWizard(this.model);
		wizard.setWindowTitle("Print");
		final WizardDialog wizardDialog = new WizardDialog(Display.getDefault()
				.getActiveShell(), wizard) {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.wizard.WizardDialog#configureShell(org.eclipse
			 * .swt.widgets.Shell)
			 */
			protected void configureShell(Shell newShell) {
				super.configureShell(newShell);
			}
		};
		wizardDialog.create();
		wizardDialog.open();
		super.run();
	}

}
