/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.provider;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.xopen.gpevaluation.rcp.model.ISimpleNode;

/**
 * This class is ...
 *
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class MasterContentProvider implements ITreeContentProvider {

	@SuppressWarnings("unchecked")
	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof ArrayList) {
			return ((ArrayList) parentElement).toArray();
		}
		return ((ISimpleNode) parentElement).getChildren().toArray();
	}

	public Object getParent(Object element) {
		return ((ISimpleNode) element).getParent();
	}

	public boolean hasChildren(Object element) {
		return ((ISimpleNode) element).hasChildren();
	}

	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

}