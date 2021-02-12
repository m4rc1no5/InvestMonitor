package pl.marceen.investmonitor.email.boundary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.marceen.investmonitor.config.boundary.ConfigReader;
import pl.marceen.investmonitor.config.entity.EmailConfig;
import pl.marceen.investmonitor.email.entity.EmailData;
import pl.marceen.investmonitor.email.entity.EmailException;

import javax.json.bind.JsonbBuilder;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author Marcin Zaremba
 */
public class EmailSender {
    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);

    private final Session session;
    private EmailConfig emailConfig;

    public EmailSender() {
        emailConfig = JsonbBuilder.create().fromJson(getConfigAsJson(), EmailConfig.class);

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", emailConfig.getSmtpHost());
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.socketFactory.port", "587");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailConfig.getUsername(), emailConfig.getPassword());
            }
        };

        session = Session.getInstance(properties, auth);
    }

    public void send(EmailData data) {
        logger.info("Sending email with data {}", data);

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addFrom(InternetAddress.parse(emailConfig.getEmailsFrom()));
            mimeMessage.addRecipients(Message.RecipientType.TO, InternetAddress.parse(emailConfig.getEmailsTo()));
            mimeMessage.setSubject(data.getSubject());
            mimeMessage.setText(data.getText());

            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            throw EmailException.sendingProblem("Problem with sending email - details: " + e.getMessage(), logger);
        }

        logger.info("Email sent");
    }

    private String getConfigAsJson() {
        try {
            return ConfigReader.read(getClass(), "config/email.json");
        } catch (Exception e) {
            throw new RuntimeException("Problem with reading config file: " + e.getMessage());
        }
    }
}
