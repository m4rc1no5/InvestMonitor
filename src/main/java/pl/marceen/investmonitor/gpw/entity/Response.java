package pl.marceen.investmonitor.gpw.entity;

import java.util.List;

/**
 * @author Marcin Zaremba
 */
public class Response {
    private String isin;
    private String mode;
    private String from;
    private String to;
    private List<ResponseData> data;

    public String getIsin() {
        return isin;
    }

    public Response setIsin(String isin) {
        this.isin = isin;
        return this;
    }

    public String getMode() {
        return mode;
    }

    public Response setMode(String mode) {
        this.mode = mode;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public Response setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getTo() {
        return to;
    }

    public Response setTo(String to) {
        this.to = to;
        return this;
    }

    public List<ResponseData> getData() {
        return data;
    }

    public Response setData(List<ResponseData> data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "Response{" +
                "isin='" + isin + '\'' +
                ", mode='" + mode + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", data=" + data +
                '}';
    }
}
