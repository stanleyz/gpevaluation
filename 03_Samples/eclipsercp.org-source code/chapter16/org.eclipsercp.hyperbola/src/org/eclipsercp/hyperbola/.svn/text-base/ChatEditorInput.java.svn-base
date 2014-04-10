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

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.Assert;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableElement;
import org.jivesoftware.smack.util.StringUtils;

/**
 * Input for a chat editor.
 */
public class ChatEditorInput implements IEditorInput, IPersistableElement {

	public final static String KEY_NAME = "user";

	private String participant;

	/**
	 * Creates a chat editor input on the given session and participant.
	 */
	public ChatEditorInput(String participant) {
		Assert.isNotNull(participant);
		this.participant = StringUtils.parseBareAddress(participant);
	}

	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return getParticipant();
	}

	public String getToolTipText() {
		return "";
	}

	public Object getAdapter(Class adapter) {
		return null;
	}

	public boolean equals(Object obj) {
  	    if (super.equals(obj))
			return true;
		if (!(obj instanceof ChatEditorInput))
			return false;
		ChatEditorInput other = (ChatEditorInput) obj;
		return this.participant.equals(other.participant);
	}

	public int hashCode() {
		return participant.hashCode();
	}

	public String getParticipant() {
		return participant;
	}

	public IPersistableElement getPersistable() {
		return this;
	}

	public String getFactoryId() {
		return ChatEditorInputFactory.ID;
	}

	public void saveState(IMemento memento) {
		memento.putString(KEY_NAME, getParticipant());
	}
}
