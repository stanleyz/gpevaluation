package de.spiritlink.editor.printing.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.EditorPart;

import de.spiritlink.editor.printing.action.PrintAction;
import de.spiritlink.editor.printing.model.ExampleModel;

public class ExampleEditor extends EditorPart {



    private static final int LABELWIDTH = 80;
    private ExampleModel model;
    private Text firstNameText;
    private Text lastNameText;
    private Text companyText;
    private Text streetText;
    private Text zipText;
    private Text countryText;
    private Text cityLLabelText;

    @Override
    public void doSave(IProgressMonitor monitor) {
        // do nothing

    }

    @Override
    public void doSaveAs() {
        // do nothing

    }


    @Override
    public void init(IEditorSite site, IEditorInput input)
    throws PartInitException {
        super.setInput(input);
        setSite(site);
        this.model = ((ExampleEditorInput) input).getModel();
        
        // Enabling the global print button
        PrintAction printAction = new PrintAction(this.model);
        site.getActionBars().setGlobalActionHandler(ActionFactory.PRINT.getId(),printAction);


    }

    @Override
    public boolean isDirty() {
        return false;
    }

    @Override
    public boolean isSaveAsAllowed() {
        return false;
    }

    @Override
    public void createPartControl(Composite parent) {
        FormToolkit toolkit = new FormToolkit(Display.getDefault());

        ScrolledForm form = toolkit.createScrolledForm(parent);

        GridLayout layout = new GridLayout(1, false);
        form.getBody().setLayout(layout);

        Section section = toolkit.createSection(form.getBody(), ExpandableComposite.TITLE_BAR);
        section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
        GridData gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        section.setLayoutData(gd);
        Composite client = toolkit.createComposite(section, SWT.WRAP);
        layout = new GridLayout(2, false);
        client.setLayout(layout);
        
        toolkit.paintBordersFor(client);
        section.setText("Object Data");
        section.setClient(client);
        section.setExpanded(true);
        
        
        // FirstName
        Label titleLabel = toolkit.createLabel(client, "First Name");
        gd = new GridData(SWT.BEGINNING,SWT.CENTER,false,false);
        gd.widthHint = LABELWIDTH;
        titleLabel.setLayoutData(gd);
        
        this.firstNameText = toolkit.createText(client, this.model.getFirstName());
        gd = new GridData(SWT.FILL,SWT.CENTER,true,false);
        this.firstNameText.setLayoutData(gd);
        
        // LastName
        Label lastNameLabel = toolkit.createLabel(client, "Last Name");
        gd = new GridData(SWT.BEGINNING,SWT.CENTER,false,false);
        gd.widthHint = LABELWIDTH;
        lastNameLabel.setLayoutData(gd);
        
        this.lastNameText = toolkit.createText(client, this.model.getLastName());
        gd = new GridData(SWT.FILL,SWT.CENTER,true,false);
        this.lastNameText.setLayoutData(gd);
        
        // Company
        Label companyLabel = toolkit.createLabel(client, "Company");
        gd = new GridData(SWT.BEGINNING,SWT.CENTER,false,false);
        gd.widthHint = LABELWIDTH;
        companyLabel.setLayoutData(gd);
        
        this.companyText = toolkit.createText(client, this.model.getCompany());
        gd = new GridData(SWT.FILL,SWT.CENTER,true,false);
        this.companyText.setLayoutData(gd);
        
        // Street
        Label streetLabel = toolkit.createLabel(client, "Street");
        gd = new GridData(SWT.BEGINNING,SWT.CENTER,false,false);
        gd.widthHint = LABELWIDTH;
        streetLabel.setLayoutData(gd);
        
        this.streetText = toolkit.createText(client, this.model.getStreet());
        gd = new GridData(SWT.FILL,SWT.CENTER,true,false);
        this.streetText.setLayoutData(gd);
        
        // Zip
        Label zipLabel = toolkit.createLabel(client, "Zip");
        gd = new GridData(SWT.BEGINNING,SWT.CENTER,false,false);
        gd.widthHint = LABELWIDTH;
        zipLabel.setLayoutData(gd);
        
        this.zipText = toolkit.createText(client, this.model.getZip());
        gd = new GridData(SWT.FILL,SWT.CENTER,true,false);
        this.zipText.setLayoutData(gd);
        
        // City
        Label cityLabel = toolkit.createLabel(client, "City");
        gd = new GridData(SWT.BEGINNING,SWT.CENTER,false,false);
        gd.widthHint = LABELWIDTH;
        cityLabel.setLayoutData(gd);
        
        this.cityLLabelText = toolkit.createText(client, this.model.getCity());
        gd = new GridData(SWT.FILL,SWT.CENTER,true,false);
        this.cityLLabelText.setLayoutData(gd);
        
        
        // Country
        Label countryLabel = toolkit.createLabel(client, "Country");
        gd = new GridData(SWT.BEGINNING,SWT.CENTER,false,false);
        gd.widthHint = LABELWIDTH;
        countryLabel.setLayoutData(gd);
        
        this.countryText = toolkit.createText(client, this.model.getCountry());
        gd = new GridData(SWT.FILL,SWT.CENTER,true,false);
        this.countryText.setLayoutData(gd);
        

        form.reflow(true);
    }

    @Override
    public void setFocus() {
        // do nothing.
    }

}
