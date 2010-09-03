/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.bikeonet.resize;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *
 * @author spike
 */
public class Main {

    private static final Logger log = Logger.getLogger(Main.class.getName());
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {    	
    	Settings settings = new Settings();    	
    	ArrayList<String> filenames = new ArrayList<String>();
    	
        for (int i = 0; i < args.length; i++) {
        	String arg = args[i];
        	
        	if ( arg.startsWith("-")) {
        		settings.set(arg.substring(1));
        	}
        	if ( arg.endsWith("jpg") || arg.endsWith("JPG")) {
        		filenames.add(arg);
        	}
        }
        
        for ( String filename : filenames) {        
            log.info(filename);
            String resizedFilename = filename;
            if (settings.isResizeing()) {
            	Resizer resizer = new Resizer(filename, (Integer) settings.get(Settings.PREF_SIZE));
            	resizedFilename = resizer.resize();
            }
            if (settings.isSending()) {
            	Mailer mailer = new Mailer((String) settings.get(Settings.PREF_SMTP),
            							   (String) settings.get(Settings.PREF_SMTP_USER),
            							   (String) settings.get(Settings.PREF_SMTP_PASS));
            	mailer.mailImage((String) settings.get(Settings.PREF_EMAIL_FROM), 
            					 (String) settings.get(Settings.PREF_EMAIL_TO), 
            					 settings.getSubject(), 
            					 resizedFilename);
            }
        }
    }

}
