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

import org.eclipse.jface.util.Assert;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.*;
import org.eclipse.ui.part.EditorInputTransfer;

public class EditorAreaDropAdapter extends DropTargetAdapter {
	private IWorkbenchWindow window;

	public EditorAreaDropAdapter(IWorkbenchWindow window) {
		this.window = window;
	}

	public void dragEnter(DropTargetEvent event) {
		event.detail = DND.DROP_COPY;
	}

	public void dragOperationChanged(DropTargetEvent event) {
		event.detail = DND.DROP_COPY;
	}

	public void drop(final DropTargetEvent event) {
		Display d = window.getShell().getDisplay();
		final IWorkbenchPage page = window.getActivePage();
		if (page != null) {
			d.asyncExec(new Runnable() {
				public void run() {
					asyncDrop(event, page);
				}
			});
		}
	}

	private void asyncDrop(DropTargetEvent event, IWorkbenchPage page) {

		/* Open Editor for generic IEditorInput */
		if (EditorInputTransfer.getInstance().isSupportedType(
				event.currentDataType)) {
			/*
			 * event.data is an array of EditorInputData, which contains an
			 * IEditorInput and the corresponding editorId
			 */
			Assert
					.isTrue(event.data instanceof EditorInputTransfer.EditorInputData[]);
			EditorInputTransfer.EditorInputData[] editorInputs = (EditorInputTransfer.EditorInputData []) event.data;
			for (int i = 0; i < editorInputs.length; i++) {
				IEditorInput editorInput = editorInputs[i].input;
				String editorId = editorInputs[i].editorId;
				openNonExternalEditor(page, editorInput, editorId);
			}
		}
	}

	/**
	 * Opens an editor for the given editor input and editor id combination on
	 * the given workbench page in response to a drop on the workbench editor
	 * area. In contrast to other ways of opening an editor, we never open an
	 * external editor in this case (since external editors appear in their own
	 * window and not in the editor area). The operation fails silently if the
	 * editor cannot be opened.
	 * 
	 * @param page
	 *            the workbench page
	 * @param editorInput
	 *            the editor input
	 * @param editorId
	 *            the editor id
	 * @return the editor part that was opened, or <code>null</code> if no
	 *         editor was opened
	 */
	private IEditorPart openNonExternalEditor(IWorkbenchPage page,
			IEditorInput editorInput, String editorId) {
		IEditorPart result;
		try {
			IEditorRegistry editorReg = PlatformUI.getWorkbench()
					.getEditorRegistry();
			IEditorDescriptor editorDesc = editorReg.findEditor(editorId);
			if (editorDesc != null && !editorDesc.isOpenExternal()) {
				result = page.openEditor(editorInput, editorId);
			} else {
				result = null;
			}
		} catch (PartInitException e) {
			// silently ignore problems opening the editor
			result = null;
		}
		return result;
	}

}