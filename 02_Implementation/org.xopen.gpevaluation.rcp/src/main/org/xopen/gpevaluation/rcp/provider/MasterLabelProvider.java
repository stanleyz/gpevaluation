/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.provider;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.xopen.gpevaluation.rcp.model.ISimpleNode;

/**
 * This class is ...
 *
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class MasterLabelProvider extends LabelProvider {

	public Image getImage(Object element) {
		return ((ISimpleNode) element).getImage();
	}

	public String getText(Object element) {
		return ((ISimpleNode) element).getName();
	}

}
