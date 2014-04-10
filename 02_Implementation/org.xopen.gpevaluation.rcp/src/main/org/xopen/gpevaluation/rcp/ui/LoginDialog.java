/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.ui;

import java.util.Hashtable;
import java.util.Iterator;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.branding.IProductConstants;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;
import org.xopen.gpevaluation.rcp.Activator;
import org.xopen.gpevaluation.rcp.SystemConstants;
import org.xopen.gpevaluation.rcp.model.Session;
import org.xopen.gpevaluation.rcp.model.User;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class LoginDialog extends Dialog {
	private Combo userNameCombo;
	private Text passwordText;
	private Button rememberButton, autoLoginButton, removeCurrentUser;
	private boolean rememberPassword, autoLogin;
	private String autoLoginUserName;
	private Image[] images;
	private final Hashtable<String, User> users = new Hashtable<String, User>();
	private Preferences preferences, savedUsers;
	public final static String AUTO_LOGIN = "user.auto.login"; //$NON-NLS-1$
	private static final String SAVED = "saved.users"; //$NON-NLS-1$
	private static final String PASSWORD = "saved.user.password"; //$NON-NLS-1$
	private static final String LAST_USER = "saved.last.user.name"; //$NON-NLS-1$

	private User user;

	public LoginDialog(Shell parentShell) {
		super(parentShell);
		preferences = new ConfigurationScope().getNode(Activator.PLUGIN_ID);
		savedUsers = preferences.node(SAVED);
		loadDescriptors();
	}

	protected void buttonPressed(int buttonId) {
		if (user == null) {
			user = new User();
		}
		user.setName(userNameCombo.getText());
		user.setPassword(passwordText.getText());
		super.buttonPressed(buttonId);
	}

	protected void createButtonsForButtonBar(Composite parent) {
		removeCurrentUser = createButton(parent, IDialogConstants.CLIENT_ID,
				Messages.LoginDialog_4, false);
		removeCurrentUser.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				users.remove(userNameCombo.getText());
				deleteUserPref();
				initializeUsers(""); //$NON-NLS-1$
			}
		});
		removeCurrentUser.setEnabled(!userNameCombo.getText().equals("")); //$NON-NLS-1$
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	private void initializeUsers(String defaultUser) {
		userNameCombo.removeAll();
		passwordText.setText(""); //$NON-NLS-1$
		for (Iterator<String> it = users.keySet().iterator(); it.hasNext();)
			userNameCombo.add((String) it.next());
		int index = Math.max(userNameCombo.indexOf(defaultUser), 0);
		userNameCombo.select(index);

		if (!userNameCombo.getText().equals("")) { //$NON-NLS-1$
			autoLoginButton.setSelection(userNameCombo.getText().equals(
					autoLoginUserName));
		}

		boolean b = !passwordText.getText().equals("") //$NON-NLS-1$
				&& !userNameCombo.getText().equals(""); //$NON-NLS-1$
		autoLoginButton.setEnabled(b);
		rememberButton.setEnabled(b);
	}

	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		composite.setLayout(layout);

		Label label = new Label(composite, SWT.NONE);
		label.setText(Messages.LoginDialog_11);
		GridData gd = new GridData(GridData.BEGINNING, GridData.CENTER, false,
				false, 2, 1);
		label.setLayoutData(gd);

		label = new Label(composite, SWT.NONE);
		label.setText(Messages.LoginDialog_12);
		gd = new GridData(GridData.END, GridData.CENTER, false, false);
		label.setLayoutData(gd);

		userNameCombo = new Combo(composite, SWT.BORDER);
		gd = new GridData(GridData.FILL, GridData.FILL, true, false);
		gd.widthHint = convertHeightInCharsToPixels(20);
		userNameCombo.setLayoutData(gd);
		userNameCombo.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event event) {
				user = (User) users.get(userNameCombo.getText());
				if (user != null) {
					passwordText.setText(user.getPassword());
				} else {
					passwordText.setText(""); //$NON-NLS-1$
				}
				rememberButton.setSelection(!passwordText.getText().equals("")); //$NON-NLS-1$

				if (removeCurrentUser != null) {
					removeCurrentUser.setEnabled(!userNameCombo.getText()
							.equals("")); //$NON-NLS-1$
				}

				if (!userNameCombo.getText().equals("")) { //$NON-NLS-1$
					autoLoginButton.setSelection(userNameCombo.getText()
							.equals(autoLoginUserName));
				}
				boolean b = !passwordText.getText().equals("") //$NON-NLS-1$
						&& !userNameCombo.getText().equals(""); //$NON-NLS-1$
				autoLoginButton.setEnabled(b);
				rememberButton.setEnabled(b);
			}
		});

		label = new Label(composite, SWT.NONE);
		label.setText(Messages.LoginDialog_19);
		gd = new GridData(GridData.END, GridData.CENTER, false, false);
		label.setLayoutData(gd);

		passwordText = new Text(composite, SWT.BORDER | SWT.PASSWORD);
		gd = new GridData(GridData.FILL, GridData.FILL, true, false);
		passwordText.setLayoutData(gd);
		passwordText.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event event) {
				boolean b = !passwordText.getText().equals("") //$NON-NLS-1$
						&& !userNameCombo.getText().equals(""); //$NON-NLS-1$
				autoLoginButton.setEnabled(b);
				rememberButton.setEnabled(b);
				if(!b) {
					autoLoginButton.setSelection(b);
					rememberButton.setSelection(b);
				}
			}
		});

		Composite buttonArea = new Composite(composite, SWT.NONE);
		gd = new GridData(SWT.BEGINNING, SWT.CENTER, true, true, 2, 1);
		buttonArea.setLayoutData(gd);
		RowLayout rl = new RowLayout(SWT.HORIZONTAL);
		rl.justify = true;
		rl.center = true;
		rl.marginLeft = 60;
		rl.spacing = 30;
		rl.marginTop = 5;
		buttonArea.setLayout(rl);

		rememberButton = new Button(buttonArea, SWT.CHECK);
		rememberButton.setText(Messages.LoginDialog_22);
		rememberButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				rememberPassword = rememberButton.getSelection();
				if (!rememberPassword) {
					autoLoginButton.setSelection(rememberPassword);
				}
			}
		});

		autoLoginButton = new Button(buttonArea, SWT.CHECK);
		autoLoginButton.setText(Messages.LoginDialog_23);
		autoLoginButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				autoLogin = autoLoginButton.getSelection();
				if (autoLogin) {
					rememberButton.setSelection(true);
					rememberPassword = true;
				}
			}
		});

		String s = ""; //$NON-NLS-1$
		if (user != null) {
			s = user.getPassword();
			rememberButton.setSelection(!s.equals("")); //$NON-NLS-1$
			s = user.getName();
		}
		initializeUsers(s);

		boolean b = !passwordText.getText().equals("") //$NON-NLS-1$
				&& !userNameCombo.getText().equals(""); //$NON-NLS-1$
		autoLoginButton.setEnabled(b);
		rememberButton.setEnabled(b);

		return composite;
	}

	protected void okPressed() {
		if (user.getName().equals("")) { //$NON-NLS-1$
			MessageDialog.openError(getShell(), Messages.LoginDialog_29, Messages.LoginDialog_30);
			return;
		}
		if (user.getPassword().equals("")) { //$NON-NLS-1$
			MessageDialog.openError(getShell(), Messages.LoginDialog_32, Messages.LoginDialog_33);
			return;
		}

		if (!this.login())
			return;

		this.saveUserPref();
		super.okPressed();
	}

	public boolean login() {
		try {
			Session session = Session.getInstance();
			session.setUser(user);
			session.login();
			if (!session.isAuthenticated()) {
				MessageDialog.openError(getShell(), Messages.LoginDialog_34, Messages.LoginDialog_35);
				return false;
			}
		} catch (ConfigurationException e) {
			MessageDialog.openError(getShell(), Messages.LoginDialog_36, Messages.LoginDialog_37);
		}

		return true;
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		IProduct product = Platform.getProduct();
		if (product != null) {
			String[] imageURLs = StringUtils.split(product
					.getProperty(IProductConstants.WINDOW_IMAGES),
					SystemConstants.SEPARATOR);
			if (imageURLs.length > 0) {
				images = new Image[imageURLs.length];
				for (int i = 0; i < imageURLs.length; i++) {
					ImageDescriptor descriptor = AbstractUIPlugin
							.imageDescriptorFromPlugin(product
									.getDefiningBundle().getSymbolicName(),
									imageURLs[i]);
					images[i] = descriptor.createImage(true);
				}
			}

			newShell.setImages(images);
			newShell.setText(product.getProperty(IProductConstants.APP_NAME));
		}
	}

	public User getUser() {
		return user;
	}

	private void deleteUserPref() {
		try {
			if (!user.getName().equals("")) { //$NON-NLS-1$
				savedUsers.node(user.getName()).removeNode();
				if (user.getName().equals(autoLoginUserName)) {
					preferences.put(AUTO_LOGIN, ""); //$NON-NLS-1$
					autoLoginUserName = ""; //$NON-NLS-1$
				}
			}
			preferences.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}

	public void saveUserPref() {
		preferences.put(LAST_USER, user.getName());
		Preferences p = savedUsers.node(user.getName());
		p.put(PASSWORD, rememberPassword ? user.getPassword() : ""); //$NON-NLS-1$
		preferences.put(AUTO_LOGIN, autoLogin ? user.getName() : ""); //$NON-NLS-1$
		try {
			preferences.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}

	private void loadDescriptors() {
		try {
			String[] userNames = savedUsers.childrenNames();
			for (int i = 0; i < userNames.length; i++) {
				String userName = userNames[i];
				Preferences node = savedUsers.node(userName);
				User u = new User();
				u.setName(userName);
				u.setPassword(node.get(PASSWORD, "")); //$NON-NLS-1$
				users.put(userName, u);
			}
			user = users.get(preferences.get(LAST_USER, "")); //$NON-NLS-1$
			autoLoginUserName = preferences.get(AUTO_LOGIN, ""); //$NON-NLS-1$
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}

	public boolean isAutoLogin() {
		if (user != null && user.getName().equals(autoLoginUserName)) {
			return true;
		}

		return false;
	}
}
