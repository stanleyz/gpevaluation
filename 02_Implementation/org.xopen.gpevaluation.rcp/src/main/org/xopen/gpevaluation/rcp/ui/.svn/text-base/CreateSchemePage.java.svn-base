/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.ui;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.xopen.gpevaluation.rcp.SystemConstants;
import org.xopen.gpevaluation.rcp.model.EvaluationSchemes;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class CreateSchemePage extends WizardPage {
	private Text schemeName, saveTo, remark;
	private Button chooseFolder;
	private Combo industry;
	private Button[] destDir;

	protected CreateSchemePage() {
		super(Messages.CreateSchemePage_0);
		setTitle(Messages.CreateSchemePage_1);
		setDescription(Messages.CreateSchemePage_2);
	}

	public void createControl(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.horizontalSpacing = 10;
		layout.verticalSpacing = 5;
		composite.setLayout(layout);
		Label label = new Label(composite, SWT.NONE);
		label.setText(Messages.CreateSchemePage_3);
		schemeName = new Text(composite, SWT.SINGLE);
		GridData noSpan = new GridData(GridData.FILL_HORIZONTAL);
		noSpan.heightHint = 16;
		schemeName.setLayoutData(noSpan);

		final Group destDirArea = new Group(composite, SWT.NONE);
		destDirArea.setText(Messages.CreateSchemePage_4);
		final GridData span = new GridData(GridData.FILL_HORIZONTAL);
		span.horizontalSpan = 2;
		destDirArea.setLayoutData(span);
		destDirArea.setLayout(layout);
		destDir = new Button[2];
		final String[] destDesc = { Messages.CreateSchemePage_5,
				Messages.CreateSchemePage_6 };
		SelectionListener choiceListener = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (((Button) e.getSource()).getText().equals(destDesc[1])) {
					chooseFolder.setEnabled(true);
					saveTo.setEnabled(true);
					if (StringUtils.isBlank(saveTo.getText())) {
						setPageComplete(false);
					} else {
						setPageComplete(true);
					}
				} else {
					chooseFolder.setEnabled(false);
					saveTo.setEnabled(false);
					if (StringUtils.isNotBlank(schemeName.getText())) {
						setPageComplete(true);
					} else {
						setPageComplete(false);
					}
				}
			}
		};
		for (int i = 0; i < destDir.length; i++) {
			destDir[i] = new Button(destDirArea, SWT.RADIO);
			destDir[i].setText(destDesc[i]);
			destDir[i].setLayoutData(span);
			destDir[i].addSelectionListener(choiceListener);
		}
		destDir[0].setSelection(true);
		chooseFolder = new Button(destDirArea, SWT.PUSH);
		chooseFolder.setText(Messages.CreateSchemePage_7);
		chooseFolder.setEnabled(false);
		chooseFolder.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(composite
						.getShell());
				saveTo.setText(dialog.open());
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		saveTo = new Text(destDirArea, SWT.SINGLE);
		noSpan = new GridData(GridData.FILL_HORIZONTAL);
		noSpan.heightHint = 16;
		saveTo.setLayoutData(noSpan);
		saveTo.setEnabled(false);
		saveTo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (StringUtils.isNotBlank(saveTo.getText())
						&& StringUtils.isNotBlank(schemeName.getText())) {
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}
		});

		label = new Label(composite, SWT.NONE);
		label.setText(Messages.CreateSchemePage_8);
		industry = new Combo(composite, SWT.NONE);
		noSpan = new GridData(GridData.FILL_HORIZONTAL);
		industry.setLayoutData(noSpan);
		try {
			industry.setItems(EvaluationSchemes.getInstance()
					.getIndustryNames());
		} catch (ConfigurationException e1) {
			e1.printStackTrace();
		}
		label = new Label(composite, SWT.NONE);
		label.setText(Messages.CreateSchemePage_9);
		remark = new Text(composite, SWT.MULTI);
		noSpan = new GridData(GridData.FILL_BOTH);
		noSpan.heightHint = 50;
		remark.setLayoutData(noSpan);

		schemeName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String s = schemeName.getText();
				if (s.indexOf(SystemConstants.DASH) >= 0) {
					setErrorMessage(Messages.CreateSchemePage_10);
					setPageComplete(false);
					return;
				}
				if (StringUtils.isNotBlank(s)) {
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
		});

		setControl(composite);
		setPageComplete(false);
	}

	public String getSchemeName() {
		return schemeName.getText();
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

	public String getIndustry() {
		return industry.getText();
	}
}
