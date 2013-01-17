/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.physion.ovation.editor;

import imagej.ImageJ;
import imagej.display.DisplayService;
import java.awt.Component;
import java.io.File;
import javax.swing.JPanel;
import net.imglib2.img.ImgPlus;
import net.imglib2.io.ImgIOException;
import net.imglib2.io.ImgOpener;
import org.openide.util.lookup.ServiceProvider;
import ovation.OvationException;
import ovation.Response;
import ovation.URLResponse;
import imagej.data.Dataset;
import imagej.data.DefaultDataset;
import imagej.data.display.DefaultImageDisplay;
import imagej.data.display.ImageDisplay;
import imagej.ui.swing.sdi.viewer.SwingDisplayWindow;
import imagej.ui.swing.sdi.viewer.SwingSdiImageDisplayViewer;
import imagej.ui.swing.viewer.image.SwingImageDisplayViewer;


/**
 *
 * @author huecotanks
 */
public class ImageJVisualization implements Visualization{

    private final Response response;
    ImageJVisualization(Response r)
    {
        this.response = r;
    }
    
    @Override
    public Component generatePanel() {
        assert (this.response instanceof URLResponse);

        try {
            final ImageJ context = new ImageJ(); //TODO can this be an instance variable?

            // Open the Dataset
            final ImgPlus ip = ImgOpener.open(((URLResponse) this.response).getURLString());
            final Dataset dataset = new DefaultDataset(context, ip);
            
            // Display the dataset
            final ImageDisplay display = new DefaultImageDisplay();
            display.setContext(context);
            display.display(dataset);
            
            final SwingImageDisplayViewer displayViewer = 
                    new SwingSdiImageDisplayViewer();
            final SwingDisplayWindow displayWindow = new SwingDisplayWindow();
            displayViewer.view(displayWindow, display);
            
            return displayViewer.getPanel();
            
        } catch (ImgIOException ex) {
            throw new OvationException("Unable to open image " + ((URLResponse) this.response).getURLString(), ex);
        }
    }

    @Override
    public boolean shouldAdd(Response r) {
        return false;
    }

    @Override
    public void add(Response r) {
        throw new UnsupportedOperationException("Not supported for this image visualization.");
    }
    
}
