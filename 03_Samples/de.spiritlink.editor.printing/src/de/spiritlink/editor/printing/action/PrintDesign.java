/*******************************************************************************
 * Copyright (c) 2006 Spirit Link GmbH
 * All rights reserved.
 * 
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package de.spiritlink.editor.printing.action;

import java.util.Calendar;

import net.sf.paperclips.DefaultGridLook;
import net.sf.paperclips.GridLook;
import net.sf.paperclips.GridPrint;
import net.sf.paperclips.LinePrint;
import net.sf.paperclips.PageDecoration;
import net.sf.paperclips.PageNumber;
import net.sf.paperclips.PageNumberPrint;
import net.sf.paperclips.PagePrint;
import net.sf.paperclips.Print;
import net.sf.paperclips.SimplePageDecoration;
import net.sf.paperclips.TextPrint;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;

import de.spiritlink.editor.printing.model.ExampleModel;
import de.spiritlink.editor.printing.model.TreeObject;

/**
 * 
 * @author Tom Seidel - tom.seidel@spiritlink.de
 */
public class PrintDesign {



    public static PagePrint createPrint(TreeObject[] elements, ExampleModel model) {
        DefaultGridLook look = new DefaultGridLook();
        look.setCellSpacing(5, 2);
        GridPrint grid = new GridPrint("p:g, d:g", look);
        FontData bold = new FontData("Arial",50, SWT.BOLD);
        FontData normal = new FontData("Arial",50, SWT.NORMAL);
        for (int i = 0, n = elements.length; i <n; i++) {
          grid.add(new TextPrint(elements[i].getDataObject(),bold));
          grid.add(new TextPrint(getValueById(elements[i].getDataObject(), model),normal));
        }
        PagePrint page = new PagePrint(grid);
        page.setHeader(new SimplePageDecoration(new TextPrint(model.getFirstName(), SWT.CENTER)));
        PageDecoration footer = new PageDecoration() {
            private final Print copyrightStatement;
            private final GridLook footerLook;
            {
              int year = Calendar.getInstance().get(Calendar.YEAR);
              String copyrightText = "Copyright (c) " + year + " ABC Corp.  All Rights Reserved.";

              this.copyrightStatement = new TextPrint(copyrightText);

              this.footerLook = new DefaultGridLook(5, 2);
            }

            public Print createPrint(PageNumber pageNumber) {
              GridPrint grid = new GridPrint("d:g, d", this.footerLook);
              grid.add(new LinePrint(SWT.HORIZONTAL), GridPrint.REMAINDER);
              grid.add(this.copyrightStatement);
              grid.add(new PageNumberPrint(pageNumber, SWT.RIGHT));
              return grid;
            }
          };
        page.setFooter(footer);
        page.setHeaderGap(5);
        page.setFooterGap(2);

        return page;
    }
    
    /**
     * very very ugly implementation. for this purpse enough
     * @param id
     * @param model
     * @return
     */
    private static String getValueById(String id, ExampleModel model) {
        String returnValue = null;
        if (id.equals("First Name")) {
            returnValue = model.getFirstName();
        }
        if (id.equals("Last Name")) {
            returnValue = model.getLastName();
        }
        if (id.equals("Company")) {
            returnValue = model.getCompany();
        }
        if (id.equals("Street")) {
            returnValue = model.getStreet();
        }
        if (id.equals("Zip")) {
            returnValue = model.getZip();
        }
        if (id.equals("City")) {
            returnValue = model.getCity();
        }
        if (id.equals("Country")) {
            returnValue = model.getCountry();
        }
        return returnValue;
    }

}
