/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.editor.FormEditor;
import org.xopen.gpevaluation.rcp.model.IEvaluationNode;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class EvaluationEditorInput implements IEditorInput {
	private final IEvaluationNode node;

	public EvaluationEditorInput(IEvaluationNode _node) {
		this.node = _node;
	}

	public IEvaluationNode getData() {
		return this.node;
	}

	public boolean exists() {
		return false;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return this.node.toString();
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public String getToolTipText() {
		return node.toString();
	}

	@SuppressWarnings("unchecked")
	public Object getAdapter(Class adapter) {
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EvaluationEditorInput) {
			IWorkbenchPage page = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			FormEditor editor = null;
			if (page != null) {
				editor = (FormEditor) page.getActiveEditor();
			}
			if (editor != null
					&& editor.getActivePageInstance() instanceof EvaluationEditorPage) {
				((EvaluationEditorPage) editor.getActivePageInstance())
						.getViewer().refresh();
				((EvaluationEditorPage) editor.getActivePageInstance())
						.getViewer().expandAll();
			}

			return ((EvaluationEditorInput) obj).getData().equals(this.node);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.node.hashCode();
	}

}
