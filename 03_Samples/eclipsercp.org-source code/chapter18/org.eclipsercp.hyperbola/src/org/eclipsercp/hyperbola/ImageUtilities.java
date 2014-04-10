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
package org.eclipsercp.hyperbola;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class ImageUtilities {

	static ImageData[] loadDatas(String[] names) {
		ImageData[] datas = new ImageData[names.length];
		for (int i = 0; i < names.length; i++) {
			datas[i] = new ImageData(names[i]);
		}
		return datas;
	}

	static void loadImages(Display display, ImageData[] datas, Image[] images, Region[] regions) {
		for (int i = 0; i < datas.length; i++) {
			Region region = new Region(display);
			ImageData data = datas[i];
			ImageData mask = data.getTransparencyMask();
			Rectangle pixel = new Rectangle(0, 0, 1, 1);
			for (int y = 0; y < mask.height; y++) {
				for (int x = 0; x < mask.width; x++) {
					if (mask.getPixel(x, y) != 0) {
						pixel.x = data.x + x;
						pixel.y = data.y + y;
						region.add(pixel);
					}
				}
			}
			images[i] = new Image(display, datas[i]);
			regions[i] = region;
		}
	}

	static int visibleDelay(int ms) {
		if (ms < 20)
			return ms + 30;
		if (ms < 30)
			return ms + 10;
		return ms;
	}

	public static void main(String[] args) {
		final Display display = new Display();
		//	Shell z = new Shell();
		//	z.open();
		//	while (display.readAndDispatch());
		final Shell shell = new Shell(display, SWT.NO_TRIM | SWT.ON_TOP | SWT.NO_BACKGROUND);
		//	final String[] names = new String[]{
		//		"ibmblue.gif",
		//		"balloon.gif",
		//		"OP_LGO.GIF",
		//		"atlas1.gif"};
		//	final ImageData[] datas = loadDatas(names);
		final ImageData[] datas = new ImageLoader().load("C:/Eclipse/Latest/eclipse-0924/workspacePresentation/RCP Book Code/nonrect/wmp.gif");
		final Image[] images = new Image[datas.length];
		final Region[] regions = new Region[datas.length];
		loadImages(display, datas, images, regions);
		final int[] currentImage = new int[]{-1};
		Listener listener = new Listener() {

			int startX, startY;

			public void handleEvent(Event e) {
				if (e.type == SWT.MouseDown && e.button == 1) {
					startX = e.x;
					startY = e.y;
				}
				if (e.type == SWT.MouseMove && (e.stateMask & SWT.BUTTON1) != 0) {
					Point p = shell.toDisplay(e.x, e.y);
					p.x -= startX;
					p.y -= startY;
					shell.setLocation(p);
				}
				if (e.type == SWT.Paint) {
					ImageData data = datas[currentImage[0]];
					e.gc.drawImage(images[currentImage[0]], data.x, data.y);
				}
				if (e.type == SWT.KeyDown && e.character == SWT.ESC) {
					shell.dispose();
				}
			}
		};
		shell.addListener(SWT.KeyDown, listener);
		shell.addListener(SWT.MouseDown, listener);
		shell.addListener(SWT.MouseMove, listener);
		shell.addListener(SWT.Paint, listener);
		Runnable animate = new Runnable() {

			public void run() {
				if (shell.isDisposed())
					return;
				currentImage[0] = (currentImage[0] + 1) % images.length;
				ImageData data = datas[currentImage[0]];
				shell.setSize(data.x + data.width, data.y + data.height);
				shell.setRegion(regions[currentImage[0]]);
				shell.redraw();
				int time = datas[currentImage[0]].delayTime * 10;
				display.timerExec(time == 0 ? 500 : visibleDelay(time), this);
			}
		};
		animate.run();
		shell.setVisible(true);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		for (int i = 0; i < images.length; i++) {
			regions[i].dispose();
			images[i].dispose();
		}
		display.dispose();
	}
}