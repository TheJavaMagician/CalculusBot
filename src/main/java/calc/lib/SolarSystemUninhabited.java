package calc.lib;

/** Class (POJO) used to store Uninhabited Solar System JSON objects
 *
 * @author Carlin Robertson
 * @version 1.0
 *
 */
public class SolarSystemUninhabited {
    private String[] information;

    private String requirePermit;

    private Coords coords;

    private String name;

    public String[] getInformation ()
    {
        return information;
    }

    public void setInformation (String[] information)
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
}
