/*******************************************************************************
 * Copyright (c) 2007 Spirit Link GmbH
 * All rights reserved.
 * 
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package de.spiritlink.custom.ui.model;

import org.eclipse.core.runtime.PlatformObject;

/**
 * 
 * @author Tom Seidel tom.seidel@spiritlink.de
 */
public class Item extends PlatformObject {
    
    private String id = null;
    
    private String detail1 = null;
    
    private String detail2 = null;
    
    private FolderItem parent = null;

    /**
     * @param id
     * @param detail1
     * @param detail2
     * @param parent
     */
    public Item(final String id, final String detail1, final String detail2, final FolderItem parent) {
        super();
        this.id = id;
        this.detail1 = detail1;
        this.detail2 = detail2;
        this.parent = parent;
        if (parent != null) {
            parent.add(this);
        }
    }

    /**
     * @return the id
     */
    public String getId() {
        return this.id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * @return the detail1
     */
    public String getDetail1() {
        return this.detail1;
    }

    /**
     * @param detail1 the detail1 to set
     */
    public void setDetail1(final String detail1) {
        this.detail1 = detail1;
    }

    /**
     * @return the detail2
     */
    public String getDetail2() {
        return this.detail2;
    }

    /**
     * @param detail2 the detail2 to set
     */
    public void setDetail2(final String detail2) {
        this.detail2 = detail2;
    }

    /**
     * @return the parent
     */
    public FolderItem getParent() {
        return this.parent;
    }
}
