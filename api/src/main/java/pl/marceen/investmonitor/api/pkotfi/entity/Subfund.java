package pl.marceen.investmonitor.api.pkotfi.entity;

import java.math.BigDecimal;

/**
 * @author Marcin Zaremba
 */
public enum Subfund {
    GLOBAL_TECHNOLOGY_AND_INNOVATION("35", "PKO Global Technology and Innovation Subfund", new BigDecimal("0.6"), new BigDecimal("-0.6")),
    STABLE_GROWTH("5", "PKO Stable Growth Subfund", new BigDecimal("0.6"), new BigDecimal("-0.6")),
    GLOBAL_NATURAL_RESOURCES("34", "PKO Global Natural Resources Subfund", new BigDecimal("0.6"), new BigDecimal("-0.6")),
    LONG_TERM_BOND("4", "PKO Long-Term Bond Subfund", new BigDecimal("0.5"), new BigDecimal("-1")),
    US_EQUITY("75", "PKO U.S. Equity Subfund", new BigDecimal("0.5"), new BigDecimal("-0.6")),
    JAPANESE_EQUITY("77", "PKO Japanese Equity Subfund", new BigDecimal("0.6"), new BigDecimal("-0.6"));

    private final String id;
    private final String name;
    private final BigDecimal entry;
    private final BigDecimal exit;

    Subfund(String id, String name, BigDecimal entry, BigDecimal exit) {
        this.id = id;
        this.name = name;
        this.entry = entry;
        this.exit = exit;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getEntry() {
        return entry;
    }

    public BigDecimal getExit() {
        return exit;
    }
}
