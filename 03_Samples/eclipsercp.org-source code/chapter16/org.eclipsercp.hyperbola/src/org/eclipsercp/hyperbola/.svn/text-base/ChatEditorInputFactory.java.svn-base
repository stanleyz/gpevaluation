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

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;

public class ChatEditorInputFactory implements IElementFactory {

	public static final String ID = "org.eclipsercp.hyperbola.chatinput";

	public IAdaptable createElement(IMemento memento) {
		String name = memento.getString(ChatEditorInput.KEY_NAME);
		if (name != null)
			return new ChatEditorInput(name);
		return null;
	}
}