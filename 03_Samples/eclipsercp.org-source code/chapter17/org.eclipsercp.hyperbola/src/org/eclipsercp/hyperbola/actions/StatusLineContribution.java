/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipsercp.hyperbola.actions;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.StatusLineLayoutData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

public class StatusLineContribution extends ContributionItem {

	public final static int DEFAULT_CHAR_WIDTH = 40;

	private CLabel label;

	private Image image;

	private String text = ""; //$NON-NLS-1$

	private int widthHint = -1;

	private int heightHint = -1;

	private Listener listener;

	private int eventType;

	private String tooltip;

	public StatusLineContribution(String id) {
		this(id, DEFAULT_CHAR_WIDTH);
	}

	public StatusLineContribution(String id, int charWidth) {
		super(id);
		this.widthHint = charWidth;
		setVisible(false); // no text to start with
	}

	public void fill(Composite parent) {
		Label sep = new Label(parent, SWT.SEPARATOR);
		label = new CLabel(parent, SWT.SHADOW_NONE);

		GC gc = new GC(parent);
		gc.setFont(parent.getFont());
		FontMetrics fm = gc.getFontMetrics();
		Point extent = gc.textExtent(text);
		if (widthHint > 0) {
			widthHint = fm.getAverageCharWidth() * widthHint;
		} else {
			widthHint = extent.x;
		}
		heightHint = fm.getHeight();
		gc.dispose();

		StatusLineLayoutData statusLineLayoutData = new StatusLineLayoutData();
		statusLineLayoutData.widthHint = widthHint;
		statusLineLayoutData.heightHint = heightHint;
		label.setLayoutData(statusLineLayoutData);
		label.setText(text);
		label.setImage(image);
		if (listener != null) {
			label.addListener(eventType, listener);
		}
		if (tooltip != null) {
			label.setToolTipText(tooltip);
		}

		statusLineLayoutData = new StatusLineLayoutData();
		statusLineLayoutData.heightHint = heightHint;
		sep.setLayoutData(statusLineLayoutData);
	}

	public void addListener(int eventType, Listener listener) {
		this.eventType = eventType;
		this.listener = listener;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if (text == null)
			throw new NullPointerException();

		this.text = text;

		if (label != null && !label.isDisposed())
			label.setText(this.text);

		if (this.text.length() == 0) {
			if (isVisible()) {
				setVisible(false);
				IContributionManager contributionManager = getParent();

				if (contributionManager != null)
					contributionManager.update(true);
			}
		} else {
			if (!isVisible()) {
				setVisible(true);
				IContributionManager contributionManager = getParent();

				if (contributionManager != null)
					contributionManager.update(true);
			}
		}
	}

	public void setTooltip(String tooltip) {
		if (tooltip == null)
			throw new NullPointerException();

		this.tooltip = tooltip;

		if (label != null && !label.isDisposed()) {
			label.setToolTipText(this.tooltip);
		}
	}

	public void setImage(Image image) {
		if (image == null)
			throw new NullPointerException();

		this.image = image;

		if (label != null && !label.isDisposed())
			label.setImage(this.image);

		if (!isVisible()) {
			setVisible(true);
			IContributionManager contributionManager = getParent();

			if (contributionManager != null)
				contributionManager.update(true);
		}
	}
}
