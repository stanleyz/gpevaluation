package org.xopen.gpevaluation.rcp.ui;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.xopen.gpevaluation.rcp.model.EvaluationScheme;
import org.xopen.gpevaluation.rcp.model.Factors;
import org.xopen.gpevaluation.rcp.provider.SchemeEditorInput;

public class SchemeEditor extends FormEditor {
	public static final String ID = ViewIds.SCHEME_EDITOR;
	private EvaluationScheme scheme;
	private boolean dirty;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.editor.FormEditor#addPages()
	 */
	protected void addPages() {
		try {
			addPage(new SchemeGeneralPage(this));
			addPage(new SchemeEditorPage(this));
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	public void doSave(IProgressMonitor monitor) {
		try {
			XMLConfiguration config = (XMLConfiguration) Factors.getInstance()
					.getConfig(scheme.getPath());
			if (config != null) {
				config.save();
			}
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

		this.setDirty(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	public void doSaveAs() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 */
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
		scheme = ((SchemeEditorInput) input).getScheme();
		StringBuilder stringBuilder = new StringBuilder(scheme.getName());
		String author = scheme.getAuthor();
		if (author != null) {
			stringBuilder.append(" - ");
			stringBuilder.append(scheme.getAuthor());
		}
		setPartName(stringBuilder.toString());
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean _dirty) {
		this.dirty = _dirty;
		this.editorDirtyStateChanged();
	}

	@Override
	public void dispose() {
		Factors.getInstance().removeConfig(scheme.getPath());
		super.dispose();
	}
}
