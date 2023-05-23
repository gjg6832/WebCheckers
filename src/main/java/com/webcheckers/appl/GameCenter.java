package com.webcheckers.appl;

import com.webcheckers.model.CheckersGame;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * The object to coordinate matches for the web sever
 *
 *
 * @author Shrif Rai
 * @author Greg Godlewski
 * @author Ethan Yaccarino-Mims
 */
public class GameCenter {

    private PlayerLobby lobby;
    private HashMap<Player, CheckersGame> games;
    private HashMap<Player, Player> matchList;
    private HashMap<String, String> gameMessages;

    /**
     * constructer that creates the GameCenter object
     */
    public GameCenter(){
        games = new HashMap<>();
        lobby = new PlayerLobby();
        matchList = new HashMap<>();
        gameMessages = new HashMap<>();
    }

    /**
     * Starts a game with p1 as red and p2 as white
     *
     * @param p1 the name of the player who selected the other
     * @param p2 the name of the player who was selected
     */
    public CheckersGame startGame(Player p1, Player p2) {
        return addGame(
                getGame(p1, p2)
        );
    }

    /**
     *
     * @return a int of how many players are in the lobby
     */
    public int numPlayers() {
        return lobby.getUsernamesList().size();
    }

    /**
     * Matches two players and puts them in a matchList (active matches) as a pair
     *
     * @param p1 Player 1
     * @param p2 Player 2
     */
    public void addMatchedPairs(Player p1, Player p2) {
        matchList.put(p1, p2);
    }

    /**
     * Adds a game to games
     *
     * @param game - the game that needs to be added
     */
    public CheckersGame addGame(CheckersGame game){
        Player red = game.getRedPlayer();
        Player white = game.getWhitePlayer();

        addMatchedPairs(white, red);
        games.put(red, game);
        games.put(white, game);
        return game;
    }

    /**
     * Checks is a specific player is in a game or not
     *
     * @param player String Name of the player to be searched for in games
     * @return true if player is in game false if not
     */
    public boolean isInAGame(Player player) {
        return games.containsKey(player);
    }



    /**
     * Gets a new checkers game
     *
     * @return The new game
     */
    public CheckersGame getGame(Player player1, Player player2) {
        return new CheckersGame(player1, player2);
    }

    /**
     * Get a game that a specific player is in
     *
     * @param player - the player to retrieve the game for
     * @return the game
     */
    public CheckersGame retrieveGame(Player player){
        return games.get(player);
    }

    /**
     * Gets the lobby
     *
     * @return the lobby
     */
    public PlayerLobby getLobby() {
        return lobby;
    }

    /**
     * Adds the player to the player list
     *
     * @param name the new players name
     * @return if the name was valid
     */
    public boolean addPlayer(String name) {
        return lobby.add_player(name) == PlayerLobby.UsernameAvailability.AVAILABLE;
    }


    /**
     * returns the state of the player in text
     *
     * @param name the player being checked
     * @return the message associated with the player
     */
    public String getMessage(String name) {
        String message = gameMessages.get(name);
        gameMessages.remove(name);
        return message;
    }

    /**
     * checks if the player has a message waiting for them.
     *
     * @param name
     * @return
     */
    public boolean hasMessage(String name) {
        return gameMessages.containsKey(name);
    }

    /**
     * sets the message of text for a player
     *
     * @param name the players name
     * @param message the players message
     */
    public void setMessage(String name, String message) {
        gameMessages.put(name, message);
    }

    /**
     * Ends the current checkers game
     *
     * @param game - the game to be ended
     *
     */
    public CheckersGame endGame(CheckersGame game){
        Player redPlayer = game.getRedPlayer();
        Player whitePlayer = game.getWhitePlayer();

        lobby.makeAvailable(redPlayer);
        lobby.makeAvailable(whitePlayer);

        if(games.containsKey(redPlayer)){
            games.remove(redPlayer);
        }

        if(games.containsKey(whitePlayer)){
            games.remove(whitePlayer);
        }

        if(matchList.containsKey(redPlayer)){
            matchList.remove(redPlayer);
        }

        if(matchList.containsValue(whitePlayer)){
            matchList.remove(whitePlayer);
        }

        game = null;
        return game;
    }

}