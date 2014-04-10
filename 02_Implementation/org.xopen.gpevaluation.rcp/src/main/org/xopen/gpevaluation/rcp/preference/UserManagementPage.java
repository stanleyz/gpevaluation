/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.preference;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This class is ...
 *
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class UserManagementPage extends PreferencePage implements
		IWorkbenchPreferencePage {

	/**
	 * 
	 */
	public UserManagementPage() {
	}

	/**
	 * @param title
	 */
	public UserManagementPage(String title) {
		super(title);
	}

	/**
	 * @param title
	 * @param image
	 */
	public UserManagementPage(String title, ImageDescriptor image) {
		super(title, image);
	}

	@Override
	protected Control createContents(Composite parent) {
		return null;
	}

	public void init(IWorkbench workbench) {
		setDescription(Messages.UserManagementPage_0);
	}

}
