package hu.bikeonet.resize;

import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mailer {

    private String smtp;
    private String smtp_user;
    private String smtp_pass;
    private static final Logger log = Logger.getLogger(Mailer.class.getName());
    
	public Mailer(String smtp, String smtp_user, String smtp_pass) {
		this.smtp = smtp;
		this.setSmtp_user(smtp_user);
		this.setSmtp_pass(smtp_pass);
	}

	public void mailImage(String from_email, String to_email, String subject, String resizedFileName) {

        Properties props = System.getProperties();
        props.put("mail.smtp.host", smtp);

        Session session = Session.getInstance(props, null);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from_email));
            InternetAddress[] address = {new InternetAddress(to_email)};
            msg.setRecipients(RecipientType.TO, address);
            msg.setSubject(subject);

            MimeBodyPart mbp1 = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(resizedFileName);
            mbp1.setDataHandler(new DataHandler(fds));
            mbp1.setFileName(subject);

            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);

            msg.setContent(mp);
            msg.setSentDate(new Date());

            Transport.send(msg);

        }
        catch (MessagingException e) {
            log.warning(e.getMessage());
        }

    }

	/**
	 * @param smtp_user the smtp_user to set
	 */
	public void setSmtp_user(String smtp_user) {
		this.smtp_user = smtp_user;
	}

	/**
	 * @return the smtp_user
	 */
	public String getSmtp_user() {
		return smtp_user;
	}

	/**
	 * @param smtp_pass the smtp_pass to set
	 */
	public void setSmtp_pass(String smtp_pass) {
		this.smtp_pass = smtp_pass;
	}

	/**
	 * @return the smtp_pass
	 */
	public String getSmtp_pass() {
		return smtp_pass;
	}
    
	
}
