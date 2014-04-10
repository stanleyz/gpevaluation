/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.PlatformUI;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class PromptBox {
	public static int Prompt(String _title, String _message) {
		return Prompt(_title, _message, SWT.OK | SWT.ICON_INFORMATION
				| SWT.APPLICATION_MODAL);
	}

	public static int Prompt(String _title, String _message, int style) {
		MessageBox messageBox = new MessageBox(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), style);

		messageBox.setText(_title);
		messageBox.setMessage(_message);
		return messageBox.open();
	}

	public static MessageBox getPromptBox() {
		return new MessageBox(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell());
	}

	public static MessageBox getPromptBox(int style) {
		return new MessageBox(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), style);
	}
}
