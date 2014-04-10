/*******************************************************************************
 * Copyright (c) 2007 Spirit Link GmbH
 * All rights reserved.
 * 
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package de.spiritlink.custom.ui.views;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import de.spiritlink.custom.ui.model.FolderItem;
import de.spiritlink.custom.ui.model.Item;

/**
 * 
 * @author Tom Seidel tom.seidel@spiritlink.de
 */
public class BrowseContentProvider extends ArrayContentProvider implements ITreeContentProvider {

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     */
    public Object[] getChildren(final Object parentElement) {
        
            return ((FolderItem) parentElement).getChildren().toArray();
        
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     */
    public Object getParent(final Object element) {
        return ((Item) element).getParent();
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     */
    public boolean hasChildren(final Object element) {
        return ((FolderItem) element).getChildren().size() > 0;
    }

   
}
