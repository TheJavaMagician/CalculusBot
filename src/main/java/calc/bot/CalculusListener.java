package calc.bot;

import calc.function.Command;
import com.google.common.util.concurrent.FutureCallback;
import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import de.btobastian.javacord.entities.Channel;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.message.MessageCreateListener;
import de.btobastian.javacord.listener.server.ServerMemberAddListener;
import de.btobastian.javacord.listener.user.UserChangeNameListener;

import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import calc.function.Administration;


import javax.imageio.ImageIO;


/** Main discord listener
 *
 * @author Carlin Robertson
 * @version 1.0
 *
 */

public class CalculusListener {

    /**
     * Connection to the postgresql database
     */
        private Connection connection = getDatabaseConnection();

    /**
     * Administrator list
     */
        private static ArrayList<String> adminList = new ArrayList<String>();

    /**
     * List of muted users
     */
        private static ArrayList<String> muteList = new ArrayList<String>();


        public CalculusListener(String token) {


            // Define JavaCord API
            DiscordAPI api = Javacord.getApi(token, true);

            // create administrator functions object
            Administration adminFunctions = new Administration(connection, api);

            // create standard commands object
            Command commandFunctions = new Command(api);

            api.connect(new FutureCallback<DiscordAPI>() {
                @Override
                public void onSuccess(DiscordAPI api) {
                    // register listener

                    BufferedImage img = null;

                    try {
                        img = ImageIO.read(new File("copied_avatar.jpg"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    api.updateAvatar(img);


                    adminList.add("296755073529413632");
                    adminList.add("213371237299257344"); // Scarlet
                    adminList.add("168408467667812353"); // yoda
                    adminList.add("103730295533993984"); // alex



                    for (Channel nextChannel : api.getChannels()) {
                        if(nextChannel.getName().contains("lobby")) {
                            nextChannel.sendMessage("Hello, I am Calculus the discord bot. My **version** is 1.4 and I am connected to " + nextChannel.getName() + "." +
                                    " Please type 'help' to see more.");
                        }
                    }

                    api.registerListener(new MessageCreateListener() {
                        @Override
                        public void onMessageCreate(DiscordAPI api, Message message) {
                            // check the content of the message

                            recordWatchedUser(message);
                            for (String mutedTag : muteList) {
                                if (mutedTag.equalsIgnoreCase(message.getAuthor().getMentionTag())) {
                                    message.delete();
                                }
                            }

                            /* Bulk commands, these will be responded to if typed in the discord chat **/

                            if (message.getContent().contains("!mute") && adminList.contains(message.getAuthor().getId())) {
                                adminFunctions.mute(message);
                            } else if (message.getContent().equalsIgnoreCase("!autoban -list") && adminList.contains(message.getAuthor().getId())) {
                                adminFunctions.autoBanList(message);
                            } else if (message.getContent().contains("!autoban -remove") && adminList.contains(message.getAuthor().getId())) {
                                adminFunctions.autoBanRemove(message);
                            } else if (message.getContent().contains("!autoban -add") && adminList.contains(message.getAuthor().getId())) {
                                adminFunctions.autoBanAdd(message);
                            } else if (message.getContent().contains("!unmute") && adminList.contains(message.getAuthor().getId())) {
                                adminFunctions.unMute(message);
                            } else if (message.getContent().contains("!watch") && adminList.contains(message.getAuthor().getId())) {
                                adminFunctions.watch(message);
                            } else if (message.getContent().equalsIgnoreCase("List Muted")) {
                                message.reply("Current Mute List: " + muteList.toString());
                            } else if (message.getContent().contains("!fahr")) {
                                commandFunctions.getFahr(message);
                            } else if (message.getContent().contains("!kelvin")) {
                                commandFunctions.getKelvin(message);
                            } else if (message.getContent().contains("!keltocel")) {
                                commandFunctions.getKelvinToCelsius(message);
                            } else if (message.getContent().contains("!keltofah")) {
                                commandFunctions.getKelvinToFahr(message);
                            } else if (message.getContent().contains("!celsius")) {
                                commandFunctions.getFahrToCelsius(message);
                            } else if (message.getContent().equalsIgnoreCase("help")) {
                                commandFunctions.getHelp(message);
                            } else if (message.getContent().contains("!admin") && adminList.contains(message.getAuthor().getId())) {
                                adminFunctions.adminHelp(message);
                            } else if (message.getContent().contains("!admin") && !adminList.contains(message.getAuthor().getId())) {
                                message.reply("Hello, "+message.getAuthor().getName()+". Whilst we appreciate your curiosity, administration is done by the moderators of the server accessed by typing 'help' anywhere. Thank you.");
                            } else if (message.getContent().contains("!lookup")) {
                                adminFunctions.lookup(message);
                            } else if (message.getContent().contains("!system")) {
                                 commandFunctions.getSystem(message);
                            } else if (message.getContent().contains("!faction")) {
                                 commandFunctions.searchFaction(message);
                            } else if (message.getContent().contains("!unwatch") && adminList.contains(message.getAuthor().getId())) {
                                 adminFunctions.unWatch(message);
                            } else if (message.getContent().equalsIgnoreCase("Who's being watched") && adminList.contains(message.getAuthor().getId())) {
                                 adminFunctions.listWatched(message);
                            }  else if (message.getContent().contains("!ship")) {
                                 commandFunctions.getShipList(message);
                            } else if (message.getContent().equalsIgnoreCase("hello calculus") || message.getContent().equalsIgnoreCase("hi calculus")) {
                                 commandFunctions.helloResponse(message, message.getChannelReceiver().getServer());
                            }
                        }




                    });

                    /* Functionality for a when a new user joins the discord **/

                    api.registerListener(new ServerMemberAddListener() {
                        @Override
                        public void onServerMemberAdd(DiscordAPI discordAPI, User user, Server server) {

                            adminFunctions.checkBanList(server, user);

                            for (Channel nextChannel : server.getChannels()) {
                                if(nextChannel.getName().contains("lobby")) {
                                    nextChannel.sendMessage("Hello " + user.getMentionTag() + ". Welcome to the **Four Stars Research Syndicate**." +
                                            "\n\n Your moderators are **Scarlet Winters**, **Master Yoda**, **Kaz Archer** and **Professor Java**." +
                                            "\n\n This bot responds to public commands:" +
                                            "\n\n Temperature Commands: !fahr, !celsius, !kelvin, !keltofah, !keltocel"+
                                            "\n\n type 'help' anywhere to get help from this bot"+
                                            "\n\n Game Commands: !system System Name, !faction Faction Name, !ship -ship name"+
                                            "\n\n Silly Commands: !eagle, !imperial courier");
                                }
                            }
                        }
                    });

                    /* Listener for a when a user changes name */

                    api.registerListener(new UserChangeNameListener() {

                        @Override
                        public void onUserChangeName(DiscordAPI discordAPI, User user, String s) {
                            for(String mutedName: muteList) {
                                if(mutedName.equalsIgnoreCase(s)) {
                                    muteList.add(user.getMentionTag());
                                }
                            }
                        }
                    });
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                }
            });
        }


    /**
     * @return
     */
    public static ArrayList<String> getAdminList() {
        return adminList;
    }

    /**
     * @return
     */
    public static ArrayList<String> getMuteList() {
        return adminList;
    }


    /**
     * @param discord_handle
     */
    public static void addMuted(String discord_handle) {
        muteList.add(discord_handle);
    }


    /**
     * @param discord_handle
     */
    public static void removeMuted(String discord_handle) {
        muteList.remove(discord_handle);
    }

    /**
     * @param discord_handle
     */
    public static void removeMuted(int discord_handle) {
        muteList.remove(discord_handle);
    }


    /**
     * @param message
     */
    private void recordWatchedUser(Message message) {
        String findUserByBan = "SELECT discord_handle FROM public.watch WHERE discord_handle = ?;";


        // Use Java Prepared Statements to protect against SQL Injection
        PreparedStatement queryProtectedStatement = null;

        try {
            queryProtectedStatement = connection.prepareStatement(findUserByBan);
            queryProtectedStatement.setString(1, message.getAuthor().getMentionTag());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet result = null;
        try {
            result = queryProtectedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            while (result.next()) {
                // insert used by discord id
                String insertWatchUser = "INSERT INTO public.watchlist (message_content, discord_handle)" +
                        "VALUES (?,?);";

                // Use Java Prepared Statements to protect against SQL Injection
                queryProtectedStatement = connection.prepareStatement(insertWatchUser);
                queryProtectedStatement.setString(1, message.getContent());
                queryProtectedStatement.setString(2, message.getAuthor().getName());

                // insert watched user
                queryProtectedStatement.execute();
                System.out.println("Recorded message by: "+message.getAuthor().getName());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /** Database connection, set to localhost by default
     * @return
     */
    private Connection getDatabaseConnection() {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/database", "user",
                        "password");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Cant connect to database");
            }

            return connection;
    }

}
