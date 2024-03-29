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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipsercp.hyperbola.model.Session;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.packet.Presence;

public class AdapterFactory implements IAdapterFactory {

	private IWorkbenchAdapter groupAdapter = new IWorkbenchAdapter() {
		public Object getParent(Object o) {
			return Session.getInstance().getConnection().getRoster();
		}

		public String getLabel(Object o) {
			RosterGroup group = ((RosterGroup) o);
			int available = getNumAvailable(group);
			return group.getName() + " (" + available + "/"
					+ group.getEntryCount() + ")";			
		}
		
		private int getNumAvailable(RosterGroup group) {
			int available = 0;
			for (Iterator i = group.getEntries(); i.hasNext();) {
				RosterEntry entry = (RosterEntry) i.next();
				Presence presence = getPresence(entry);
				if (presence != null
						&& presence.getMode() != Presence.Mode.INVISIBLE)
					available++;
			}
			return available;
		}

		public ImageDescriptor getImageDescriptor(Object object) {
			return AbstractUIPlugin.imageDescriptorFromPlugin(
					Application.PLUGIN_ID, IImageKeys.GROUP);
		}

		public Object[] getChildren(Object o) {
			RosterGroup group = (RosterGroup) o;
			List result = new ArrayList();
			collect(group.getEntries(), result);
			return result.toArray();
		}
	};

	private IWorkbenchAdapter entryAdapter = new IWorkbenchAdapter() {
		public Object getParent(Object o) {
			return null;
		}

		public String getLabel(Object o) {
			RosterEntry entry = ((RosterEntry) o);
			return entry.getName() + " (" + entry.getUser() + ")";
		}

		public ImageDescriptor getImageDescriptor(Object object) {
			RosterEntry entry = ((RosterEntry) object);
			String key = presenceToKey(getPresence(entry));
			return AbstractUIPlugin.imageDescriptorFromPlugin(
					Application.PLUGIN_ID, key);
		}

		public Object[] getChildren(Object o) {
			return new Object[0];
		}				
	};
	
	private IWorkbenchAdapter rosterAdapter = new IWorkbenchAdapter() {
		public Object getParent(Object o) {
			return null;
		}

		public String getLabel(Object o) {
			return "";
		}

		public ImageDescriptor getImageDescriptor(Object object) {
			return null;
		}

		public Object[] getChildren(Object o) {
			Roster roster = (Roster) o;
			List result = new ArrayList();
			collect(roster.getGroups(), result);
			collect(roster.getUnfiledEntries(), result);
			return result.toArray();
		}			
	};

	private String presenceToKey(Presence p) {
		Presence.Mode presence = p != null ? p.getMode() : null;
		if (presence == Presence.Mode.AVAILABLE)
			return IImageKeys.ONLINE;
		if (presence == Presence.Mode.AWAY)
			return IImageKeys.AWAY;
		if (presence == Presence.Mode.DO_NOT_DISTURB)
			return IImageKeys.DO_NOT_DISTURB;
		if (presence == Presence.Mode.INVISIBLE)
			return IImageKeys.OFFLINE;
		return IImageKeys.OFFLINE;
	}
	
	private void collect(Iterator itr, List list) {
		while (itr.hasNext())
			list.add(itr.next());
	}
	
	private Presence getPresence(RosterEntry entry) {
		return Session.getInstance().getConnection().getRoster()
				.getPresence(entry.getUser());
	}

	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adapterType == IWorkbenchAdapter.class
				&& adaptableObject instanceof RosterGroup)
			return groupAdapter;
		if (adapterType == IWorkbenchAdapter.class
				&& adaptableObject instanceof RosterEntry)
			return entryAdapter;
		if (adapterType == IWorkbenchAdapter.class
				&& adaptableObject instanceof Roster)
			return rosterAdapter;
		return null;
	}

	public Class[] getAdapterList() {
		return new Class[] { IWorkbenchAdapter.class };
	}
}
