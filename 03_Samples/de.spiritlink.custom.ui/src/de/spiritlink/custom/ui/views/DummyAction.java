/*******************************************************************************
 * Copyright (c) 2007 Spirit Link GmbH
 * All rights reserved.
 * 
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package de.spiritlink.custom.ui.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import de.spiritlink.custom.ui.Activator;

/**
 * 
 * @author Tom Seidel tom.seidel@spiritlink.de
 */
public class DummyAction extends Action {
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.action.Action#getToolTipText()
     */
    @Override
    public String getToolTipText() {
        return "Dummy";
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.action.Action#getImageDescriptor()
     */
    @Override
    public ImageDescriptor getImageDescriptor() {
        return AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, 
                "icons/calendar.png");
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Dummy", "This is a dummy implementation");
    }

}
