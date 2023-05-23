package com.webcheckers.appl;

import com.webcheckers.model.AIBot;
import com.webcheckers.model.Player;

import java.util.*;

/**
 * List of all online users in WebCheckers
 *
 * @author JiaLiang (Hayden) Liang
 * @author Shrif Rai
 * @author Greg Godlewski
 */
public class PlayerLobby {

    /**
     * Enums for player username registration
     *
     */
    public enum UsernameAvailability {
        AVAILABLE, TAKEN, INVALID
    }

    private HashMap<String, Player> allUsers;
    private HashMap<String, Player> availableUsers;

    public static final AIBot jarvisBot = new AIBot("Jarvis (Easy AI)", AIBot.Level.EASY);

    /**
     * Constructor for lobby
     */
    public PlayerLobby () {
        this.allUsers = new HashMap<>();
        this.availableUsers = new HashMap<>();

        allUsers.put(jarvisBot.getName(), jarvisBot);
        availableUsers.put(jarvisBot.getName(), jarvisBot);
    }

    /**
     * adds a player to the lobby
     * @param username The username entered by the player
     * @return returns if username is avaible, taken, or invalid
     */
    public UsernameAvailability add_player(String username){
        UsernameAvailability available = usernameAvaiable(username);

        if(available == UsernameAvailability.AVAILABLE) {
            Player player = new Player(username);
            allUsers.put(username, player);
            availableUsers.put(username, player);
        }
        return available;
    }

    /**
     * Check if username is avaible and valid to use
     *
     * @param username to be entered
     * @return returns if username is avaible, taken, or invalid
     */
    public UsernameAvailability usernameAvaiable(String username) {
        UsernameAvailability avaiablility = UsernameAvailability.AVAILABLE;
        if(allUsers.containsKey(username))
            avaiablility = UsernameAvailability.TAKEN;
        else {
            if(username.length() == 0) {
                avaiablility = UsernameAvailability.INVALID;
            }
            else {
                boolean hasAlpha = false;
                int asciiCode = (int) username.charAt(0);
                if(!(65 <= asciiCode && asciiCode <= 90 || 97 <= asciiCode && asciiCode <= 122)) {
                    avaiablility = UsernameAvailability.INVALID;
                }
                for(int i = 0; i < username.length(); i++) {
                    asciiCode = (int) username.charAt(i);
                    if(65 <= asciiCode && asciiCode <= 90 || 97 <= asciiCode && asciiCode <= 122) {
                        hasAlpha = true;
                    } else if(username.charAt(i) == ' ' || 48 <= asciiCode && asciiCode <= 57 || 65 <= asciiCode && asciiCode <= 90 || 97 <= asciiCode && asciiCode <= 122) {
                        continue;
                    }else {
                        avaiablility = UsernameAvailability.INVALID;
                    }
                }
                if(!hasAlpha) {
                    avaiablility = UsernameAvailability.INVALID;
                }
            }
        }
        return avaiablility;
    }

    /**
     * Compile a list of all signed-in players' usernames
     *
     * @return list of all usernames
     */
    public List<String> getUsernamesList() {
        Set<String> allPlayers = allUsers.keySet();
        List<String> usernames = new ArrayList<>(allPlayers);
        return usernames;
    }

    /**
     * Compile a list of all players who are signed-in and not in a game
     *
     * @return list of avaible players
     */
    public List<Player> getAvailblePlayers() {
        Collection<Player> availblePlayers = availableUsers.values();
        List<Player> playerList = new ArrayList<>(availblePlayers);
        return playerList;
    }

    /**
     * Removes a player from the player lobby
     *
     * @param name username of the player to be removed
     */
    public void removePlayer(String name) {
        availableUsers.remove(availableUsers.get(name));
    }

    /**
     * Get a player with a given name
     *
     * @param name username of player
     * @return player object
     */
    public Player getPlayer(String name) {
        return (allUsers.get(name));
    }


    /**
     * Get a player with username and remove it from
     *
     * @param name username of player
     * @return player object
     */
    public Player getPlayerRemove(String name) {
        Player p = getPlayer(name);
        removePlayer(name);
        return p;
    }

    /**
     * makes the player avaiable
     *
     * @param player - the player to be made a avaiable
     */
    public void makeAvailable(Player player) {
        availableUsers.put(player.getName(), player);
    }

    /**
     * Signs out a player
     *
     * @param name - the player to be signed out
     */
    public void signOut(String name){
        allUsers.remove(name);
        availableUsers.remove(name);
    }
}
