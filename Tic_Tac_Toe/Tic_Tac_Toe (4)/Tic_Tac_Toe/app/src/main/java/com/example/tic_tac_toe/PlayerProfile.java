package com.example.tic_tac_toe;

public class PlayerProfile {
    private int profileID;       // Unique identifier for the profile
    private String profileName;  // Name of the player's profile
    private int playerRole;      // Role of the player: 1 for player, 0 for opponent
    private int numWin;          // Number of wins for the player
    private int numLoss;         // Number of losses for the player
    private int numDraw;         // Number of draws for the player


    /**
     * General constructor to create a new profile with default values.
     */
    public PlayerProfile() {
        this.profileID = 0;
        this.profileName = "";
        this.playerRole = 0;
        this.numWin = 0;
        this.numLoss = 0;
        this.numDraw = 0;
    }
    /**
     * Constructor to create a new profile.
     *
     * @param profileID   The unique identifier for the profile.
     * @param profileName The name of the player's profile.
     * @param playerRole  The role of the player: 1 for player, 0 for opponent.
     * @param numWin      The number of wins for the player.
     * @param numLoss     The number of losses for the player.
     * @param numDraw     The number of draws for the player.
     */
    public PlayerProfile(int profileID, String profileName, int playerRole,
                   int numWin, int numLoss, int numDraw) {
        this.profileID = profileID;
        this.profileName = profileName;
        this.playerRole = playerRole;
        this.numWin = numWin;
        this.numLoss = numLoss;
        this.numDraw = numDraw;
    }

    // Getters

    /**
     * Get the unique identifier of the profile.
     *
     * @return The profile's unique identifier.
     */
    public int getProfileID() {
        return profileID;
    }

    /**
     * Get the name of the player's profile.
     *
     * @return The name of the profile.
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     * Get the player's role: 1 for player, 0 for opponent.
     *
     * @return The player's role.
     */
    public int getPlayerRole() {
        return playerRole;
    }

    /**
     * Get the number of wins for the player.
     *
     * @return The number of wins.
     */
    public int getNumWin() {
        return numWin;
    }

    /**
     * Get the number of losses for the player.
     *
     * @return The number of losses.
     */
    public int getNumLoss() {
        return numLoss;
    }

    /**
     * Get the number of draws for the player.
     *
     * @return The number of draws.
     */
    public int getNumDraw() {
        return numDraw;
    }

    // Setters

    /**
     * Set the name of the player's profile.
     *
     * @param profileName The new name for the profile.
     */
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    /**
     * Set the player's role: 1 for player, 0 for opponent.
     *
     * @param playerRole The new role for the player.
     */
    public void setPlayerRole(int playerRole) {
        this.playerRole = playerRole;
    }

    /**
     * Set the number of wins for the player.
     *
     * @param numWin The new number of wins.
     */
    public void setNumWin(int numWin) {
        this.numWin = numWin;
    }

    /**
     * Set the number of losses for the player.
     *
     * @param numLoss The new number of losses.
     */
    public void setNumLoss(int numLoss) {
        this.numLoss = numLoss;
    }

    /**
     * Set the number of draws for the player.
     *
     * @param numDraw The new number of draws.
     */
    public void setNumDraw(int numDraw) {
        this.numDraw = numDraw;
    }
}

