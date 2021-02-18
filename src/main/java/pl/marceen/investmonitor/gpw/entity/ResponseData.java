package pl.marceen.investmonitor.gpw.entity;

import java.math.BigDecimal;

/**
 * @author Marcin Zaremba
 */
public class ResponseData {
    private BigDecimal c;
    private BigDecimal h;
    private BigDecimal l;
    private BigDecimal o;
    private Long t;
    private Integer v;

    public BigDecimal getC() {
        return c;
    }

    public ResponseData setC(BigDecimal c) {
        this.c = c;
        return this;
    }

    public BigDecimal getH() {
        return h;
    }

    public ResponseData setH(BigDecimal h) {
        this.h = h;
        return this;
    }

    public BigDecimal getL() {
        return l;
    }

    public ResponseData setL(BigDecimal l) {
        this.l = l;
        return this;
    }

    public BigDecimal getO() {
        return o;
    }

    public ResponseData setO(BigDecimal o) {
        this.o = o;
        return this;
    }

    public Long getT() {
        return t;
    }

    public ResponseData setT(Long t) {
        this.t = t;
        return this;
    }

    public Integer getV() {
        return v;
    }

    public ResponseData setV(Integer v) {
        this.v = v;
        return this;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "c=" + c +
                ", h=" + h +
                ", l=" + l +
                ", o=" + o +
                ", t=" + t +
                ", v=" + v +
                '}';
    }
}
