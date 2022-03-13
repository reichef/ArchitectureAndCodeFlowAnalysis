package edu.kit.kastel.sdq.pcmjoanaflowanalysis;


import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
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
import org.eclipse.xtend.lib.macro.file.Path;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PCMJOANAFlowAnalysisStartDialog extends ApplicationWindow {

	private boolean selection = false;
	private String joanaOutputPath = "";
	private String joanaServerIP = "";
	private boolean joanaIntraComponentEndPoint_selected = false;
	private Shell s;
	private int windowHeight = 200;
	private int windowWidth = 450;
	private Text joanaOutputPath_text = null;
	
    public PCMJOANAFlowAnalysisStartDialog(Shell parentShell) {
        super(parentShell);
        s = parentShell;
    }
    
    public PCMJOANAFlowAnalysisStartDialog(Shell parentShell, String joanaOutputPath, String joanaServerIP, 
    		boolean intra_componend_end_point) {
        super(parentShell);
        s = parentShell;
        this.joanaOutputPath = joanaOutputPath;
        this.joanaServerIP = joanaServerIP;
        this.joanaIntraComponentEndPoint_selected = intra_componend_end_point;
        this.setReturnCode(CANCEL);
    }
    
    public String getJoanaOutputPath() {
    	return joanaOutputPath;
    }
    
    public void setJoanaOutputPath(String joanaOutputPath) {
    	this.joanaOutputPath = joanaOutputPath;
    }
    
    public String getJoanaServerIP() {
    	return joanaServerIP;
    }
    
    public boolean getjoanaIntraComponentEndPoint_selected() {
    	return joanaIntraComponentEndPoint_selected;
    }
    
    @Override
    protected Control createContents(Composite parent) {
        Composite container = new Composite(parent, SWT.NULL);
        var parent_size = parent.getSize();
        container.computeSize(windowWidth, windowHeight);
        container.setLayout(new GridLayout());
        
        //Create options
        //JOANA Output Path
        var container_joanaOutputPath = new Composite(container,SWT.NULL);
        var container_layout = new GridLayout();
        container_layout.numColumns = 3;
        container_joanaOutputPath.setLayout(container_layout);
        var joanaOutputPath_label = new Label(container_joanaOutputPath, SWT.RIGHT);
        joanaOutputPath_label.setText("Output folder path: ");
        joanaOutputPath_text = new Text(container_joanaOutputPath, SWT.LEFT | SWT.BORDER );
        joanaOutputPath_text.setText(joanaOutputPath);
        Button joanaOutputPath_button = new Button(container_joanaOutputPath, SWT.CENTER);
        joanaOutputPath_button.setText("Choose...");
        joanaOutputPath_button.addSelectionListener(new SelectionAdapter() {
          @Override
          public void widgetSelected(SelectionEvent e) {
              var fileDialog = new DirectoryDialog(s, SWT.OPEN);
              fileDialog.setText("Select Output folder path ...");
              fileDialog.setFilterPath(joanaOutputPath);
              String selected = fileDialog.open();
              joanaOutputPath_text.setText(selected);
              System.out.println(selected);
          }
        });
        
        var container_joanaServerIP = new Composite(container,SWT.PUSH);
        container_joanaServerIP.setLayout(new RowLayout());
        var joanaServerIP_label = new Label(container_joanaServerIP, SWT.RIGHT);
        joanaServerIP_label.setText("JOANA Server (IP address): ");
        var joanaServerIP_text = new Text(container_joanaServerIP, SWT.LEFT | SWT.BORDER);
        joanaServerIP_text.setText(joanaServerIP);
        Button joanaConnectionTest_button = new Button(container_joanaServerIP, SWT.CENTER);
        joanaConnectionTest_button.setText("Test");
        joanaConnectionTest_button.addSelectionListener(new SelectionAdapter() {
          @Override
          public void widgetSelected(SelectionEvent e) {
              System.out.println("Test connection");
        	  checkConnection(joanaServerIP_text.getText(),8004,s,false);
          }
        });
        
        var container_joanaIntraComponentEndPoint = new Composite(container,SWT.PUSH);
        container_joanaIntraComponentEndPoint.setLayout(new RowLayout());
        var joanaIntraComponentEndPoint_label = new Label(container_joanaIntraComponentEndPoint, SWT.RIGHT);
        joanaIntraComponentEndPoint_label.setText("Ignore Intra-Component End-Points");
        var joanaIntraComponentEndPoint_checkbox = new Button(container_joanaIntraComponentEndPoint, 
        		SWT.LEFT | SWT.BORDER | SWT.CHECK);
        joanaIntraComponentEndPoint_checkbox.setSelection(joanaIntraComponentEndPoint_selected);
        
        //Create buttons
        var container_buttons = new Composite(container,SWT.PUSH);
        container_buttons.setLayout(new RowLayout());
        Button exit_button = new Button(container_buttons, SWT.LEFT);
        exit_button.setText("Exit");
        exit_button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	setReturnCode(CANCEL);
            	close();
            	selection = false;
                System.out.println("Exit Pressed");
            }
        });
        
        Button start_button = new Button(container_buttons, SWT.RIGHT);
        start_button.setText("Run Analysis");
        start_button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	
            	java.nio.file.Path outputPath = Paths.get(joanaOutputPath_text.getText());
            	boolean input_ok = false;
            	if (Files.exists(outputPath)) {
            		input_ok = true;
	            	if (checkConnection(joanaServerIP_text.getText(),8004,s,true))
	            		input_ok = true;
	            	else
	            		input_ok = false;
            	}
            	else {
            		input_ok = false;
            		MessageDialog.openError(s, "Input Error", "The path " + joanaOutputPath_text.getText() + " does not exist");
            	}
            		
            	if (input_ok) {
                	setReturnCode(OK);
                	joanaOutputPath = joanaOutputPath_text.getText();
                	joanaServerIP = joanaServerIP_text.getText();
                	joanaIntraComponentEndPoint_selected = joanaIntraComponentEndPoint_checkbox.getSelection();
                	close();
                	selection = true;
                    System.out.println("Run Analysis Pressed");
            	}
            }
        });

        return container;
    }

    
    private static boolean checkConnection(String server, int port, Shell parent, boolean run_time) {
        System.out.println("Testing connection " + port);
        Socket connection = null;
        try {
        	connection = new Socket(server, port);
        	if (!run_time)
        		MessageDialog.openInformation(parent, "Connection", "Connection to Server '" + server + "' successful");
            System.out.println("Connection successful");
            if( connection != null){
                try {
                	connection.close();
                } catch (IOException e) {
                    throw new RuntimeException("Error closing connection");
                }
            }
            return true;
        } catch (IOException e) {
        	MessageDialog.openError(parent, "Connection Error", "Server '" + server + "' does not respond");
            System.out.println("Connection refused");
            return false;
        }
    }
    
    // overriding this methods allows you to set the
    // title of the custom dialog
    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        //newShell.setSize(this.windowWidth, this.windowHeight);
        //newShell.computeSize(windowWidth, windowHeight);
        newShell.setText("PCMJOANA Component Analysis");
        
    }
    

    /*@Override
    protected Point getInitialSize() {
        return new Point(windowWidth, windowHeight);
    }*/

    @Override
    public boolean close() {
    	// TODO Auto-generated method stub
    	return super.close();
    }
    
    public void showFinishedDialog() {
    	MessageDialog.openInformation(s, "JOANA Analyisis", "JOANA Analysis successfully finished");
    }
    
    public void showFinishedErrorDialog() {
    	MessageDialog.openError(s, "JOANA Analysis", "JOANA Analyisis did not finished correctly. Please refer to output console.");
    }
}
