package edu.kit.informatik.pcc.webinterface.gui;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;
import de.steinwedel.messagebox.MessageBox;
import edu.kit.informatik.pcc.webinterface.datamanagement.NamedInputStream;
import edu.kit.informatik.pcc.webinterface.datamanagement.PersecutionWebinterface;
import edu.kit.informatik.pcc.webinterface.datamanagement.Video;


import java.util.LinkedList;
import java.util.ResourceBundle;

/**
 * This class creates a table of all videos and adds buttons for functionality.
 *
 * @author Josh Romanowski, Christoph Hörtnagl
 */
public class VideoTable extends Table {

    private static final String tableId = "VideoTable";
    private static ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");
    private LinkedList<Video> videos;
    private PersecutionWebinterface webInterface;


    public VideoTable(PersecutionWebinterface webinterface) {
        super();
        this.setSizeFull();
        this.webInterface = webinterface;
    }

    /**
     * Creates the table.
     */
    public void update() {
    	webInterface.setCurrentSession(getSession());
        videos = webInterface.getVideos();
        this.addContainerProperty(messages.getString(tableId + "Name"), String.class, null);
        this.addContainerProperty(messages.getString(tableId + "DownloadConfidentialVideo"), Button.class, null);
        this.addContainerProperty(messages.getString(tableId + "Info"), Button.class, null);
        this.removeAllItems();
        prepareEntries();
    }

    /**
     * This method sets the table entries up.
     */
    private void prepareEntries() {
        int i = 2;
        
        if (videos == null) {
        	return;
        }
        
        for (Video v : videos) {
          webInterface.setCurrentSession(getSession());
          Button confidentialVideoDownload = new Button(FontAwesome.DOWNLOAD);
          
          NamedInputStream inputStream = webInterface.downloadVideo(v.getId(), v.getName());
          
          FileDownloader confidentialVideoDownloader = new FileDownloader(inputStream.toStreamResource());
          confidentialVideoDownloader.extend(confidentialVideoDownload);

            Button info = new Button(FontAwesome.INFO);
            info.addClickListener(
                    (ClickListener) event -> MessageBox.createInfo()
                            .withMessage(v.getInfo())
                            .open()
            );

            //this.addItem(new Object[]{v.getName(), download, info, delete}, i);
            this.addItem(new Object[]{v.getName(), confidentialVideoDownload, info}, i);
            i++;
        }
    }

}
