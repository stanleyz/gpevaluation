package org.xopen.gpevaluation.rcp;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.xopen.gpevaluation.rcp.ui.NavigationView;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(true);

		layout.addStandaloneView(NavigationView.ID, true, IPageLayout.LEFT,
				0.33f, editorArea);
		layout.getViewLayout(NavigationView.ID).setCloseable(false);
	}
}
