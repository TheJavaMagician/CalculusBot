package calc.lib;

/** Class (POJO) used to store Faction JSON objects
 *
 * @author Carlin Robertson
 * @version 1.0
 *
 */
public class Faction {
    private String id;

    private String state_id;

    private String allegiance_id;

    private String government_id;

    private String updated_at;

    private String name;

    private String state;

    private String allegiance;

    private String government;

    private String home_system_id;

    private String is_player_faction;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getState_id ()
    {
        return state_id;
    }

    public void setState_id (String state_id)
    {
        this.state_id = state_id;
    }

    public String getAllegiance_id ()
    {
        return allegiance_id;
    }

    public void setAllegiance_id (String allegiance_id)
    {
        this.allegiance_id = allegiance_id;
    }

    public String getGovernment_id ()
    {
        return government_id;
    }

    public void setGovernment_id (String government_id)
    {
        this.government_id = government_id;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getState ()
    {
        return state;
    }

    public void setState (String state)
    {
        this.state = state;
    }

    public String getAllegiance ()
    {
        return allegiance;
    }

    public void setAllegiance (String allegiance)
    {
        this.allegiance = allegiance;
    }

    public String getGovernment ()
    {
        return government;
    }

    public void setGovernment (String government)
    {
        this.government = government;
    }

    public String getHome_system_id ()
    {
        return home_system_id;
    }

    public void setHome_system_id (String home_system_id)
    {
        this.home_system_id = home_system_id;
    }

    public String getIs_player_faction ()
    {
        return is_player_faction;
    }

    public void setIs_player_faction (String is_player_faction)
    {
        this.is_player_faction = is_player_faction;
    }

}
