/*******************************************************************************
 * Copyright (c) 2007 Spirit Link GmbH
 * All rights reserved.
 * 
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package de.spiritlink.custom.ui.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import de.spiritlink.custom.ui.Activator;
import de.spiritlink.custom.ui.util.DummyJob;

/**
 * 
 * @author Tom Seidel tom.seidel@spiritlink.de
 */
public class DummyJobAction extends Action {
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        new DummyJob().schedule();
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.action.Action#getImageDescriptor()
     */
    @Override
    public ImageDescriptor getImageDescriptor() {
        return AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/gear_run.png");
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.action.Action#getToolTipText()
     */
    @Override
    public String getToolTipText() {
        return "Do something"; //$NON-NLS-1$
    }

}
