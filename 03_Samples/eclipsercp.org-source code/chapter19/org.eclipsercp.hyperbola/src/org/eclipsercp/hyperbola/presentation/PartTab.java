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
package org.eclipsercp.hyperbola.presentation;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.presentations.IPresentablePart;

public class PartTab extends Canvas implements PaintListener {

	private String text;

	private Image image;

	private boolean selected;

	private boolean focus = false;

	// private IPresentablePart part;

	private static final int VERT_SPACING = 5;

	private static final int HORIZ_SPACING = 3;

	private boolean showImage;

	private boolean showText;

	public PartTab(Composite parent) {
		super(parent, SWT.NONE);
		addPaintListener(this);
	}

	public void paintControl(PaintEvent e) {
		Rectangle titleRect = getClientArea();
		int x = titleRect.x + VERT_SPACING;
		GC gc = e.gc;
		setBackground(getParent().getBackground());
		fill(gc, titleRect.x, titleRect.y, titleRect.width - 1,
				titleRect.height);

		Image image = getImage();
		if (image != null && showImage) {
			Rectangle imageBounds = image.getBounds();
			int imageX = x;
			int imageHeight = imageBounds.height;
			int imageY = (titleRect.height - imageHeight) / 2;
			int imageWidth = imageBounds.width * imageHeight
					/ imageBounds.height;
			gc.drawImage(image, imageBounds.x, imageBounds.y,
					imageBounds.width, imageBounds.height, imageX, imageY,
					imageWidth, imageHeight);
			x += imageWidth + VERT_SPACING;
		}

		int textWidth = titleRect.width - 1;
		if (textWidth > 0 && text != null && showText) {
			gc.setFont(getFont());
			Point extent = gc.textExtent(text, SWT.DRAW_TRANSPARENT
					| SWT.DRAW_MNEMONIC);
			int textY = titleRect.y + (titleRect.height - extent.y) / 2;

			if (selected)
				gc.setForeground(e.display.getSystemColor(SWT.COLOR_BLACK));
			else
				gc.setForeground(e.display.getSystemColor(SWT.COLOR_DARK_GRAY));
			gc.setFont(JFaceResources.getDefaultFont());
			gc.drawText(text, x, textY, SWT.DRAW_TRANSPARENT
					| SWT.DRAW_MNEMONIC);
		}

	}

	public Point computeSize(int wHint, int hHint) {
		int width = VERT_SPACING;
		int height = HORIZ_SPACING;
		GC gc = new GC(this);
		if (image != null && showImage) {
			Rectangle imageBounds = image.getBounds();
			height = imageBounds.height + HORIZ_SPACING;
			width += imageBounds.width + VERT_SPACING;
		}

		if (text != null && showText) {
			Point extent = gc.textExtent(text, SWT.DRAW_TRANSPARENT
					| SWT.DRAW_MNEMONIC);
			width += extent.x + VERT_SPACING;
			height = Math.max(height, extent.y) + HORIZ_SPACING;
		}

		if (wHint != SWT.DEFAULT)
			width = wHint;
		if (hHint != SWT.DEFAULT)
			height = hHint;
		gc.dispose();
		return new Point(width, height);
	}

	private void fill(GC gc, int x, int y, int width, int height) {
		Color fg = null;

		if (!selected)
			fg = getDisplay().getSystemColor(SWT.COLOR_WHITE);
		// else if(focus)
		// fg = getDisplay().getSystemColor(SWT.COLOR_BLUE);
		else
			fg = getDisplay().getSystemColor(SWT.COLOR_GREEN);

		gc.setForeground(fg);

		gc.fillGradientRectangle(x, y, x + width, y + height, true);
	}

	public Point computeSize(int wHint, int hHint, boolean changed) {
		return computeSize(wHint, hHint);
	}

	public void setText(String text) {
		this.text = text;
		redraw();
	}

	public String getText() {
		return this.text;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Image getImage() {
		return this.image;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		redraw();
	}

	public boolean getSelected() {
		return this.selected;
	}

	public boolean isFocus() {
		return focus;
	}

	public void setFocus(boolean focus) {
		this.focus = focus;
	}

	public void setShowImage(boolean showImage) {
		this.showImage = showImage;
	}

	public void setShowText(boolean showText) {
		this.showText = showText;
	}
}