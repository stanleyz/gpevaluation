/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.ui;

import java.io.File;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.xopen.gpevaluation.rcp.SystemConstants;
import org.xopen.gpevaluation.rcp.model.EvaluationSchemes;
import org.xopen.gpevaluation.rcp.model.IIndustryNode;
import org.xopen.gpevaluation.rcp.model.ISchemeNode;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class CreateEvaluationPage extends WizardPage implements Listener {
	private Text evalSerial, saveTo, remark;
	private Button[] destDir;
	private Button chooseFolder;
	private Combo industry, scheme;

	protected CreateEvaluationPage() {
		super(Messages.CreateEvaluationPage_0);
		setTitle(Messages.CreateEvaluationPage_1);
		setDescription(Messages.CreateEvaluationPage_2);
	}

	public void createControl(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.horizontalSpacing = 10;
		layout.verticalSpacing = 5;
		composite.setLayout(layout);
		Label label = new Label(composite, SWT.NONE);
		label.setText(Messages.CreateEvaluationPage_3);
		evalSerial = new Text(composite, SWT.SINGLE);
		GridData noSpan = new GridData(GridData.FILL_HORIZONTAL);
		noSpan.heightHint = 16;
		evalSerial.setLayoutData(noSpan);

		Group destDirArea = new Group(composite, SWT.NONE);
		destDirArea.setText(Messages.CreateEvaluationPage_4);
		final GridData span = new GridData(GridData.FILL_HORIZONTAL);
		span.horizontalSpan = 2;
		destDirArea.setLayoutData(span);
		destDirArea.setLayout(layout);
		destDir = new Button[2];
		final String[] destDesc = { Messages.CreateEvaluationPage_5,
				Messages.CreateEvaluationPage_6 };
		for (int i = 0; i < destDir.length; i++) {
			destDir[i] = new Button(destDirArea, SWT.RADIO);
			destDir[i].setText(destDesc[i]);
			destDir[i].setLayoutData(span);
			destDir[i].addListener(SWT.Selection, this);
		}
		destDir[0].setSelection(true);
		chooseFolder = new Button(destDirArea, SWT.PUSH);
		chooseFolder.setText(Messages.CreateEvaluationPage_7);
		chooseFolder.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(composite
						.getShell());
				saveTo.setText(dialog.open());
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		chooseFolder.setEnabled(false);
		saveTo = new Text(destDirArea, SWT.SINGLE);
		noSpan = new GridData(GridData.FILL_HORIZONTAL);
		noSpan.heightHint = 16;
		saveTo.setLayoutData(noSpan);
		saveTo.setEnabled(false);

		label = new Label(composite, SWT.NONE);
		label.setText(Messages.CreateEvaluationPage_8);
		industry = new Combo(composite, SWT.READ_ONLY);
		noSpan = new GridData(GridData.FILL_HORIZONTAL);
		industry.setLayoutData(noSpan);
		try {
			industry.setItems(EvaluationSchemes.getInstance()
					.getIndustryNames());
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		industry.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {

				try {

					IIndustryNode i = EvaluationSchemes.getInstance()
							.getIndustryByName(industry.getText());
					List<ISchemeNode> schemes = EvaluationSchemes.getInstance()
							.getSchemes(i);
					scheme.removeAll();
					for (ISchemeNode evalScheme : schemes) {
						String s = evalScheme.getName();
						if (StringUtils.isNotBlank(evalScheme.getScheme()
								.getAuthor())) {
							s += SystemConstants.DASH
									+ evalScheme.getScheme().getAuthor();
						}
						scheme.add(s);
					}
				} catch (ConfigurationException e) {
					e.printStackTrace();
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		label = new Label(composite, SWT.NONE);
		label.setText(Messages.CreateEvaluationPage_9);
		scheme = new Combo(composite, SWT.READ_ONLY);
		noSpan = new GridData(GridData.FILL_HORIZONTAL);
		scheme.setLayoutData(noSpan);
		scheme.addListener(SWT.Selection, this);

		label = new Label(composite, SWT.NONE);
		label.setText(Messages.CreateEvaluationPage_10);
		remark = new Text(composite, SWT.MULTI);
		noSpan = new GridData(GridData.FILL_BOTH);
		noSpan.heightHint = 50;
		remark.setLayoutData(noSpan);

		setControl(composite);
		setPageComplete(false);
		evalSerial.addListener(SWT.Modify, this);
		saveTo.addListener(SWT.Modify, this);
	}

	public void handleEvent(Event event) {
		if (event.widget == destDir[0]) {
			saveTo.setEnabled(false);
			chooseFolder.setEnabled(false);
		} else if (event.widget == destDir[1]) {
			saveTo.setEnabled(true);
			chooseFolder.setEnabled(true);
		}

		if (StringUtils.isNotBlank(evalSerial.getText())
				&& StringUtils.isNotBlank(scheme.getText())) {
			if (destDir[0].getSelection()
					|| (destDir[1].getSelection() && StringUtils
							.isNotBlank(saveTo.getText()))) {
				setPageComplete(true);
				return;
			}
		}
		setPageComplete(false);
		return;
	}

	public String getEvalSerial() {
		return evalSerial.getText();
	}

	public String getSaveTo() {
		String path;
		if (destDir[1].getSelection()) {
			path = saveTo.getText();
		} else {
			path = SystemConstants.DATA_DIR;
		}
		if (!path.endsWith(File.separator)) {
			path += File.separator;
		}
		return path;
	}

	public String getRemark() {
		return remark.getText();
	}

	public String getScheme() {
		StringBuilder s = new StringBuilder(industry.getText());
		s.append(SystemConstants.DASH);
		s.append(scheme.getText());
		return s.toString();
	}

}
