package es.developer.achambi.pkmng.modules.overview.model;

public class Configuration {
    private String item;
    private String ability;
    private String nature;
    private StatsSet statsSet;

    public Configuration() {
        statsSet = new StatsSet();
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public StatsSet getStatsSet() {
        return statsSet;
    }

    public void setStatsSet(StatsSet statsSet) {
        this.statsSet = statsSet;
    }
}
