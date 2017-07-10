package calc.lib;

/** Class (POJO) used to store Solar System JSON objects
 *
 * @author Carlin Robertson
 * @version 1.0
 *
 */
public class SolarSystem {

    private Information information;

    private String requirePermit;

    private Coords coords;

    private String name;

    private String permitName;

    public Information getInformation ()
    {
        return information;
    }

    public void setInformation (Information information)
    {
        this.information = information;
    }

    public String getRequirePermit ()
    {
        return requirePermit;
    }

    public void setRequirePermit (String requirePermit)
    {
        this.requirePermit = requirePermit;
    }
    public Coords getCoords ()
    {
        return coords;
    }

    public void setCoords (Coords coords)
    {
        this.coords = coords;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getPermitName ()
    {
        return permitName;
    }

    public void setPermitName (String permitName)
    {
        this.permitName = permitName;
    }

}
