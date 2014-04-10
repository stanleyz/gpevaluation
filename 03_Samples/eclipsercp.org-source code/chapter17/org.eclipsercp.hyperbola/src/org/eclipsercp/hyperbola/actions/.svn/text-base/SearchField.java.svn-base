/*******************************************************************************
 * Copyright (c) 2005 Jean-Michel Lemieux, Jeff McAffer and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Hyperbola is an RCP application developed for the book
 *     Eclipse Rich Client Platform - 
 *         Designing, Coding, and Packaging Java Applications
 * See http://eclipsercp.org
 *
 * Contributors:
 *     Jean-Michel Lemieux and Jeff McAffer - initial API and implementation
 *******************************************************************************/
package org.eclipsercp.hyperbola.actions;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipsercp.hyperbola.Application;

public class SearchField extends Composite {

	static ImageDescriptor QUICKSEARCH_ICON = AbstractUIPlugin
			.imageDescriptorFromPlugin(Application.PLUGIN_ID,
					"icons/online.gif");

	private static final boolean MAC = "carbon".equals(SWT.getPlatform());

	private ToolBar optionsToolbar;

	private Text textBox;

	private int itemWidth;

	private Menu dropDownMenu;

	public SearchField(Composite parent, int width) {
		super(parent, MAC ? SWT.NONE : SWT.BORDER);

		itemWidth = width;

		this.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent ev) {
				int GAP = 0;
				int RIGHT_MARGIN = 3;
				Rectangle r = getClientArea();
				Point e = new Point(r.width, r.height);
				Point e1 = optionsToolbar.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				optionsToolbar.setBounds(0, (e.y - e1.y) / 2, e1.x, e1.y);
				int w = e.x - e1.x - GAP - RIGHT_MARGIN;
				Point e2 = textBox.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				textBox.setBounds(e1.x + GAP, (e.y - e2.y) / 2, w, e2.y);
			}
		});

		optionsToolbar = new ToolBar(this, SWT.FLAT);
		final ToolItem ti = new ToolItem(optionsToolbar, SWT.DROP_DOWN);
		ti.setImage(QUICKSEARCH_ICON.createImage(true));

		if (!MAC) {
			Color white = getDisplay().getSystemColor(SWT.COLOR_WHITE);
			setBackground(white);
			optionsToolbar.setBackground(white);
		}

		dropDownMenu = new Menu(parent.getShell(), SWT.POP_UP);

		for (int i = 0; i < 10; i++) {
			MenuItem mi = new MenuItem(dropDownMenu, SWT.RADIO);
			mi.setText("Option " + i + 1);
		}

		ti.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (event.detail == SWT.ARROW) {
					Rectangle rect = ti.getBounds();
					Point pt = new Point(rect.x, rect.y + rect.height);
					pt = optionsToolbar.toDisplay(pt);
					dropDownMenu.setLocation(pt.x, pt.y);
					dropDownMenu.setVisible(true);
				} else {
					search(textBox.getText());
				}
			}
		});

		optionsToolbar.pack();

		textBox = new Text(this, MAC ? SWT.BORDER : SWT.NONE);
		textBox.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {
				search(textBox.getText());
			}
		});
	}

	public Point computeSize(int wHint, int hHint, boolean changed) {
		Point e = super.computeSize(wHint, hHint, changed);
		e.x = Math.max(e.x, itemWidth);
		return e;
	}

	private void search(String text) {
		if (text.length() > 0) {
			MessageDialog
					.openInformation(getShell(), "Info", "Not Implemented");
		}
	}
}
