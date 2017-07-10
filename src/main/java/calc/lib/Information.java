package calc.lib;

/** Class (POJO) used to store nested information from solar system JSON objects
 *
 * @author Carlin Robertson
 * @version 1.0
 *
 */
public class Information {
    private String economy;

    private String reserve;

    private String faction;

    private String allegiance;

    private String factionState;

    private String security;

    private String government;

    private String eddbId;

    private String population;

    public String getEconomy ()
    {
        return economy;
    }

    public void setEconomy (String economy)
    {
        this.economy = economy;
    }

    public String getReserve ()
    {
        return reserve;
    }

    public void setReserve (String reserve)
    {
        this.reserve = reserve;
    }

    public String getFaction ()
    {
        return faction;
    }

    public void setFaction (String faction)
    {
        this.faction = faction;
    }

    public String getAllegiance ()
    {
        return allegiance;
    }

    public void setAllegiance (String allegiance)
    {
        this.allegiance = allegiance;
    }

    public String getFactionState ()
    {
        return factionState;
    }

    public void setFactionState (String factionState)
    {
        this.factionState = factionState;
    }

    public String getSecurity ()
    {
        return security;
    }

    public void setSecurity (String security)
    {
        this.security = security;
    }

    public String getGovernment ()
    {
        return government;
    }

    public void setGovernment (String government)
    {
        this.government = government;
    }

    public String getEddbId ()
    {
        return eddbId;
    }

    public void setEddbId (String eddbId)
    {
        this.eddbId = eddbId;
    }

    public String getPopulation ()
    {
        return population;
    }

    public void setPopulation (String population)
    {
        this.population = population;
    }

}
