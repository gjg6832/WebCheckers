package com.webcheckers.model;

/**
 * A player object
 *
 * @author Greg Godlewski
 */
public class Player {

    private String name;

    /**
     * Makes a player with a given name
     *
     * @param name - the name of the player
     */
    public Player(String name){
        this.name = name;
    }

    /**
     * Gets the player's name
     *
     * @return the players name
     */
    public String getName(){
        return name;
    }

    /**
     * Get Player object's username
     *
     * @return
     */
    public String toString() {
        return getName();
    }
}
