package de.spiritlink.custom.ui;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import de.spiritlink.custom.ui.views.BrowseView;
import de.spiritlink.custom.ui.views.ChildrenView;
import de.spiritlink.custom.ui.views.DetailsView;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(final IPageLayout layout) {
		final String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		layout.setFixed(true);
		
        layout.addStandaloneView(BrowseView.VIEW_ID,  false, IPageLayout.LEFT, 0.2f, editorArea);
        layout.addStandaloneView(ChildrenView.VIEW_ID,  false, IPageLayout.RIGHT, 0.2f, BrowseView.VIEW_ID);
        layout.addStandaloneView(DetailsView.VIEW_ID,  false, IPageLayout.BOTTOM, 0.8f, ChildrenView.VIEW_ID);
		/*layout.addView(BrowseView.VIEW_ID, IPageLayout.LEFT, 0.2f, editorArea);
		layout.addView(ChildrenView.VIEW_ID, IPageLayout.RIGHT, 0.2f, BrowseView.VIEW_ID);
		layout.addView(DetailsView.VIEW_ID, IPageLayout.BOTTOM, 0.8f,ChildrenView.VIEW_ID);*/
	}

}
