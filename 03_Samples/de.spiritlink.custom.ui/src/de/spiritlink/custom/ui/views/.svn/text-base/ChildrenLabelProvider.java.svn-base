/*******************************************************************************
 * Copyright (c) 2007 Spirit Link GmbH
 * All rights reserved.
 * 
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package de.spiritlink.custom.ui.views;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import de.spiritlink.custom.ui.model.FolderItem;
import de.spiritlink.custom.ui.model.Item;

/**
 * 
 * @author Tom Seidel tom.seidel@spiritlink.de
 */
public class ChildrenLabelProvider extends LabelProvider implements
        ITableLabelProvider {

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
     */
    public Image getColumnImage(final Object element, final int columnIndex) {
        Image returnValue = null;
        if (columnIndex == 0) {
            if (element instanceof FolderItem) {
                returnValue = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
            } else {
                returnValue = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
            }
        }
        return returnValue;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
     */
    public String getColumnText(final Object element, final int columnIndex) {
        String returnValue = null;
        switch (columnIndex) {
        case 0:
            returnValue = ((Item) element).getId();
            break;
        case 1:
            returnValue = ((Item) element).getDetail1();
            break;
        case 2:
            returnValue = ((Item) element).getDetail2();
            break;
        default:
            break;
        }
        return returnValue;
    }

}
