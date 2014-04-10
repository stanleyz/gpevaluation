package com.example.zyx;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.handlers.HandlerUtil;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	
	/*remove the following comments to enable welcome page*/
    ///*
	private IWorkbenchAction introAction;
    //*/
	
	/*remove the following comments to enable About Dialog*/
	///*
	private IWorkbenchAction aboutAction;
	//*/
	
	private IWorkbenchAction exitAction;
    
	private IAction gameAction;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	protected void makeActions(final IWorkbenchWindow window) {
		 /*remove the following comments to enable welcome page*/
		///*
		introAction = ActionFactory.INTRO.create(window);
		register(introAction);
		//*/
		
        /*remove the following comments to enable About Dialog*/
		///*
		aboutAction = ActionFactory.ABOUT.create(window);
		register(aboutAction);
        //*/
		exitAction = ActionFactory.QUIT.create(window);
		register(exitAction);
		

		gameAction = new Action("Start") { //$NON-NLS-1$
			{
				setId("start");
			} //$NON-NLS-1$

			public void run() {
				try {
					IWorkbenchPage page = window.getActivePage();
					if (page != null) {
						IViewPart gameView = page.findView("com.example.zyx.GameView");
						
						if (gameView == null) {
							page.showView("com.example.zyx.GameView");
						} else {
							page.hideView(gameView);
						}
					}
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		};
		register(gameAction);
	}

	protected void fillMenuBar(IMenuManager menuBar) {
		IMenuManager viewMenu = new MenuManager("&Game", "Game");  //$NON-NLS-2$
		menuBar.add(viewMenu);
        viewMenu.add(gameAction);
        viewMenu.add(exitAction);
		
		MenuManager helpMenu = new MenuManager("&Help",
				IWorkbenchActionConstants.M_HELP);
		menuBar.add(helpMenu);
		
        /*remove the following comments to enable welcome page*/
		///*
		helpMenu.add(introAction);
        //*/
		
		/*remove the following comments to enable About Dialog*/
		///*
		helpMenu.add(new Separator());
		helpMenu.add(aboutAction);
		//*/
	}

}
