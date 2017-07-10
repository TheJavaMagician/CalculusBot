package calc.function;

import calc.bot.CalculusListener;
import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.Server;
import de.btobastian.javacord.entities.User;
import de.btobastian.javacord.entities.message.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/** Class of administration functions called by the discord bot listeners
 *
 * @author Carlin Robertson
 * @version 1.0
 *
 */


public class Administration {

    private Connection connection;
    private DiscordAPI api;

    /** Constructor that requires the database connection and discord api connection
     * @param connection
     */
    public Administration(Connection connection, DiscordAPI api) {
        this.connection = connection;
        this.api = api;
    }

    /* Mute User Functionality */

    /** Method to allow administrators to add users to the 'mute list'
     *
     * @param message
     * @param muteList
     */
    public void muteUser(Message message,  ArrayList<String> muteList) {
            String[] arguments = message.getContent().split(" ");
            if (arguments.length != 2) {
            } else {
                try {
                    if (!arguments[1].contains("@")) {
                        message.reply("You have not specified a person to mute");
                    } else if (muteList.contains(arguments[1])) {
                        message.reply(arguments[1] + "has already been muted");
                    } else {
                        muteList.add(arguments[1]);
                        message.reply(arguments[1] + " has been muted");
                    }
                } catch (Exception e) {
                    message.reply("Something went wrong there");
                }
            }
    }

    /** Method to return the users from the auto ban list
     *
     * @param message
     */
    public void autoBanList(Message message) {

        String errorMessage = "";

        try {
            // Find Users by Email
            String findUserByBan = "SELECT discord_id, reason FROM public.bans;";

            // Use Java Prepared Statements to protect against SQL Injection
            PreparedStatement queryProtectedStatement = connection.prepareStatement(findUserByBan);
            //                queryProtectedStatement.setString(1, loginForm.get().email);

                                  /* Locate email address in database */
            ResultSet result = queryProtectedStatement.executeQuery();

            while (result.next()) {
                message.reply("**Discord ID**: " + result.getString("discord_id") + " **Reason**: " + result.getString("reason"));
            }

        } catch (Exception e) {

        }


    }

    /**
     *
     * @param message
     */
    public void autoBanRemove(Message message) {

        String[] arguments = message.getContent().split(" ");


        if (arguments.length != 3) {
            message.reply("I have got past the argument checking");
        } else {
            try {

                // Find Users by Email
                String removeBannedUser = "DELETE FROM public.bans WHERE discord_id = ?";

                // Use Java Prepared Statements to protect against SQL Injection
                PreparedStatement queryProtectedStatement = connection.prepareStatement(removeBannedUser);
                queryProtectedStatement.setString(1, arguments[2]);


                                  /* Locate email address in database */
                queryProtectedStatement.execute();

                message.reply("Removed: " + arguments[2] + " from the autoban list");

            } catch (Exception e) {
                message.reply(e.toString());
            }
        }
    }

    /** Method to add user to the autoban arrayList
     * @param message
     */
    public void autoBanAdd(Message message) {

        String[] arguments = message.getContent().split(" ");


        if (arguments.length < 4) {
            message.reply("I have got past the argument checking");
        } else {
            try {

                String[] reason = message.getContent().split("-reason");
                // Find Users by Email
                String insertBannedUser = "INSERT INTO public.bans (discord_id, reason)" +
                        "VALUES (?, ?);";

                // Use Java Prepared Statements to protect against SQL Injection
                PreparedStatement queryProtectedStatement = connection.prepareStatement(insertBannedUser);
                queryProtectedStatement.setString(1, arguments[2]);
                queryProtectedStatement.setString(2, reason[1]);

                queryProtectedStatement.execute();

                message.reply("Inserted: " + arguments[2] + " into the autoban list for the reason: " + reason[1]);

            } catch (Exception e) {
                message.reply(e.toString());
            }
        }
    }


    /** Unmute User
     * @param message
     */
    public void unMute(Message message) {
        String[] arguments = message.getContent().split(" ");
        if (arguments.length != 2) {
        } else {
            try {
                if (!arguments[1].contains("@")) {
                    message.reply("You have not specified a person to allow to speak");
                } else {
                    for (int i = 0; i < CalculusListener.getMuteList().size(); i++) {
                        if (CalculusListener.getMuteList().get(i).equalsIgnoreCase(arguments[1])) {
                            CalculusListener.removeMuted(i);
                        }
                    }
                    message.reply(arguments[1] + " has been allowed to speak once more");
                }
            } catch (Exception e) {
                message.reply("Something went wrong there");
            }
        }
    }

    /** Mute User
     * @param message
     */
    public void mute(Message message) {
        String[] arguments = message.getContent().split(" ");
        if (arguments.length != 2) {
        } else {
            try {
                if (!arguments[1].contains("@")) {
                    message.reply("You have not specified a person to mute");
                } else if (CalculusListener.getMuteList().contains(arguments[1])) {
                    message.reply(arguments[1] + "has already been muted");
                } else {
                    CalculusListener.addMuted(arguments[1]);
                    message.reply(arguments[1] + " has been muted");
                }
            } catch (Exception e) {
                message.reply("Something went wrong there");
            }
        }
    }

    /** Add user to watch list
     * @param message
     */
    public void watch(Message message) {

        String[] arguments = message.getContent().split(" ");


        if (arguments.length != 2) {
            message.reply("I have got past the argument checking");
        } else {
            try {
                if (!arguments[1].contains("@")) {
                    message.reply("You have not specified a person to watch");
                } else {
                    // insert used by discord id
                    String insertWatchUser = "INSERT INTO public.watch (discord_handle)" +
                            "VALUES (?);";

                    // Use Java Prepared Statements to protect against SQL Injection
                    PreparedStatement queryProtectedStatement = connection.prepareStatement(insertWatchUser);
                    queryProtectedStatement.setString(1, arguments[1]);

                    // insert watched user
                    queryProtectedStatement.execute();

                    message.reply("Inserted: " + arguments[1] + " is being watched");
                    message.delete();
                }

            } catch (Exception e) {
                message.reply(e.toString());
            }
        }

    }

    /** Internal ID lookup
     * @param message
     */
    public void lookup(Message message) {
        String[] arguments = message.getContent().split(" ");

        Future<User> userSearch = api.getUserById(arguments[1]);

        int searchedNumber = 0;

        message.reply("Searching ID. "+ arguments[1]);

        while(!userSearch.isDone() && searchedNumber < 3) {
            searchedNumber++;
        }

        if(searchedNumber == 3) {
            message.reply("It's likely I couldn't find that user..");
        }

        User foundUser = null;

        try {
            foundUser = userSearch.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(arguments.length != 2) {
        }  else {

            String playedGame;

            if(foundUser.getGame() == null) {
                playedGame = "nothing...";
            } else {
                playedGame = foundUser.getGame();
            }

            try {
                message.reply("Lookup Found: "+ userSearch.get().getName() +
                        "\n\nIs the user a bot?: "+ userSearch.get().isBot()+
                        "\n\nUser is playing: "+playedGame);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
}

    /**
     * @param message
     */
    public void unWatch(Message message) {
        // Connect to the confederacy vulcan database
        String[] arguments = message.getContent().split(" ");


        if (arguments.length != 2) {
            message.reply("I have got past the argument checking");
        } else {
            try {

                // Find Users by Email
                String removeBannedUser = "DELETE FROM public.watch WHERE discord_handle = ?";

                // Use Java Prepared Statements to protect against SQL Injection
                PreparedStatement queryProtectedStatement = connection.prepareStatement(removeBannedUser);
                queryProtectedStatement.setString(1, arguments[1]);


                                  /* Locate email address in database */
                queryProtectedStatement.execute();

                message.reply("Removed: " + arguments[1] + " is no longer being watched");
            } catch (Exception e) {
                message.reply(e.toString());
            }
        }
    }

    /**
     * @param message
     */
    public void listWatched(Message message) {
        try {
            // Find Users by Email
            String findUserByBan = "SELECT discord_handle FROM public.watch;";

            // Use Java Prepared Statements to protect against SQL Injection
            PreparedStatement queryProtectedStatement = connection.prepareStatement(findUserByBan);
            //                queryProtectedStatement.setString(1, loginForm.get().email);

                                  /* Locate email address in database */
            ResultSet result = queryProtectedStatement.executeQuery();

            while (result.next()) {
                message.reply("**Discord Handle**: " + result.getString("discord_handle"));
            }

        } catch (Exception e) {

        }
    }

    /**
     * @param server
     * @param user
     */
    public void checkBanList(Server server, User user) {
        try {
            // Find Users in ban list
            String findUserByBan = "SELECT discord_id, reason FROM public.bans;";

            // Use Java Prepared Statements to protect against SQL Injection
            PreparedStatement queryProtectedStatement = connection.prepareStatement(findUserByBan);

                                  /* Locate user in database */
            ResultSet result = queryProtectedStatement.executeQuery();

            while(result.next()) {
                if(user.getId().equalsIgnoreCase(result.getString("discord_id"))) {
                    server.banUser(user);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void adminHelp(Message message) {
        message.reply("Hello " + message.getAuthor().getMentionTag()+
                ". The administrator commands you have available are: **!autoban -list, !autoban -add, !autoban -remove, !mute, !unmute, List Muted, !watch, !unwatch, Who's Being Watched**");
    }

}
