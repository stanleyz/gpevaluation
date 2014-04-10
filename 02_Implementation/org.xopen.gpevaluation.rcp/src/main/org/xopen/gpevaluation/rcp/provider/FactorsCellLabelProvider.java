/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.provider;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.xopen.gpevaluation.rcp.Activator;
import org.xopen.gpevaluation.rcp.model.IFactorNode;
import org.xopen.gpevaluation.rcp.util.DoubleUtil;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class FactorsCellLabelProvider extends ColumnLabelProvider {
	private int column;

	public FactorsCellLabelProvider(int i) {
		this.column = i;
	}

	public String getText(Object element) {
		IFactorNode node = (IFactorNode) element;
		switch (this.column) {
		case 0:
			return node.getName();
		case 1:
			return node.getFactor().getRemark();
		case 2:
			return node.getFactor().getWeight();
		case 3:
			return node.getFactor().getRealWeight();
		case 4:
			return node.getFactor().getFunction();
		case 5:
			return node.getFactor().getType();
		case 6:
			return node.getFactor().getThreshold();
		case 7:
			if (node.getFactorValue() != null
					&& node.getFactorValue().getRealValue() != null) {
				return node.getFactorValue().getRealValue();
			} else {
				return "";
			}
		case 8:
			if (node.hasChildren()) {
				return "";
			}
			return node.getFactorValue() != null ? node.getFactorValue()
					.getScore() : "";
		case 9:
			if (node.getFactorValue() != null
					&& node.getFactorValue().getDefiniteScore() != null) {
				return node.getFactorValue().getDefiniteScore();
			} else {
				return "";
			}
		}
		return null;
	}

	public Image getImage(Object element) {
		IFactorNode node = (IFactorNode) element;
		switch (this.column) {
		case 10:
			if(node.hasChildren())
				return null;
			if (DoubleUtil.valueOf(node.getFactorValue().getScore()) >= DoubleUtil
					.valueOf(node.getFactor().getThreshold())) {
				return Activator.getDefault().getImageRegistry().get(
						Activator.RIGHT_ICON);
			} else {
				return Activator.getDefault().getImageRegistry().get(
						Activator.WRONG_ICON);
			}
		}
		return null;
	}
}
