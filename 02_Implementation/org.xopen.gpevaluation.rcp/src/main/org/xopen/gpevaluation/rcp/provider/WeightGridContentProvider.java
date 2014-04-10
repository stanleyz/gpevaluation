/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.provider;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.xopen.gpevaluation.rcp.model.IFactorNode;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class WeightGridContentProvider implements IStructuredContentProvider {

	public Object[] getElements(Object inputElement) {
		return ((IFactorNode) inputElement).getChildren().toArray();
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
}
