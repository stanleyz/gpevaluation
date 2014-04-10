package org.xopen.gpevaluation.rcp;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.xopen.gpevaluation.rcp.model.ISimpleNode;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.xopen.gpevaluation.rcp";

	// The shared instance
	private static Activator plugin;
	public static final String INDUSTRY_ICON = "industry_icon";
	public static final String SCHEME_ICON = "scheme_icon";
	public static final String EVALUATION_ICON = "evaluation_icon";
	public static final String RIGHT_ICON = "right_icon";
	public static final String WRONG_ICON = "wrong_icon";
	public static final String HORIZONTAL_ICON = "horizontal_icon";
	public static final String VERTICAL_ICON = "vertical_icon";
	private static final Map<ISimpleNode, IEditorPart> openedEditors = new HashMap<ISimpleNode, IEditorPart>();

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	protected void initializeImageRegistry(ImageRegistry registry) {
		registerImage(registry, INDUSTRY_ICON, "industry.gif");
		registerImage(registry, SCHEME_ICON, "scheme.gif");
		registerImage(registry, EVALUATION_ICON, "eval.gif");
		registerImage(registry, RIGHT_ICON, "right.gif");
		registerImage(registry, WRONG_ICON, "wrong.gif");
		registerImage(registry, HORIZONTAL_ICON, "hor.gif");
		registerImage(registry, VERTICAL_ICON, "ver.gif");
	}

	private void registerImage(ImageRegistry registry, String key,
			String fileName) {
		try {
			IPath path = new Path(SystemConstants.ICONS_DIR + fileName); //$NON-NLS-1$
			URL url = FileLocator.find(this.getBundle(), path, null);
			if (url != null) {
				ImageDescriptor desc = ImageDescriptor.createFromURL(url);
				registry.put(key, desc);
			}
		} catch (Exception e) {
		}
	}

	public static Map<ISimpleNode, IEditorPart> getOpenededitors() {
		return openedEditors;
	}
}
