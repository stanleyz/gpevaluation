package org.xopen.gpevaluation.rcp.provider;

import org.eclipse.nebula.widgets.grid.AbstractRenderer;
import org.eclipse.nebula.widgets.grid.internal.TextUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

@SuppressWarnings("restriction")
public class TextTopLeftRenderer extends AbstractRenderer {
	int leftMargin = 6;

	int rightMargin = 8;

	private String text;

	/**
	 * {@inheritDoc}
	 */
	public Point computeSize(GC gc, int wHint, int hHint, Object value) {
		return new Point(wHint, hHint);
	}

	/**
	 * {@inheritDoc}
	 */
	public void paint(GC gc, Object value) {
		gc.setBackground(getDisplay().getSystemColor(
				SWT.COLOR_WIDGET_BACKGROUND));

		gc.fillRectangle(getBounds().x, getBounds().y, getBounds().width - 1,
				getBounds().height + 1);

		gc.setForeground(getDisplay().getSystemColor(
				SWT.COLOR_WIDGET_DARK_SHADOW));

		gc.drawLine(getBounds().x + getBounds().width - 1, getBounds().y,
				getBounds().x + getBounds().width - 1, getBounds().y
						+ getBounds().height);

		gc.drawLine(getBounds().x, getBounds().y + getBounds().height - 1,
				getBounds().x + getBounds().width, getBounds().y
						+ getBounds().height - 1);

		int width = getBounds().width - leftMargin - rightMargin;

		gc.drawText(TextUtils.getShortText(gc, text, width), getBounds().x
				+ leftMargin, getBounds().y
				+ (getBounds().height - gc.textExtent(text).y) / 2, true);

	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
