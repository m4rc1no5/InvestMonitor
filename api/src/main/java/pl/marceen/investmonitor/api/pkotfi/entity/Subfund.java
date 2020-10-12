package pl.marceen.investmonitor.api.pkotfi.entity;

/**
 * @author Marcin Zaremba
 */
public enum Subfund {
    GLOBAL_TECHNOLOGY_AND_INNOVATION("35", "PKO Global Technology and Innovation Subfund");

    private final String id;
    private final String name;

    Subfund(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
