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

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.IFontDecorator;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.EditorInputTransfer;
import org.eclipse.ui.part.ViewPart;
import org.eclipsercp.hyperbola.model.Session;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;

public class ContactsView extends ViewPart {

	public static final String ID = "org.eclipsercp.hyperbola.views.contacts";

	private TreeViewer treeViewer;

	private ArrayList openEditors = new ArrayList();
	
	private IPartListener partListener = new IPartListener(){
		public void partOpened(IWorkbenchPart part) {
			trackOpenChatEditors(part);
		}
	
		public void partDeactivated(IWorkbenchPart part) {
		}
	
		public void partClosed(IWorkbenchPart part) {
			trackOpenChatEditors(part);		
		}
	
		public void partBroughtToTop(IWorkbenchPart part) {
		}
	
		public void partActivated(IWorkbenchPart part) {
		}
		
		private void trackOpenChatEditors(IWorkbenchPart part) {
			if(! (part instanceof ChatEditor)) return;
			ChatEditor editor = (ChatEditor)part;
			ChatEditorInput input = (ChatEditorInput) editor.getEditorInput();
			String participant = input.getParticipant();
			if(openEditors.contains(participant))
				openEditors.remove(participant);
			else
				openEditors.add(participant);
			treeViewer.refresh();
		}		
	};
	
	private class ContactsDecorator implements ILabelDecorator, IFontDecorator {

		public Image decorateImage(Image image, Object element) {
			return null;
		}

		public String decorateText(String text, Object element) {
			return null;
		}

		public void addListener(ILabelProviderListener listener) {
		}

		public void dispose() {
		}

		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {
		}

		public Font decorateFont(Object element) {
			if(element instanceof RosterEntry) {
				RosterEntry entry = (RosterEntry)element;
				if(ContactsView.this.openEditors.contains(entry.getUser()))
					return JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);
			}
			return null;
		}
	}

	public ContactsView() {
		super();
	}

	public void createPartControl(Composite parent) {
		treeViewer = new TreeViewer(parent, SWT.BORDER | SWT.MULTI
				| SWT.V_SCROLL);
		getSite().setSelectionProvider(treeViewer);
		HyperbolaLabelProvider hyperbolaLabelProvider = new HyperbolaLabelProvider();
		DecoratingLabelProvider decorator = new DecoratingLabelProvider(hyperbolaLabelProvider, new ContactsDecorator());
		treeViewer.setLabelProvider(decorator);
		treeViewer.setContentProvider(new HyperbolaContentProvider());
		treeViewer.setInput(Session.getInstance().getConnection().getRoster());
		Session.getInstance().getConnection().getRoster().addRosterListener(new RosterListener() {
			public void presenceChanged(String arg0) {
				refresh();
			};
			public void rosterModified() {
				refresh();
			};
		});
		initDragAndDrop(treeViewer);
		getViewSite().getPage().addPartListener(partListener);
	}

	private void refresh() {
		getSite().getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				treeViewer.refresh();
			}
		});
	}

	public void setFocus() {
		treeViewer.getControl().setFocus();
	}
	
	public void dispose() {
		getSite().getPage().removePartListener(partListener);
	}
	
	protected void initDragAndDrop(final StructuredViewer viewer) {
		int operations = DND.DROP_COPY;
		Transfer[] transferTypes = new Transfer[] { EditorInputTransfer
				.getInstance() };
		DragSourceListener listener = new DragSourceAdapter() {
			public void dragSetData(DragSourceEvent event) {
				if (EditorInputTransfer.getInstance().isSupportedType(
						event.dataType)) {
					String[] names = getUsers();
					EditorInputTransfer.EditorInputData[] inputs = new EditorInputTransfer.EditorInputData[names.length];
					if (names.length > 0) {
						for (int i = 0; i < names.length; i++) {
							String name = names[i];
							inputs[i] = EditorInputTransfer
									.createEditorInputData(ChatEditor.ID,
											new ChatEditorInput(name));
						}
						event.data = inputs;
						return;
					}
				}
				event.doit = false;
			}

			public void dragFinished(DragSourceEvent event) {
			}

			public void dragStart(DragSourceEvent event) {
				super.dragStart(event);
			}
		};
		viewer.addDragSupport(operations, transferTypes, listener);
	}

	private String[] getUsers() {
		IStructuredSelection ss = (IStructuredSelection) treeViewer
				.getSelection();
		ArrayList users = new ArrayList();
		if (!ss.isEmpty()) {
			for (Iterator it = ss.iterator(); it.hasNext();) {
				Object element = it.next();
				if (element instanceof RosterEntry) {
					String name = ((RosterEntry) element).getUser();
					users.add(name);
				} else {
					users.clear();
					break;
				}
			}
		}
		return (String[]) users.toArray(new String[users.size()]);
	}
}
