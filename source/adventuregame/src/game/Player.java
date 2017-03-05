/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author Mike
 */
public class Player {

    public static int num_players = 0;

    public String name;
    public int ID;

    public Player(String name) {
        this.name = name;
        this.ID = num_players;
        num_players++;

    }

}
