package de.spiritlink.custom.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import de.spiritlink.custom.ui.model.FolderItem;
import de.spiritlink.custom.ui.model.Item;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in VIEW_ID
	public static final String PLUGIN_ID = "de.spiritlink.custom.ui";

	// The shared instance
	private static Activator plugin;
    
    private FolderItem model = null;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
    public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
    public void stop(final BundleContext context) throws Exception {
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
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(final String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

    /**
     * @return the model
     */
    public FolderItem getModel() {
        if (this.model == null) {
            initModel();
        }
        return this.model;
    }

    /**
     * 
     */
    private void initModel() {
       this.model = new FolderItem("Root",null);
        
        final FolderItem leaf1 = new FolderItem("Leaf1", this.model);
        final FolderItem leaf2 = new FolderItem("Leaf2", this.model);
        final FolderItem leaf3 = new FolderItem("Leaf3", this.model);
        final FolderItem leaf4 = new FolderItem("Leaf4", this.model);
        
       
        final FolderItem leaf11 = new FolderItem("Leaf11", leaf1);
        final FolderItem leaf12 = new FolderItem("Leaf12", leaf1);
        final FolderItem leaf21 = new FolderItem("Leaf21", leaf2);
        final FolderItem leaf22 = new FolderItem("Leaf22", leaf2);
        final FolderItem leaf23 = new FolderItem("Leaf23", leaf2);        
        final FolderItem leaf31 = new FolderItem("Leaf31", leaf3);
        final FolderItem leaf41 = new FolderItem("Leaf41", leaf4);
        final FolderItem leaf42 = new FolderItem("Leaf42", leaf4);
        final FolderItem leaf43 = new FolderItem("Leaf43", leaf4);
        final FolderItem leaf44 = new FolderItem("Leaf44", leaf4);
        
        new Item("item111","This is a test111", "Some description",leaf11);
        new Item("item112","This is a test112", "Some description",leaf11);
        new Item("item113","This is a test113", "Some description",leaf11);
        new Item("item114","This is a test114", "Some description",leaf11);
        new Item("item115","This is a test115", "Some description",leaf11);
        new Item("item121","This is a test121", "Some description",leaf12);
        new Item("item122","This is a test122", "Some description",leaf12);
        new Item("item123","This is a test123", "Some description",leaf12);
        new Item("item124","This is a test124", "Some description",leaf12);
        
        new Item("item11","This is a test11", "Some description",leaf1);
        new Item("item12","Another test", "Some description",leaf1);
        new Item("item13","This is a test12", "Some description",leaf1);
        new Item("item14","This is a test12", "Some description",leaf1);
        new Item("item15","This is a test12", "Some description",leaf1);
        
        
        
        
        
        
    }
}
