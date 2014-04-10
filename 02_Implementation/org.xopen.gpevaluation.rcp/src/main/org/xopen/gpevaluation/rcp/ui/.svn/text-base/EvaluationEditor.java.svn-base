/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.ui;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.xopen.gpevaluation.rcp.SystemConstants;
import org.xopen.gpevaluation.rcp.model.Evaluations;
import org.xopen.gpevaluation.rcp.model.IEvaluationNode;
import org.xopen.gpevaluation.rcp.model.Session;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class EvaluationEditor extends FormEditor {
	private IEvaluationNode node;
	private boolean dirty, allowed=false;

	public EvaluationEditor() {
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		try {
			XMLConfiguration config = (XMLConfiguration) Evaluations
					.getInstance().getConfig(node.getEvaluation().getPath());
			if (config != null) {
				config.save();
			}
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

		this.setDirty(false);
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
		node = ((EvaluationEditorInput) input).getData();
		StringBuilder builder = new StringBuilder(node.getName());
		builder.append(SystemConstants.DASH);
		builder.append(node.getEvaluation().getAuthor());
		setPartName(builder.toString());
		/*
		 * check the permissions
		 */
		try {
			allowed = Session.getInstance().checkPermission(
					SystemConstants.PERM_USER);
		} catch (ConfigurationException e) {
		}
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void setFocus() {
	}

	@Override
	protected void addPages() {
		try {
			addPage(new EvaluationGeneralPage(this));
			addPage(new EvaluationEditorPage(this));
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void dispose() {
		Evaluations.getInstance().removeConfig(node.getEvaluation().getPath());
		super.dispose();
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean _dirty) {
		this.dirty = _dirty;
		this.editorDirtyStateChanged();
	}

	public boolean isAllowed() {
		return allowed;
	}
}
