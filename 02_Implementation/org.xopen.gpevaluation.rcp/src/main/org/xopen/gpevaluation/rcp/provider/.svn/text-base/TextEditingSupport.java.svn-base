/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.provider;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.swt.SWT;
import org.xopen.gpevaluation.rcp.SystemConstants;
import org.xopen.gpevaluation.rcp.model.IFactorNode;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class TextEditingSupport extends EditingSupport {
	private CellEditor editor;
	private int column;
	private GridTableViewer viewer;

	public TextEditingSupport(GridTableViewer viewer, int i) {
		super(viewer);
		this.viewer = viewer;
		editor = new TextCellEditor(viewer.getGrid(), SWT.CENTER | SWT.FILL);
		this.column = i;
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return editor;
	}

	@Override
	protected Object getValue(Object element) {
		IFactorNode factorNode = (IFactorNode) element;
		String text = factorNode.getFactor().getImportances().getProperty(
				viewer.getGrid().getColumn(column).getText());

		return text == null ? SystemConstants.ONE : text;
	}

	@Override
	protected void setValue(Object element, Object value) {
		IFactorNode factorNode = (IFactorNode) element;
		factorNode.getFactor().getImportances().setProperty(
				viewer.getGrid().getColumn(column).getText(),
				String.valueOf(value));

		getViewer().update(element, null);
	}

}
