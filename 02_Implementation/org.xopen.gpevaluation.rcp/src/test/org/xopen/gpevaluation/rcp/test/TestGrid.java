/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.xopen.gpevaluation.rcp.test;

/*
 * TreeEditor example snippet: edit the text of a tree item (in place, fancy)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

public class TestGrid {

	private static final int NUM = 3;

	public static void main(String[] args) {
		final Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		Tree tree = new Tree(shell, SWT.BORDER);
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);
		for(int i=0;i<2;i++) {
			new TreeColumn(tree, SWT.CENTER);
		}
		
		tree.getColumn(0).setWidth(200);
		tree.getColumn(1).setWidth(80);
		tree.getColumn(0).setText("1");
		tree.getColumn(1).setText("2");
		
		for (int i = 0; i < NUM; i++) {
			TreeItem iItem = new TreeItem(tree, SWT.NONE);
			iItem.setText("Item " + (i + 1));
			for (int j = 0; j < NUM; j++) {
				TreeItem jItem = new TreeItem(iItem, SWT.NONE);
				jItem.setText("Sub Item " + (j + 1));
				for (int k = 0; k < NUM; k++) {
					new TreeItem(jItem, SWT.NONE).setText("Sub Sub Item "
							+ (k + 1));
				}
				jItem.setExpanded(true);
			}
			iItem.setExpanded(true);
		}

		final TreeEditor editor = new TreeEditor(tree);
		setEditor(tree.getItem(0), tree,editor);

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	private static void setEditor(TreeItem item, Tree tree, TreeEditor editor) {
		TreeItem[] items = item.getItems();
		if (items.length == 0) {
			Button bn = new Button(tree, SWT.CHECK);
			editor.horizontalAlignment = SWT.LEFT;
		    editor.grabHorizontal = true;
			editor.setEditor(bn, item, 1);
		}

		for (TreeItem subItem : items) {
			setEditor(subItem, tree, editor);
		}
	}
}
