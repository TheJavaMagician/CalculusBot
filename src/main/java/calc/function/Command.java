package calc.function;

import calc.lib.Faction;
import calc.lib.SolarSystem;
import calc.lib.SolarSystemUninhabited;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.entities.permissions.Role;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

/** Class of standard command functions called by the discord bot listeners
 *
 * @author Carlin Robertson
 * @version 1.0
 *
 */

public class Command {

    DiscordAPI api;

    public Command(DiscordAPI api) {
        this.api = api;
    }

    /** Convert from Celsius to Fahrenheit
     * @param message
     */
    public void getFahr(Message message) {
        // reply to the message
        String[] arguments = message.getContent().split(" ");

        if (arguments.length != 2) {
        } else {
            try {
                Double celsius = Double.parseDouble(arguments[1]);
                Double fahrenheit = 9 * (celsius / 5) + 32;
                message.reply(message.getAuthor().getMentionTag() + ": " + arguments[1] + " **Celsius** converts to: " + fahrenheit + " **Fahrenheit**");

            } catch (Exception e) {
                message.reply("You didn't enter a number after **!fah**");
            }


        }
    }

    /** Convert from Celsius to Kelvin
     * @param message
     */
    public void getKelvin(Message message) {
        // reply to the message
        String[] arguments = message.getContent().split(" ");

        if (arguments.length != 2) {
        } else {
            try {
                Double celsius = Double.parseDouble(arguments[1]);
                Double kel = celsius + 273.15;
                message.reply(message.getAuthor().getMentionTag() + ": " + arguments[1] + " **Celsius** converts to: " + kel + " **Kelvin**");

            } catch (Exception e) {
                message.reply("You didn't enter a number after **!kel**");
            }


        }
    }

    /** Convert Kelvin to Celsius
     * @param message
     */
    public void getKelvinToCelsius(Message message) {
        // reply to the message
        String[] arguments = message.getContent().split(" ");

        if (arguments.length != 2) {
        } else {
            try {
                Double kelvin = Double.parseDouble(arguments[1]);
                Double cel = kelvin - 273.15;
                message.reply(message.getAuthor().getMentionTag() + ": " + arguments[1] + " **Kelvin** converts to: " + cel + " **Celsius**");

            } catch (Exception e) {
                message.reply("You didn't enter a number after **!keltocel**");
            }


        }
    }

    /** Method to convert Kelvin to Fahrenheit
     * @param message
     */
    public void getKelvinToFahr(Message message) {
        // reply to the message
        String[] arguments = message.getContent().split(" ");

        if (arguments.length != 2) {
        } else {
            try {
                Double kelvin = Double.parseDouble(arguments[1]);
                Double fah = 1.8 * (kelvin - 273) + 32;
                message.reply(message.getAuthor().getMentionTag() + ": " + arguments[1] + " **Kelvin** converts to: " + fah + " **Fahrenheit**");

            } catch (Exception e) {
                message.reply("You didn't enter a number after **!keltofah**");
            }


        }
    }

    /** Method to convert fahrenheit to celsius
     * @param message
     */
    public void getFahrToCelsius(Message message) {
        // reply to the message
        String[] arguments = message.getContent().split(" ");

        if (arguments.length != 2) {
        } else {
            try {
                Double fahrenheit = Double.parseDouble(arguments[1]);
                Double celsius = (fahrenheit - 32) * 5 / 9;
                message.reply(message.getAuthor().getMentionTag() + ": " + arguments[1] + " **Fahrenheit** converts to: " + celsius + " **Celsius**");

            } catch (Exception e) {
                message.reply("You didn't enter a number after **!cel**");
            }
        }
    }

    public void getHelp(Message message) {
        message.reply("Hello " + message.getAuthor().getMentionTag() + ". Welcome to the **Four Stars Research Syndicate**." +
                "\n\n Your moderators are **Scarlet Winters**, **Master Yoda**, **Kaz Archer** and **Professor Java**." +
                "\n\n This bot responds to public commands:" +
                "\n\n Temperature Commands: !fahr, !celsius, !kelvin, !keltofah, !keltocel"+
                "\n\n type 'help' anywhere to get help from this bot"+
                "\n\n Game Commands: !system System Name, !faction Faction Name, !ship -ship name"+
                "\n\n Silly Commands: !eagle, !imperial courier");
    }

    public void getSystem(Message message) {
        try {
            String[] arguments = message.getContent().split("!system ");
            if (arguments.length != 2) {
            } else {
                ObjectMapper mapper = new ObjectMapper();
                String currentLine;
                URL systemAPI = new URL("https://www.edsm.net/api-v1/system?systemName=" + arguments[1] + "&showInformation=1&showPermit=1&showCoordinates=1");
                InputStreamReader apiInputStream = new InputStreamReader(systemAPI.openStream());
                BufferedReader readAPI = new BufferedReader(apiInputStream);
                while((currentLine = readAPI.readLine()) != null) {
                    if(currentLine.contains("population")) {
                        SolarSystem starSys = mapper.readValue(currentLine, SolarSystem.class);
                        message.reply("**System Name**: " + starSys.getName() +
                                "\n**EDDB Link**: https://eddb.io/system/" + starSys.getInformation().getEddbId() +
                                "\n**System Permit**: " + starSys.getRequirePermit() +
                                "\n**Controlling Faction**: " + starSys.getInformation().getFaction() +
                                "\n**System Allegiance**: " + starSys.getInformation().getAllegiance() +
                                "\n**Government Type**: " + starSys.getInformation().getGovernment() +
                                "\n**System Population**: " + starSys.getInformation().getPopulation() +
                                "\n**System Security Level**: " + starSys.getInformation().getSecurity() +
                                "\n**Controlling Faction State**: " + starSys.getInformation().getFactionState() +
                                "\n**Economy Type**: " + starSys.getInformation().getEconomy());
                    } else {
                        SolarSystemUninhabited starSys = mapper.readValue(currentLine, SolarSystemUninhabited.class);
                        message.reply("*This system is uninhabited, limited information is available...*\n\n"+
                                "**System Name**: " + starSys.getName()+
                                "\n**System Permit**: " + starSys.getRequirePermit()+
                                "\n**Co-ordinates (x)**: " + starSys.getCoords().getX()+
                                "\n**Co-ordinates (y)**: " + starSys.getCoords().getY()+
                                "\n**Co-ordinates (z)**: " + starSys.getCoords().getZ());
                    }
                }


            }
        } catch (java.io.IOException e) {
            message.reply("Something went wrong... you didn't type in a correct system name did you?");
            e.printStackTrace();
        }
    }

    public void searchFaction(Message message) {
        String[] arguments = message.getContent().split("!faction ");
        ObjectMapper mapper = new ObjectMapper();
        String currentLine;
        if (arguments.length != 2 || arguments[1].length() < 3) {
            message.reply("You either entered arguments incorrectly, or a search of less than five characters");
        } else {
            FileReader in = null;
            try {
                in = new FileReader("/home/ubuntu/factions.jsonl");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            BufferedReader br = new BufferedReader(in);
            try {
                while ((currentLine = br.readLine()) != null) {
                    Faction currentFaction = mapper.readValue(currentLine, Faction.class);
                    if (currentFaction.getName().toUpperCase().contains(arguments[1].toUpperCase())) {
                        message.reply("**Faction Name**: " + currentFaction.getName()+
                                "\n**Is Player Faction**: " + currentFaction.getIs_player_faction()+
                                "\n**Faction Government**: " + currentFaction.getGovernment()+
                                "\n**Faction EDDB Page**: " + "https://eddb.io/faction/"+currentFaction.getId()+
                                "\n**Faction Home System**: " + "https://eddb.io/system/"+currentFaction.getHome_system_id());
                    }

                }
            } catch (IOException e) {
                message.reply("Something went wrong... you didn't type in a correct faction name?");
            }
        }

    }

    public void helloResponse(Message message, Server server) {

        String playedGame;

        if(message.getAuthor().getGame() == null) {
            playedGame = "nothing...";
        } else {
            playedGame = message.getAuthor().getGame();
        }

        ArrayList<String> roleNames = new ArrayList<String>();

        for(Role role : message.getAuthor().getRoles(message.getChannelReceiver().getServer())) {
            roleNames.add(role.getName());
        }

        message.reply("Hello " + message.getAuthor().getMentionTag() + ". Did you know that I'm an *automated Java Bot*?." +
                "\n\n Some fun facts for you:" +
                "\n\n There are " + server.getMemberCount() + " users that have membership to this discord"+
                "\n\n This discord server is located in: "+ server.getRegion() +
                "\n\n You are the following in this discord: "+ roleNames.toString() +
                "\n\n You are also apparently *playing* "+ playedGame);
    }

    public void getShipList(Message message) {
        String[] reason = message.getContent().split("-");
        if (reason.length < 2) {
            message.reply("Less than two arguments, please use !ship -shipname ");
        } else {
            String shipType = reason[1].toLowerCase();
            switch (shipType) {

                case "adder":  message.reply(" The Adder is a cheap multirole that offers the user a lot, but lacks in internals due to its small size. ");
                    break;
                case "anaconda":  message.reply(" The Anaconda is the top of the line multirole. Jack of all trades, master of none. ");
                    break;
                case "asp x": message.reply(" The Asp Explorer is an explorers best friend. A great ship for exploring on the cheap. ");
                    break;
                case "asp s": message.reply(" The Asp Scout is possibly the worst ship in the game, don't buy one, ever. ");
                    break;
                case "beluga": message.reply(" The Beluga is the pinnacle of luxury and the best ship for passengers. ");
                    break;
                case "cobra 3": message.reply(" The Cobra Mk. III is a great multirole ship for new and experienced players alike. ");
                    break;
                case "cobra 4": message.reply(" The Cobra Mk. IV is widely regarded as a bad ship, it is only available to those who pre-ordered Horizons.  ");
                    break;
                case "dbx": message.reply(" The Diamondback Explorer is an amazing exploration ship, second best jump range in the game.");
                    break;
                case "dbs": message.reply(" The Diamondback scout is a decent ship for combat for the price, it runs very cool. ");
                    break;
                case "eagle": message.reply(" The Eagle is a good step up from the Sidewinder, it is very fast and agile, though lacks in weaponry and shielding. ");
                    break;
                case "fdl": message.reply(" The Fer-De-Lance is known for its speed and great combat abilities. ");
                    break;
                case "fas": message.reply(" The Federal Assault Ship is a very good combat ship, though often overlooked as the Fer-De-Lance is often better. ");
                    break;
                case "dolphin": message.reply(" The Dolphin is the smallest dedicated passenger ship, it is able to equip luxury passenger bays. ");
                    break;
                case "corvette": message.reply(" The Federal Corvette is the pinnacle for combat, this ship is an end goal for many players. ");
                    break;
                case "fds": message.reply(" The Federal Dropship is a good balance of cargo capacity and firepower. ");
                    break;
                case "fgs": message.reply(" The Federal Gunship boasts a large array of guns, but has weak shields and is slower than the Federal Assault Ship. ");
                    break;
                case "hauler": message.reply(" The Hauler has a great jump range for its price, though is not recommended for exploration due to its limited internals. ");
                    break;
                case "clipper": message.reply(" The Imperial Clipper is an exceptionally fast multirole. However, it has terrible weapon convergence. ");
                    break;
                case "courier": message.reply(" The Imperial Courier is loved by many for its great speed and agility, it also has very strong shields and a respectable jump range. ");
                    break;
                case "cutter": message.reply(" The Imperial Cutter is a great trade ship, it has plentiful internals, and a great array of weapons for defense. ");
                    break;
                case "imperial eagle": message.reply(" The Imperial Eagle is an upgraded version of the standard Eagle, it is much faster and has better modules. ");
                    break;
                case "keelback": message.reply(" The Keelback is a good cargo ship that is also capable of using a ship launched fighter. ");
                    break;
                case "orca": message.reply(" The Orca is the mid sized passenger craft, it also known for being a battering ram. ");
                    break;
                case "python": message.reply(" The Python is a great multirole ship, it is known for being great at just about anything, except exploration. ");
                    break;
                case "sidewinder": message.reply(" The Sidewinder is the ship every CMDR starts in, its only good factor is its low price. ");
                    break;
                case "type 6": message.reply(" The Type-6 Transporter is a good cargo vessel for its price, but dont expect to fend off pirates. ");
                    break;
                case "type 7": message.reply(" The Type-7 Transporter is a mid sized cargo vessel, though it is lacking in firepower. ");
                    break;
                case "type 9": message.reply(" The Type-9 Heavy is a massive ship with a massive cargo capacity but is very slow. ");
                    break;
                case "viper": message.reply(" The Viper is a very capable combat ship, it is fast, and well armed for its size. ");
                    break;
                case "viper 4": message.reply(" The Viper Mk IV is an upgraded version of the standard Viper, it is slower, but has better internals. ");
                    break;
                case "vulture": message.reply(" The Vulture is a very good combat ship. It is known by its great firepower, agility, and is plagued by its power issues and canopy breaches. ");
                    break;
                case "condor": message.reply(" The F63 Condor is the Federation ship launched fighter, good balance of speed and strength. ");
                    break;
                case "taipan": message.reply(" The Taipan is the Alliance ship launched fighter, it is slightly slower than other fighters but stronger. ");
                    break;
                case "imperial fighter": message.reply(" The GU-97 Imperial fighter is the most maneuverable of the 3 fighters. ");
                    break;
            }
        }
    }
}
