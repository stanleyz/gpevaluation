/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.ui;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.SelectionStatusDialog;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class RenameDialog extends SelectionStatusDialog {
	private Text newText;
	private String newName;
	private String oldName;

	/**
	 * @param parentShell
	 */
	public RenameDialog(Shell parentShell) {
		super(parentShell);
	}

	public RenameDialog(Shell parentShell, String oldName) {
		super(parentShell);
		this.oldName = oldName;
	}

	protected Control createDialogArea(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		parent.setLayout(layout);

		Composite composite = new Composite(parent, SWT.NONE);
		layout = new GridLayout(2, false);
		composite.setLayout(layout);

		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		composite.setLayoutData(gd);

		Label label = new Label(composite, SWT.NONE);
		label.setText(Messages.RenameDialog_0);
		gd = new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false);
		label.setLayoutData(gd);

		newText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		newText.setText(oldName);
		gd = new GridData(GridData.FILL, GridData.FILL, true, false);
		newText.setLayoutData(gd);
		newText.setSelection(0, oldName.length());

		return composite;
	}

	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected void computeResult() {
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(Messages.RenameDialog_1);
		shell.setSize(300, 120);
	}

	public String getNewName() {
		return newName;
	}

	@Override
	protected void okPressed() {
		newName = newText.getText();
		super.okPressed();
	}
}
