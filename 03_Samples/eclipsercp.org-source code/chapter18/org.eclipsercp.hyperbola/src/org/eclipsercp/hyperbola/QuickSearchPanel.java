/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipsercp.hyperbola;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class QuickSearchPanel extends Composite {

	private Image nextImage;

	private Image previousImage;

	private Image closeImage;

	private Text findText;

	static public interface ICloseable {
		public void closed();
	}

	public QuickSearchPanel(Composite parent, final ICloseable closeable) {
		super(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 3;
		layout.marginWidth = 5;
		layout.numColumns = 4;
		setLayout(layout);
		Label l = new Label(this, SWT.NONE);
		l.setText("Find Contact: ");
		l.setLayoutData(new GridData(GridData.BEGINNING));
		findText = new Text(this, SWT.SINGLE | SWT.BORDER);
		findText.setLayoutData(new GridData(GridData.BEGINNING));

		ToolBar tb = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		tb.setLayoutData(new GridData(GridData.BEGINNING));
		ToolItem nextItem = new ToolItem(tb, SWT.PUSH);
		nextItem.setText("Find Next");
		nextImage = AbstractUIPlugin.imageDescriptorFromPlugin(
				Application.PLUGIN_ID, "icons/down.gif").createImage(true);
		previousImage = AbstractUIPlugin.imageDescriptorFromPlugin(
				Application.PLUGIN_ID, "icons/up.gif").createImage(true);
		closeImage = AbstractUIPlugin.imageDescriptorFromPlugin(
				Application.PLUGIN_ID, "icons/close.gif").createImage(true);
		nextItem.setImage(nextImage);
		ToolItem previousItem = new ToolItem(tb, SWT.PUSH);
		previousItem.setText("Previous Next");
		previousItem.setImage(previousImage);

		ToolBar closeTb = new ToolBar(this, SWT.FLAT);
		ToolItem closeItem = new ToolItem(closeTb, SWT.PUSH);
		closeItem.setImage(closeImage);
		closeItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (closeable != null)
					closeable.closed();
			}
		});
		closeTb.setLayoutData(new GridData(GridData.END, GridData.BEGINNING,
				true, false));
		findText.setFocus();
	}
	
	public boolean setFocus() {
		super.setFocus();
		return findText.setFocus();
	}

	public void dispose() {
		nextImage.dispose();
		previousImage.dispose();
		closeImage.dispose();
	}
}
