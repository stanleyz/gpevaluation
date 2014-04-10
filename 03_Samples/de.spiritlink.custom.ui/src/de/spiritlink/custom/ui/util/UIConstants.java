/*******************************************************************************
 * Copyright (c) 2007 Spirit Link GmbH
 * All rights reserved.
 * 
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package de.spiritlink.custom.ui.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.IFormColors;

/**
 * 
 * @author Tom Seidel tom.seidel@spiritlink.de
 */
public final class UIConstants {
    
 public static final Font SUBHEADER_FONT = new Font(null,"Arial", 10, SWT.BOLD); //$NON-NLS-1$
    
    public static final Color ORANGE_COLOR = new Color(null,245,143,0);
    public static final Color GREY_COLOR = new Color(null,236,233,226);
    public static final Color HEADING_COLOR = new Color(null,102,102,102);
    
    private static FormColors formColors;
   
    
    public static FormColors FORM_COLOR(final Display display) {
        if (formColors == null) {
            formColors = new FormColors(display);
            formColors.createColor( IFormColors.H_GRADIENT_START, ORANGE_COLOR.getRGB() );
            formColors.createColor( IFormColors.H_GRADIENT_END, GREY_COLOR.getRGB() );
            formColors.createColor( IFormColors.H_BOTTOM_KEYLINE1, GREY_COLOR.getRGB() );
            formColors.createColor( IFormColors.H_BOTTOM_KEYLINE2,ORANGE_COLOR.getRGB());
            formColors.createColor( IFormColors.TITLE, HEADING_COLOR.getRGB());
        }
        return formColors;
    }

}
