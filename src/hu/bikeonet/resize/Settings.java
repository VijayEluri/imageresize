package hu.bikeonet.resize;

import java.util.logging.Logger;
import java.util.prefs.Preferences;

public class Settings {

    public static final String PREF_SIZE = "size";
    public static final String PREF_EMAIL_TO = "email_to";
    public static final String PREF_EMAIL_FROM = "email_from";
    public static final String PREF_SMTP = "smtp";
    public static final String PREF_SMTP_USER = "smtp_user";
    public static final String PREF_SMTP_PASS = "smtp_pass";
    public static final String PREF_RESIZE = "resize";
    public static final String PREF_SEND = "send";
    public static final String SUBJECT = "subject";

    private static final Logger log = Logger.getLogger(Settings.class.getName());
    
    private Preferences prefs;
    private boolean sending = false;
    private boolean resizeing = true;
    private String subject;
    
    public Settings() {
		prefs = Preferences.userNodeForPackage(this.getClass());
		setSending(prefs.getBoolean(PREF_SEND, false));
		setResizeing(prefs.getBoolean(PREF_RESIZE, true));
    }
    
    public void set(String setting) {
    	if ( setting.startsWith("-")) {
    		String[] params = setting.substring(1).toLowerCase().split("=");
    		if ( params.length>1 ) {
    			String key = params[0];
    			String value = params[1];
    			set(key,value);
    		}    		
    	}
    	else {
    		if ( setting.toLowerCase().contains("s")) {
    			setSending(true);
    		}
    		if ( setting.toLowerCase().contains("r")) {
    			setResizeing(true);
    		}    	
    	}
    }
    
    private void set(String key, String value) {
    	log.info("Setting preference "+key+" "+value);
    	if ( key.equals(PREF_EMAIL_FROM) ||
    		 key.equals(PREF_EMAIL_TO) ||
    		 key.equals(PREF_SMTP) || 
    		 key.equals(PREF_SMTP_PASS) ||
    		 key.equals(PREF_SMTP_USER)
    		 ) {
    		prefs.put(key, value);
    	}
    	
    	if ( key.equals(PREF_RESIZE)) {
    		prefs.putBoolean(PREF_RESIZE, value.toLowerCase().equals("true"));
    	}
    	if ( key.equals(PREF_SEND)) {
    		prefs.putBoolean(PREF_SEND, value.toLowerCase().equals("true"));
    	}
    	if ( key.equals(PREF_SIZE)) {
    		Integer size = new Integer(value);
    		prefs.putInt(PREF_SIZE, size);
    	}
    	if ( key.equals(SUBJECT)) {
    		setSubject(value);
    	}
    }
    
    public Object get(String key) {
    	if ( key.equals(PREF_EMAIL_FROM) ||
       		 key.equals(PREF_EMAIL_TO) ||
       		 key.equals(PREF_SMTP) || 
       		 key.equals(PREF_SMTP_PASS) ||
       		 key.equals(PREF_SMTP_USER)
       		 ) {
       		return prefs.get(key, "");
       	}
       	
       	if ( key.equals(PREF_RESIZE)) {
       		return new Boolean(prefs.getBoolean(PREF_RESIZE, resizeing));
       	}
       	if ( key.equals(PREF_SEND)) {
       		return new Boolean(prefs.getBoolean(PREF_SEND, sending));
       	}
       	if ( key.equals(PREF_SIZE)) {
       		return new Integer(prefs.getInt(PREF_SIZE, 1024));
       	}
       	
       	return null;
    }

	/**
	 * @param sending the sending to set
	 */
	public void setSending(boolean sending) {
		this.sending = sending;
	}

	/**
	 * @return the sending
	 */
	public boolean isSending() {
		return sending;
	}

	/**
	 * @param resizeing the resizing to set
	 */
	public void setResizeing(boolean resizeing) {
		this.resizeing = resizeing;
	}

	/**
	 * @return the resizing
	 */
	public boolean isResizeing() {
		return resizeing;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	
}
