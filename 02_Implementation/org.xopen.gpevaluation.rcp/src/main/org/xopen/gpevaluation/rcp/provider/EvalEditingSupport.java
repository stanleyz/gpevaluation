/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.provider;

import org.apache.commons.configuration.ConfigurationException;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.xopen.gpevaluation.rcp.model.IFactorNode;
import org.xopen.gpevaluation.rcp.ui.EvaluationEditorPage;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class EvalEditingSupport extends EditingSupport {
	private CellEditor editor;
	private int column;

	public EvalEditingSupport(FormPage page, int column) {
		super(((EvaluationEditorPage) page).getViewer());
		this.column = column;
		switch (column) {
		case 7:
			editor = new TextCellEditor(((EvaluationEditorPage) page)
					.getViewer().getTree());
			editor.addListener((ICellEditorListener) page);
			break;
		}
	}

	protected boolean canEdit(Object element) {
		if (((IFactorNode) element).hasChildren()) {
			return false;
		}
		return true;
	}

	protected CellEditor getCellEditor(Object element) {
		return editor;
	}

	protected Object getValue(Object element) {
		IFactorNode node = (IFactorNode) element;
		switch (this.column) {
		case 7:
			if (node.getFactorValue() != null
					&& node.getFactorValue().getRealValue() != null) {
				return node.getFactorValue().getRealValue();
			} else {
				return "";
			}
		}
		return null;
	}

	protected void setValue(Object element, Object value) {
		try {
			((IFactorNode) element).setFactorValues(String.valueOf(value));
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

		updateView(element);
	}

	private void updateView(Object element) {
		IFactorNode node = (IFactorNode) element;
		getViewer().update(element, null);
		if (node.getParent() != null) {
			updateView(node.getParent());
		}
	}
}
