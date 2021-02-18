package pl.marceen.investmonitor.gpw.entity;

/**
 * @author Marcin Zaremba
 */
public class Request {
    private String isin;
    private String mode;
    private String from;
    private String to;

    public String getIsin() {
        return isin;
    }

    public Request setIsin(String isin) {
        this.isin = isin;
        return this;
    }

    public String getMode() {
        return mode;
    }

    public Request setMode(String mode) {
        this.mode = mode;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public Request setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getTo() {
        return to;
    }

    public Request setTo(String to) {
        this.to = to;
        return this;
    }

    @Override
    public String toString() {
        return "Request{" +
                "isin='" + isin + '\'' +
                ", mode='" + mode + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}
