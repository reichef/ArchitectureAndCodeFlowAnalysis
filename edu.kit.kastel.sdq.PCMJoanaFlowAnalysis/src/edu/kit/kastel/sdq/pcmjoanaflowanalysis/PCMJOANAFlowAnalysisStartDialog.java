package edu.kit.kastel.sdq.pcmjoanaflowanalysis;


import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class PCMJOANAFlowAnalysisStartDialog extends ApplicationWindow {

	private boolean selection = false;
	private Shell s;
	
    public PCMJOANAFlowAnalysisStartDialog(Shell parentShell) {
        super(parentShell);
        s = parentShell;
    }

    @Override
    protected Control createContents(Composite parent) {
        Composite container = new Composite(parent, SWT.NULL);
        container.setLayout(new GridLayout());
        
        //Create options
        //JOANA Output Path
        var container_joanaOutputPath = new Composite(container,SWT.PUSH);
        container_joanaOutputPath.setLayout(new RowLayout());
        var joanaOutputPath_label = new Label(container_joanaOutputPath, SWT.RIGHT);
        joanaOutputPath_label.setText("Output folder path: ");
        var joanaOutputPath_text = new Text(container_joanaOutputPath, SWT.LEFT | SWT.BORDER);
        Button joanaOutputPath_button = new Button(container_joanaOutputPath, SWT.CENTER);
        joanaOutputPath_button.setText("...");
        joanaOutputPath_button.addSelectionListener(new SelectionAdapter() {
          @Override
          public void widgetSelected(SelectionEvent e) {
              var fileDialog = new DirectoryDialog(s, SWT.OPEN);
              fileDialog.setText("Select Output folder path ...");
              fileDialog.setFilterPath("/");
              //String[] filterExt = { "*.*" };
              //fileDialog.setFilterExtensions(filterExt);
              String selected = fileDialog.open();
              System.out.println(selected);
          }
        });
        
        var container_joanaServerIP = new Composite(container,SWT.PUSH);
        container_joanaServerIP.setLayout(new RowLayout());
        var joanaServerIP_label = new Label(container_joanaServerIP, SWT.RIGHT);
        joanaServerIP_label.setText("JOANA Server (IP address): ");
        var joanaServerIP_text = new Text(container_joanaServerIP, SWT.LEFT | SWT.BORDER);
        
        var container_joanaJarsPath = new Composite(container,SWT.PUSH);
        container_joanaJarsPath.setLayout(new GridLayout());
        var joanaJarsPath_label = new Label(container_joanaJarsPath, SWT.RIGHT);
        joanaJarsPath_label.setText("JOANA Analysis classes (jar files): ");
        var joanaJarsPath_text = new Text(container_joanaJarsPath, SWT.LEFT | SWT.BORDER );
        Button joanaJarsPath_button = new Button(container_joanaJarsPath, SWT.CENTER);
        joanaJarsPath_button.setText("...");
        joanaJarsPath_button.addSelectionListener(new SelectionAdapter() {
          @Override
          public void widgetSelected(SelectionEvent e) {
              var fileDialog = new FileDialog(s, SWT.MULTI);
              fileDialog.setText("Select Model folder path ...");
              fileDialog.setFilterPath("/");
              String[] filterExt = { "*.jar" };
              fileDialog.setFilterExtensions(filterExt);
              String selected = fileDialog.open();
              System.out.println(selected);
          }
        });
        
        var container_joanaModelPath = new Composite(container,SWT.PUSH);
        container_joanaModelPath.setLayout(new RowLayout());
        var joanaModelPath_label = new Label(container_joanaModelPath, SWT.RIGHT);
        joanaModelPath_label.setText("JOANA Model path (.repository/.system/.usagemodel): ");
        var joanaModelPath_text = new Text(container_joanaModelPath, SWT.LEFT | SWT.BORDER);
        Button joanaModelPath_button = new Button(container_joanaModelPath, SWT.CENTER);
        joanaModelPath_button.setText("...");
        joanaModelPath_button.addSelectionListener(new SelectionAdapter() {
          @Override
          public void widgetSelected(SelectionEvent e) {
              var fileDialog = new DirectoryDialog(s, SWT.OPEN);
              fileDialog.setText("Select Model folder path ...");
              fileDialog.setFilterPath("/");
              //String[] filterExt = { "*.*" };
              //fileDialog.setFilterExtensions(filterExt);
              String selected = fileDialog.open();
              System.out.println(selected);
          }
        });
        
        //Create buttons
        var container_buttons = new Composite(container,SWT.PUSH);
        container_buttons.setLayout(new RowLayout());
        Button exit_button = new Button(container_buttons, SWT.LEFT);
//        exit_button.setLayoutData(new GridData(SWT.END, SWT.DOWN, false,
//                false));
        exit_button.setText("Exit");
        exit_button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	selection = false;
                System.out.println("Exit Pressed");
            }
        });
        
        Button start_button = new Button(container_buttons, SWT.RIGHT);
//        start_button.setLayoutData(new GridData(SWT.END, SWT.DOWN, false,
//                false));
        start_button.setText("Run Analysis");
        start_button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	selection = true;
                System.out.println("Run Analysis Pressed");
            }
        });

        return container;
    }

    // overriding this methods allows you to set the
    // title of the custom dialog
    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("PCMJOANA Component Analysis");
    }
    

    @Override
    protected Point getInitialSize() {
        return new Point(450, 300);
    }

}
