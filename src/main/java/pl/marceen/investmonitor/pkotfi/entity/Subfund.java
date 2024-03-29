package pl.marceen.investmonitor.pkotfi.entity;

import pl.marceen.investmonitor.investment.entity.InstrumentInterface;

import java.math.BigDecimal;

/**
 * @author Marcin Zaremba
 */
public enum Subfund implements InstrumentInterface {
    TREASURY_BOND_PLUS("1", "PKO Treasury Bond Plus - soif (PKO Obligacji Skarbowych Plus - sfio)", 42, "0.5", "-0.6", false),
    TREASURY_BOND("2", "PKO Treasury Bond (PKO Obligacji Skarbowych)", 42, "0.5", "-0.6", false),
    LONG_TERM_BOND("4", "PKO Long-Term Bond Subfund (PKO Obligacji Długoterminowych)", 23, "1.2", "-0.6", true),
    STABLE_GROWTH("5", "PKO Stable Growth Subfund (PKO Stabilnego Wzrostu)", 20, "1.4", "-1.4", true),
    BALANCED("6", "PKO Balanced Subfund (PKO Zrównoważony)", 22, "1.1", "-1.2", false),
    SMALL_AND_MID_CAP_EQUITY("8", "PKO Small and Mid-cap Equity Subfund (PKO Akcji Małych i Średnich Spółek)", 21, "0.5", "-1.0", false),
    NEW_EUROPE_EQUITY("10", "PKO New Europe Equity Subfund (PKO Akcji Nowa Europa)", 44, "0.8", "-0.9", false),
    BOND_PLUS("30", "PKO Bond Plus Subfund (PKO Papierów Dłużnych Plus)", 42, "0.9", "-0.8", false),
    EQUITY_PLUS("33", "PKO Equity Plus Subfund (PKO Akcji Plus)", 20, "0.9", "-0.9", false),
    GLOBAL_NATURAL_RESOURCES("34", "PKO Global Natural Resources Subfund (PKO Surowców Globalny)", 21, "1.4", "-1.0", true),
    GLOBAL_TECHNOLOGY_AND_INNOVATION("35", "PKO Global Technology and Innovation Subfund (PKO Technologii i Innowacji Globalny)", 55, "0.6", "-1.6", true),
    GLOBAL_LUXURY_GOODS("36", "PKO Global Luxury Goods Subfund (PKO Dóbr Luksusowych Globalny)", 58, "0.8", "-1.5", true),
    GLOBAL_INFRASTRUCTURE_AND_CONSTRUCTION("37", "PKO Global Infrastructure and Construction Subfund (PKO Infrastruktury i Budownictwa Globalny)", 49, "1.0", "-1.6", true),
    GLOBAL_EQUITY_DIVIDEND("73", "PKO Global Equity Dividend Subfund (PKO Akcji Dywidendowych Globalny)", 39, "0.5", "-1.2", false),
    EUROPEAN_EQUITY("74", "PKO European Equity Subfund (PKO Akcji Rynku Europejskiego)", 47, "1.0", "-1.6", true),
    US_EQUITY("75", "PKO U.S. Equity Subfund (PKO Akcji Rynku Amerykańskiego)", 56, "0.6", "-1.2", true),
    JAPANESE_EQUITY("77", "PKO Japanese Equity Subfund (PKO Akcji Rynku Japońskiego)", 22, "0.6", "-1.6", true),
    GOLD_EQUITY("79", "PKO Gold Equity Subfund (PKO Akcji Rynku Złota)", 20, "1.0", "-0.6", true),
    USD_BOND("80", "PKO USD Bond Subfund (PKO Papierów Dłużnych USD)", 52, "0.6", "-1.1", false),
    EMERGING_MARKETS_EQUITY("84", "PKO Emerging Markets Equity Subfund (PKO Akcji Rynków Wschodzących)", 44, "1.4", "-0.6", true),
    GLOBAL_MEDICINE_AND_DEMOGRAPHY("91", "PKO Global Medicine and Demography Subfund (PKO Medycyny i Demografii Globalny)", 60, "1.4", "-1.6", true);

    private final String id;
    private final String instrumentName;
    private final int numberOfElements;
    private final BigDecimal entry;
    private final BigDecimal exit;
    private final boolean active;

    Subfund(String id, String instrumentName, int numberOfElements, String entry, String exit, boolean active) {
        this.id = id;
        this.instrumentName = instrumentName;
        this.numberOfElements = numberOfElements;
        this.entry = new BigDecimal(entry);
        this.exit = new BigDecimal(exit);
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public String getInstrumentName() {
        return instrumentName;
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

    public boolean isActive() {
        return active;
    }
}
