/*******************************************************************************
 * Copyright (c) 2006 Spirit Link GmbH
 * All rights reserved.
 * 
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package de.spiritlink.editor.printing.editor;

import java.util.HashMap;
import java.util.Map;

import de.spiritlink.editor.printing.model.ExampleModel;

/**
 *
 * @author Tom Seidel tom.seidel@spiritlink.de
 */
public final class EditorContributor { 

    public static final String EXAMPLE_EDITOR_ID = "de.spiritlink.editor.printing.editor.ExampleEditor"; //$NON-NLS-1$
    
    
    private static Map<Class<?>, String> editorMapping;

    static {
        editorMapping = new HashMap<Class<?>, String>();
        editorMapping.put(ExampleModel.class, EXAMPLE_EDITOR_ID);

    }

    /**
     * Returns the correct editor id by the given 
     * class-signature
     * @param clazz the class of the object that will be
     * edited with an Editor
     * @return the editor-id definded in the <code>plugin.xml</code>
     */
    public static String getEditorIdByClass(Class clazz) {
        return editorMapping.get(clazz);
    }
}
