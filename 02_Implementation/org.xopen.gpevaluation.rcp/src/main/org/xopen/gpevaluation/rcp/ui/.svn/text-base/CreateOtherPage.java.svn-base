/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.ui;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.xopen.gpevaluation.rcp.SystemConstants;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class CreateOtherPage extends WizardPage {
	private Tree tree;

	protected CreateOtherPage() {
		super(Messages.CreateOtherPage_0);
		setTitle(Messages.CreateOtherPage_1);
		setDescription(Messages.CreateOtherPage_2);
	}

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		composite.setLayout(layout);
		Label label = new Label(composite, SWT.NONE);
		label.setText(Messages.CreateOtherPage_3);
		tree = new Tree(composite, SWT.V_SCROLL | SWT.H_SCROLL | SWT.SINGLE
				| SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_BOTH);
		tree.setLayoutData(gd);

		String[] itemNames = { Messages.CreateOtherPage_4, Messages.CreateOtherPage_5 };
		TreeItem item;
		for (int i = 0; i < itemNames.length; i++) {
			item = new TreeItem(tree, SWT.NONE);
			item.setText(itemNames[i]);
			item.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(
					ISharedImages.IMG_OBJ_ELEMENT));
		}
		TreeItem normal = new TreeItem(tree, SWT.NONE);
		normal.setText(Messages.CreateOtherPage_6);
		normal.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_OBJ_FOLDER));
		for (int i = 0; i < itemNames.length; i++) {
			item = new TreeItem(normal, SWT.NONE);
			item.setText(itemNames[i]);
			item.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(
					ISharedImages.IMG_OBJ_ELEMENT));
		}

		tree.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if (SystemConstants.ITEM_CREATE_FOLDER
						.indexOf(((TreeItem) e.item).getText()) < 0) {
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		setControl(composite);
		setPageComplete(false);
	}

	public TreeItem getSelection() {
		return tree.getSelection()[0];
	}
}
