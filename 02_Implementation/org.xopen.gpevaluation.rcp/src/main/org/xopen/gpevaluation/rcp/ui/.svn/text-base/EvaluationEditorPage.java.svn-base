/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.ui;

import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.xopen.gpevaluation.rcp.model.Factors;
import org.xopen.gpevaluation.rcp.model.ISchemeNode;
import org.xopen.gpevaluation.rcp.provider.EvalEditingSupport;
import org.xopen.gpevaluation.rcp.provider.FactorsCellLabelProvider;
import org.xopen.gpevaluation.rcp.provider.MasterContentProvider;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class EvaluationEditorPage extends FormPage implements
		ICellEditorListener {
	private TreeViewer viewer;

	public EvaluationEditorPage(FormEditor formEditor) {
		super(formEditor, "product.evaluation.editor", //$NON-NLS-1$
				Messages.EvaluationEditorPage_1);
	}

	protected void createFormContent(IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		final FormToolkit toolkit = managedForm.getToolkit();
		GridLayout layout = new GridLayout();
		form.getBody().setLayout(layout);
		form.setText(Messages.EvaluationEditorPage_2);

		final Section evalSection = toolkit.createSection(form.getBody(),
				Section.TWISTIE | Section.TITLE_BAR | Section.DESCRIPTION
						| Section.EXPANDED);
		evalSection.setText(Messages.EvaluationEditorPage_3);
		evalSection.setDescription(Messages.EvaluationEditorPage_4);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.grabExcessVerticalSpace = true;
		evalSection.setLayoutData(gd);
		final Composite evalComposite = toolkit.createComposite(evalSection);
		evalSection.setClient(evalComposite);
		layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginHeight = layout.marginWidth = 0;
		evalComposite.setLayout(layout);
		viewer = new TreeViewer(evalComposite, SWT.SINGLE | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
		gd = new GridData(GridData.FILL_BOTH);
		viewer.getTree().setLayoutData(gd);
		viewer.getTree().setHeaderVisible(true);
		viewer.getTree().setLinesVisible(true);
		final String[] columnNames = { Messages.EvaluationEditorPage_5,
				Messages.EvaluationEditorPage_12,
				Messages.EvaluationEditorPage_13,
				Messages.EvaluationEditorPage_14,
				Messages.EvaluationEditorPage_7,
				Messages.EvaluationEditorPage_15,
				Messages.EvaluationEditorPage_16,
				Messages.EvaluationEditorPage_9,
				Messages.EvaluationEditorPage_10,
				Messages.EvaluationEditorPage_17,
				Messages.EvaluationEditorPage_11 };
		int[] columnWidth = { 150, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50 };
		TreeViewerColumn column;
		for (int i = 0; i < columnNames.length; i++) {
			column = new TreeViewerColumn(viewer, SWT.CENTER);
			column.getColumn().setText(columnNames[i]);
			column.getColumn().setWidth(columnWidth[i]);
			column.setLabelProvider(new FactorsCellLabelProvider(i));
			if (((EvaluationEditor) this.getEditor()).isAllowed()) {
				column.setEditingSupport(new EvalEditingSupport(this, i));
			}
		}
		viewer.setContentProvider(new MasterContentProvider());
		ISchemeNode node = (ISchemeNode) ((EvaluationEditorInput) this
				.getEditorInput()).getData().getParent();
		viewer.setInput(Factors.getInstance().getRoot(
				node.getScheme().getPath()));
		viewer.expandAll();
	}

	public TreeViewer getViewer() {
		return this.viewer;
	}

	public void applyEditorValue() {
	}

	public void cancelEditor() {
	}

	public void editorValueChanged(boolean oldValidState, boolean newValidState) {
		((EvaluationEditor) getEditor()).setDirty(true);
	}
}
