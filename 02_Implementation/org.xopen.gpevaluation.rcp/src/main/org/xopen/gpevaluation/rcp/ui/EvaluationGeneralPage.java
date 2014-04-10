/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.ui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.xopen.gpevaluation.rcp.SystemConstants;
import org.xopen.gpevaluation.rcp.model.Evaluations;
import org.xopen.gpevaluation.rcp.model.IEvaluationNode;
import org.xopen.gpevaluation.rcp.model.ISchemeNode;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class EvaluationGeneralPage extends FormPage {
	private Text evalSerial, author, backgroud, remark, schemeName;

	public EvaluationGeneralPage(FormEditor formEditor) {
		super(formEditor, "product.evaluation.general",
				Messages.EvaluationGeneralPage_1);
	}

	protected void createFormContent(IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();
		TableWrapLayout tw = new TableWrapLayout();
		tw.leftMargin = 10;
		tw.rightMargin = 10;
		tw.verticalSpacing = 25;
		form.getBody().setLayout(tw);
		form.setText(Messages.EvaluationGeneralPage_2);

		Section generalSection = toolkit.createSection(form.getBody(),
				Section.TWISTIE | Section.TITLE_BAR | Section.DESCRIPTION
						| Section.EXPANDED);
		generalSection.setText(Messages.EvaluationGeneralPage_3);
		generalSection.setDescription(Messages.EvaluationGeneralPage_4);
		TableWrapData td = new TableWrapData();
		td.align = TableWrapData.LEFT;
		td.grabHorizontal = true;
		generalSection.setLayoutData(td);

		final Composite generalComposite = toolkit
				.createComposite(generalSection);
		generalSection.setClient(generalComposite);
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		layout.marginHeight = layout.marginWidth = 0;
		layout.verticalSpacing = 8;
		layout.horizontalSpacing = 20;
		generalComposite.setLayout(layout);

		generalSection.setClient(generalComposite);
		toolkit.createLabel(generalComposite, Messages.EvaluationGeneralPage_5);
		schemeName = toolkit.createText(generalComposite, "", SWT.SINGLE);
		schemeName.setEnabled(false);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.VERTICAL_ALIGN_BEGINNING);
		gd.widthHint = 150;
		schemeName.setLayoutData(gd);
		toolkit.createLabel(generalComposite, "");
		toolkit.createLabel(generalComposite, "");
		toolkit.createLabel(generalComposite, Messages.EvaluationGeneralPage_9);
		evalSerial = toolkit.createText(generalComposite, "", SWT.SINGLE);
		evalSerial.setLayoutData(gd);
		evalSerial.setEnabled(false);
		toolkit
				.createLabel(generalComposite,
						Messages.EvaluationGeneralPage_11);
		author = toolkit.createText(generalComposite, "", SWT.SINGLE);
		author.setLayoutData(gd);
		author.setEnabled(false);
		toolkit
				.createLabel(generalComposite,
						Messages.EvaluationGeneralPage_13);
		backgroud = toolkit.createText(generalComposite, "", SWT.MULTI);
		gd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.VERTICAL_ALIGN_BEGINNING);
		gd.widthHint = 153;
		gd.heightHint = 50;
		backgroud.setLayoutData(gd);
		backgroud.setEnabled(((EvaluationEditor) this.getEditor()).isAllowed());
		toolkit
				.createLabel(generalComposite,
						Messages.EvaluationGeneralPage_15);
		remark = toolkit.createText(generalComposite, "", SWT.MULTI);
		remark.setLayoutData(gd);
		remark.setEnabled(((EvaluationEditor) this.getEditor()).isAllowed());

		update();
	}

	private void update() {
		final IEvaluationNode node = ((EvaluationEditorInput) this
				.getEditorInput()).getData();
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				((EvaluationEditor) getEditor()).setDirty(true);
				getEditor().editorDirtyStateChanged();
				try {
					if (event.widget == backgroud) {
						node.getEvaluation()
								.firePropertyChange(SystemConstants.BACKGROUND,
										backgroud.getText());
					} else if (event.widget == remark) {
						node.getEvaluation().firePropertyChange(
								SystemConstants.MODEL_REMARK, remark.getText());
					}

				} catch (Exception e) {
					MessageDialog.openError(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(),
							Messages.EvaluationGeneralPage_17,
							Messages.EvaluationGeneralPage_18);
				}
			}
		};

		schemeName.setText(((ISchemeNode) node.getParent()).getName());
		evalSerial.setText(node.getName());
		author.setText(node.getEvaluation().getAuthor());
		String s = Evaluations.getInstance().getConfig(
				node.getEvaluation().getPath()).getString("background");
		backgroud.setText(s == null ? "" : s);
		s = Evaluations.getInstance().getConfig(node.getEvaluation().getPath())
				.getString("remark");
		remark.setText(s == null ? "" : s);

		evalSerial.addListener(SWT.Modify, listener);
		backgroud.addListener(SWT.Modify, listener);
		remark.addListener(SWT.Modify, listener);

	}
}
