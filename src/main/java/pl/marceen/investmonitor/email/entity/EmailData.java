package pl.marceen.investmonitor.email.entity;

/**
 * @author Marcin Zaremba
 */
public class EmailData {
    private String subject;
    private String text;

    public String getSubject() {
        return subject;
    }

    public EmailData subject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getText() {
        return text;
    }

    public EmailData text(String text) {
        this.text = text;
        return this;
    }

    @Override
    public String toString() {
        return "EmailData{" +
                "subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
