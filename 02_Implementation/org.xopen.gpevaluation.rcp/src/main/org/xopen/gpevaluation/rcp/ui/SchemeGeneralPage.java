package org.xopen.gpevaluation.rcp.ui;

import java.util.Calendar;

import org.apache.commons.configuration.ConfigurationException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
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
import org.xopen.gpevaluation.rcp.model.EvaluationScheme;
import org.xopen.gpevaluation.rcp.model.EvaluationSchemes;
import org.xopen.gpevaluation.rcp.model.Factors;
import org.xopen.gpevaluation.rcp.model.Session;
import org.xopen.gpevaluation.rcp.provider.SchemeEditorInput;

public class SchemeGeneralPage extends FormPage {
	private Text schemeName, author, remark, products;
	private DateTime createTime;
	private Combo industry;
	private boolean dirty;

	public SchemeGeneralPage(FormEditor formEditor) {
		super(formEditor, "scheme.general", Messages.SchemeGeneralPage_1); //$NON-NLS-1$
	}

	protected void createFormContent(IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();
		TableWrapLayout tw = new TableWrapLayout();
		tw.leftMargin = 10;
		tw.rightMargin = 10;
		tw.verticalSpacing = 25;
		form.getBody().setLayout(tw);
		form.setText(Messages.SchemeGeneralPage_2);

		Section generalSection = toolkit.createSection(form.getBody(),
				Section.TWISTIE | Section.TITLE_BAR | Section.DESCRIPTION
						| Section.EXPANDED);
		generalSection.setText(Messages.SchemeGeneralPage_3);
		generalSection.setDescription(Messages.SchemeGeneralPage_4);
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
		toolkit.createLabel(generalComposite, Messages.SchemeGeneralPage_5);
		schemeName = toolkit.createText(generalComposite, "", SWT.SINGLE); //$NON-NLS-1$
		schemeName.setEnabled(false);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.VERTICAL_ALIGN_BEGINNING);
		gd.widthHint = 150;
		schemeName.setLayoutData(gd);
		toolkit.createLabel(generalComposite, Messages.SchemeGeneralPage_7);
		author = toolkit.createText(generalComposite, "", SWT.SINGLE); //$NON-NLS-1$
		author.setLayoutData(gd);
		author.setEnabled(false);
		toolkit.createLabel(generalComposite, Messages.SchemeGeneralPage_9);
		createTime = new DateTime(generalComposite, SWT.MEDIUM);
		createTime.setLayoutData(gd);
		createTime.setEnabled(false);
		toolkit.createLabel(generalComposite, Messages.SchemeGeneralPage_10);
		industry = new Combo(generalComposite, SWT.NONE);
		industry.setLayoutData(gd);
		industry.setEnabled(false);
		toolkit.createLabel(generalComposite, Messages.SchemeGeneralPage_11);
		products = toolkit.createText(generalComposite, "", SWT.MULTI); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.VERTICAL_ALIGN_BEGINNING);
		gd.widthHint = 153;
		gd.heightHint = 50;
		products.setLayoutData(gd);
		toolkit.createLabel(generalComposite, Messages.SchemeGeneralPage_13);
		remark = toolkit.createText(generalComposite, "", SWT.MULTI); //$NON-NLS-1$
		remark.setLayoutData(gd);

		update();

		checkPermissions();
	}

	private void checkPermissions() {
		boolean b = false;
		try {
			b = Session.getInstance().checkPermission(
					SystemConstants.PERM_ADMIN);
		} catch (ConfigurationException e) {
		}

		products.setEnabled(b);
		remark.setEnabled(b);
	}

	private void update() {
		final EvaluationScheme scheme = ((SchemeEditorInput) this
				.getEditorInput()).getScheme();
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				dirty = true;
				((SchemeEditor) getEditor()).setDirty(true);

				try {
					if (event.widget == products) {
						scheme.firePropertyChange(SystemConstants.PRODUCTS,
								products.getText());
					} else if (event.widget == remark) {
						scheme.firePropertyChange(SystemConstants.MODEL_REMARK,
								remark.getText());
					}

				} catch (Exception e) {
					MessageDialog.openError(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(),
							Messages.SchemeGeneralPage_15,
							Messages.SchemeGeneralPage_16);
				}
			}
		};

		schemeName.setText(scheme.getName());
		author.setText(scheme.getAuthor());
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(scheme.getCreateTime());
		createTime.setDate(date.get(Calendar.YEAR), date.get(Calendar.MONTH),
				date.get(Calendar.DAY_OF_MONTH));
		try {
			industry.setItems(EvaluationSchemes.getInstance()
					.getIndustryNames());
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		industry.setText(scheme.getIndustry());
		String s = Factors.getInstance().getConfig(scheme.getPath()).getString(
				"products"); //$NON-NLS-1$
		products.setText(s == null ? "" : s); //$NON-NLS-1$
		s = Factors.getInstance().getConfig(scheme.getPath()).getString(
				"remark"); //$NON-NLS-1$
		remark.setText(s == null ? "" : s); //$NON-NLS-1$

		industry.addListener(SWT.Modify, listener);
		products.addListener(SWT.Modify, listener);
		remark.addListener(SWT.Modify, listener);

	}

	public boolean isDirty() {
		return dirty;
	}
}