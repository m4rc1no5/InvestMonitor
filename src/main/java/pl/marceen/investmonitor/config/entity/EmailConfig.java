package pl.marceen.investmonitor.config.entity;

/**
 * @author Marcin Zaremba
 */
public class EmailConfig {
    private String smtpHost;
    private String username;
    private String password;
    private String emailsFrom;
    private String emailsTo;

    public String getSmtpHost() {
        return smtpHost;
    }

    public EmailConfig setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public EmailConfig setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public EmailConfig setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmailsFrom() {
        return emailsFrom;
    }

    public EmailConfig setEmailsFrom(String emailsFrom) {
        this.emailsFrom = emailsFrom;
        return this;
    }

    public String getEmailsTo() {
        return emailsTo;
    }

    public EmailConfig setEmailsTo(String emailsTo) {
        this.emailsTo = emailsTo;
        return this;
    }

    @Override
    public String toString() {
        return "EmailConfig{" +
                "smtpHost='" + smtpHost + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", emailsFrom='" + emailsFrom + '\'' +
                ", emailsTo='" + emailsTo + '\'' +
                '}';
    }
}
