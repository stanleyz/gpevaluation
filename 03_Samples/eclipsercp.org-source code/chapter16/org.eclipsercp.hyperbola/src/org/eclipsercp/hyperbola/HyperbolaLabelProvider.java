/*******************************************************************************
 * Copyright (c) 2004, 2005 Jean-Michel Lemieux, Jeff McAffer and others.
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
 *     Jean-Michel Lemieux and Jeff McAffer - initial implementation
 *******************************************************************************/
package org.eclipsercp.hyperbola;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class HyperbolaLabelProvider extends LabelProvider {

	private Map imageTable = new HashMap(7);

	protected final IWorkbenchAdapter getAdapter(Object element) {
		IWorkbenchAdapter adapter = null;
		if (element instanceof IAdaptable)
			adapter = (IWorkbenchAdapter) ((IAdaptable) element)
					.getAdapter(IWorkbenchAdapter.class);
		if (adapter == null)
			adapter = (IWorkbenchAdapter) Platform.getAdapterManager()
					.loadAdapter(element, IWorkbenchAdapter.class.getName());
		return adapter;
	}

	public final Image getImage(Object element) {
		IWorkbenchAdapter adapter = getAdapter(element);
		if (adapter == null) {
			return null;
		}
		ImageDescriptor descriptor = adapter.getImageDescriptor(element);
		if (descriptor == null) {
			return null;
		}
		Image image = (Image) imageTable.get(descriptor);
		if (image == null) {
			image = descriptor.createImage();
			imageTable.put(descriptor, image);
		}
		return image;
	}

	public final String getText(Object element) {
		IWorkbenchAdapter adapter = getAdapter(element);
		if (adapter == null) {
			return "";
		}
		return adapter.getLabel(element);
	}

	public void dispose() {
		if (imageTable != null) {
			for (Iterator i = imageTable.values().iterator(); i.hasNext();) {
				((Image) i.next()).dispose();
			}
			imageTable = null;
		}
	}
}
