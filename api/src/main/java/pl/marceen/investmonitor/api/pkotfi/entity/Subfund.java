package pl.marceen.investmonitor.api.pkotfi.entity;

import java.math.BigDecimal;

/**
 * @author Marcin Zaremba
 */
public enum Subfund {
    TREASURY_BOND_PLUS("1", "PKO Treasury Bond Plus - soif ", "PKO Obligacji Skarbowych Plus - sfio", 42, "0.5", "-0.6"),
    TREASURY_BOND("2", "PKO Treasury Bond", "PKO Obligacji Skarbowych", 42, "0.5", "-0.6"),
    LONG_TERM_BOND("4", "PKO Long-Term Bond Subfund", "PKO Obligacji Długoterminowych", 44, "0.5", "-0.9"),
    STABLE_GROWTH("5", "PKO Stable Growth Subfund", "PKO Stabilnego Wzrostu", 20, "0.8", "-0.6"),
    BALANCED("6", "PKO Balanced Subfund", "PKO Zrównoważony", 22, "1.1", "-1.2"),
    SMALL_AND_MID_CAP_EQUITY("8", "PKO Small and Mid-cap Equity Subfund", "PKO Akcji Małych i Średnich Spółek", 21, "0.5", "-1.0"),
    NEW_EUROPE_EQUITY("10", "PKO New Europe Equity Subfund", "PKO Akcji Nowa Europa", 44, "0.8", "-0.9"),
    GLOBAL_NATURAL_RESOURCES("34", "PKO Global Natural Resources Subfund", "PKO Surowców Globalny", 57, "1.1", "-1.2"),
    GLOBAL_TECHNOLOGY_AND_INNOVATION("35", "PKO Global Technology and Innovation Subfund", "PKO Technologii i Innowacji Globalny", 25, "0.5", "-1.1"),
    US_EQUITY("75", "PKO U.S. Equity Subfund", "PKO Akcji Rynku Amerykańskiego", 21, "0.7", "-1.0"),
    EMERGING_MARKETS_EQUITY("84", "PKO Emerging Markets Equity Subfund", "PKO Akcji Rynków Wschodzących", 40, "0.8", "-1.2"),
    JAPANESE_EQUITY("77", "PKO Japanese Equity Subfund", "PKO Akcji Rynku Japońskiego", 22, "0.9", "-1.2");

    private final String id;
    private final String subfundName;
    private final String subfundNameInPolish;
    private final int numberOfElements;
    private final BigDecimal entry;
    private final BigDecimal exit;

    Subfund(String id, String subfundName, String subfundNameInPolish, int numberOfElements, String entry, String exit) {
        this.id = id;
        this.subfundName = subfundName;
        this.subfundNameInPolish = subfundNameInPolish;
        this.numberOfElements = numberOfElements;
        this.entry = new BigDecimal(entry);
        this.exit = new BigDecimal(exit);
    }

    public String getId() {
        return id;
    }

    public String getSubfundName() {
        return subfundName;
    }

    public String getSubfundNameInPolish() {
        return subfundNameInPolish;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public BigDecimal getEntry() {
        return entry;
    }

    public BigDecimal getExit() {
        return exit;
    }
}
