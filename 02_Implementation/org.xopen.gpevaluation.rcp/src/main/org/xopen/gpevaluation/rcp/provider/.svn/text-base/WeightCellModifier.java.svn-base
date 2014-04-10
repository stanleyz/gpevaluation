/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.provider;

import java.util.Properties;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridItem;
import org.eclipse.swt.SWT;
import org.eclipse.ui.PlatformUI;
import org.xopen.gpevaluation.rcp.SystemConstants;
import org.xopen.gpevaluation.rcp.model.FactorNode;
import org.xopen.gpevaluation.rcp.model.Factors;
import org.xopen.gpevaluation.rcp.model.IFactorNode;
import org.xopen.gpevaluation.rcp.service.WeightCaculator;
import org.xopen.gpevaluation.rcp.ui.FactorDetailsPage;
import org.xopen.gpevaluation.rcp.ui.PromptBox;
import org.xopen.gpevaluation.rcp.util.DoubleUtil;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class WeightCellModifier implements ICellModifier {
	private FactorDetailsPage page;
	private int columnIndex;
	private int rowIndex;

	public WeightCellModifier(FactorDetailsPage page) {
		super();
		this.page = page;
	}

	public boolean canModify(Object element, String property) {
		setColumnRowNumber(element, property);
		if (columnIndex <= rowIndex) {
			return false;
		} else {
			return true;
		}
	}

	public Object getValue(Object element, String property) {
		IFactorNode factorNode = (IFactorNode) element;
		String text = factorNode.getFactor().getImportances().getProperty(
				property);

		return text == null ? SystemConstants.ONE : text;
	}

	public void modify(Object element, String property, Object value) {
		IFactorNode factorNode = (IFactorNode) ((GridItem) element).getData();

		/*
		 * If the value is the same as the old value, just return.
		 */
		if (value.equals(getValue(factorNode, property)))
			return;

		/*
		 * Check if the value entered is a double value, or prompt a mesage box,
		 * and return with null.
		 */
		if (!DoubleUtil.isPositiveDouble(String.valueOf(value))) {
			PromptBox.Prompt(Messages.WeightCellModifier_0,
					Messages.WeightCellModifier_1);
			return;
		}

		/*
		 * Save the value to memory DOM tree.
		 */
		String _value = String.valueOf(value);
		this.saveFactorNode(factorNode, property, _value);
		this.setRelevantFactorNode(factorNode.getFactor().getId(), _value);

		GridItem[] items = page.getGridViewer().getGrid().getItems();
		double[][] importances = new double[items.length][items.length];
		for (int i = 0; i < items.length; i++) {
			for (int j = 0; j < items.length; j++) {
				importances[i][j] = DoubleUtil.valueOf(items[i].getText(j));
			}
		}

		double[] relatedWeight = WeightCaculator.getRelatedWeight(importances);
		boolean consistency = true;
		if (items.length > 2) {
			consistency = WeightCaculator.checkConsistency(WeightCaculator
					.getMaxSignature(importances, relatedWeight), items.length);
		}

		if (!consistency) {
			page.getGridViewer().getGrid().getItem(rowIndex).setForeground(
					columnIndex,
					PlatformUI.getWorkbench().getDisplay().getSystemColor(
							SWT.COLOR_RED));

			MessageDialog.openError(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(),
					Messages.WeightCellModifier_2,
					Messages.WeightCellModifier_3);
		} else {
			page.getGridViewer().getGrid().getItem(rowIndex).setForeground(
					columnIndex,
					PlatformUI.getWorkbench().getDisplay().getSystemColor(
							SWT.COLOR_BLACK));
		}

		if (items.length < 3 || consistency) {
			IFactorNode node;
			for (int i = 0; i < page.getGridViewer().getGrid().getItemCount(); i++) {
				node = (FactorNode) page.getGridViewer().getGrid().getItem(i)
						.getData();
				try {
					Factors.getInstance().firePropertyChange(
							SystemConstants.FACTOR_WEIGHT,
							String.valueOf(relatedWeight[i]), node.getFactor());
				} catch (Exception e) {
					MessageDialog.openError(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(),
							Messages.WeightCellModifier_4,
							Messages.WeightCellModifier_5);
					return;
				}
				
				node.calculateRealWeight();
			}
		}
	}

	private void setColumnRowNumber(Object element, String property) {
		columnIndex = page.getColumnNames().indexOf(property);
		String factorId = ((IFactorNode) element).getFactor().getId();
		rowIndex = page.getColumnNames().indexOf(factorId);
	}

	private void setRelevantFactorNode(String _property, String _value) {
		Grid grid = (Grid) page.getGridViewer().getControl();
		IFactorNode factorNode = (IFactorNode) grid.getItem(columnIndex)
				.getData();

		this.saveFactorNode(factorNode, _property, DoubleUtil
				.getReciprocal(_value));
	}

	private void saveFactorNode(IFactorNode factorNode, String property,
			String value) {
		Properties importances = factorNode.getFactor().getImportances();
		importances.setProperty(property, String.valueOf(value));
		factorNode.setFactorProperty(SystemConstants.FACTOR_IMPORTANCES,
				importances);

		page.getGridViewer().update(factorNode, null);
	}
}
