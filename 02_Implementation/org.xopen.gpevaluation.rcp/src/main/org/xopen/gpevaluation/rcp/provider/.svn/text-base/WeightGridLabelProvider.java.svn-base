/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.provider;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.xopen.gpevaluation.rcp.SystemConstants;
import org.xopen.gpevaluation.rcp.model.IFactorNode;
import org.xopen.gpevaluation.rcp.ui.FactorDetailsPage;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class WeightGridLabelProvider extends LabelProvider implements
		ITableLabelProvider {
	private FactorDetailsPage page;

	public WeightGridLabelProvider(FactorDetailsPage page) {
		this.page = page;
	}

	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		IFactorNode factorNode = (IFactorNode) element;
		String columnId = page.getColumnNames().get(columnIndex);
		String text = factorNode.getFactor().getImportances().getProperty(
				columnId);

		return text == null ? SystemConstants.ONE : text;
	}
}
