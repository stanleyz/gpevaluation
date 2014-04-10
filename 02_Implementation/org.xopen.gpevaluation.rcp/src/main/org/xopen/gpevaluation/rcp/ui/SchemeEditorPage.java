package org.xopen.gpevaluation.rcp.ui;

import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;

public class SchemeEditorPage extends FormPage {
	private ScrolledPropertiesBlock block;

	public SchemeEditorPage(FormEditor formEditor) {
		super(formEditor, "scheme.factor.editor",Messages.SchemeEditorPage_1); //$NON-NLS-1$
		block = new ScrolledPropertiesBlock();
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		form.setText(Messages.SchemeEditorPage_2);
		block.createContent(managedForm);
	}
}
