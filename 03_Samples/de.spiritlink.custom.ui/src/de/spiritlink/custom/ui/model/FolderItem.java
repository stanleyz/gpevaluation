/*******************************************************************************
 * Copyright (c) 2007 Spirit Link GmbH
 * All rights reserved.
 * 
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package de.spiritlink.custom.ui.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author Tom Seidel tom.seidel@spiritlink.de
 */
public class FolderItem extends Item {
    
    /**
     * @param id
     * @param detail1
     * @param detail2
     * @param parent
     */
    public FolderItem(final String id,
            final FolderItem parent) {
        super(id, "", "", parent); //$NON-NLS-1$ //$NON-NLS-2$
    }

    private final List<Item> children = Collections.synchronizedList(new ArrayList<Item>());

    /**
     * @return the children
     */
    public List<Item> getChildren() {
        return this.children;
    }
    
    public void add(final Item item) {
        this.children.add(item);
    }

}
