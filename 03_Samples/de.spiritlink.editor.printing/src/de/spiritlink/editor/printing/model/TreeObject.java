package de.spiritlink.editor.printing.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.PlatformObject;




/**
 * @author tmseidel
 *
 */
public class TreeObject extends PlatformObject {
	
	protected String description = null;
    protected TreeObject parent = null;
    protected List<TreeObject> children = Collections.synchronizedList(new ArrayList<TreeObject>());
    
    
	public TreeObject(String data) {
		this.description = data;
	}
	
	public void setParent(TreeObject parent) {
		this.parent = parent;
	}
    
	public TreeObject getParent() {
		return this.parent;
	}
    
	
    
    public void addChild(TreeObject child) {
        this.children.add(child);
        child.setParent(this);
      
    }
  
    
    public void removeChild(TreeObject child) {
        this.children.remove(child);
        child.setParent(null);
  
    }
    
  
    
    /**
     * Returns all Children under this tree-object
     * @return the children
     */
    public TreeObject [] getChildren() {
        return this.children.toArray(new TreeObject[this.children.size()]);
    }
    
    
    /**
     * Checks if the current tree-object has children
     * @return true if {@link #getChildren()} has a length greater 0, else false
     */
    public boolean hasChildren() {
        return this.children.size() > 0;
    }
    
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class key) {
		return super.getAdapter(key);
	}
	
	
	/**
     * Returns the wrapped dataobject
	 * @return the data-object
	 */
	public String getDataObject() {
        return this.description;
    }

}
