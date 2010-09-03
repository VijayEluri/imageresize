package hu.bikeonet.resize;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class Resizer {

    public Resizer(String filename, Integer integer) {
		// TODO Auto-generated constructor stub
    	size = integer;
    	this.filename = filename;
	}

	private int size;
	private String filename;
    private static final Logger log = Logger.getLogger(Resizer.class.getName());
	
	public String resize() {
        File file = new File(filename);
        BufferedImage img = null;

        /*
         * try to load the image into java 2d image format
         */
        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            log.warning(e.getMessage());
        }

        /*
         * on success resize it
         */
        if (img != null) {
            int width = size;
            int height = 0;
            int calcHeight = 0;
            BufferedImage resizedImg = null;
            String resizedFileName = null;

            //portrait image
            if (img.getHeight() > img.getWidth()) {
                height = size;
                width = 0;
                int calcWidth = width > 0 ? width : (height * img.getWidth() / img.getHeight());
                resizedImg = createResizedCopy(img, calcWidth, height);
            } //landscape image
            else {
                calcHeight = height > 0 ? height : (width * img.getHeight() / img.getWidth());
                resizedImg = createResizedCopy(img, width, calcHeight);
            }

            /*
             * write the resized image to a file for later reference
             */
            if (resizedImg != null) {
                try {
                	String path = file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf(file.getName()));
                    resizedFileName = path + "res_"+file.getName();
                    ImageIO.write(resizedImg, "jpg", new File(resizedFileName));
                    return resizedFileName;
                } catch (IOException e) {
                    log.warning(e.getMessage());
                }
            }
        }
        
        return null;
        
	}

    	/**
         * creates a resized image of the given width and height
         * @param originalImage
         * @param scaledWidth
         * @param scaledHeight
         * @return
         */
        private static BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight) {
            BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = scaledBI.createGraphics();
            g.setComposite(AlphaComposite.Src);
            g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
            g.dispose();
            return scaledBI;
        }
        
        
}
