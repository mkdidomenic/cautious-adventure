/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.Serializable;

/**
 *
 * @author Mike
 */
public class Player implements Serializable {

    public static int num_players = 0;

    public String name;
    public String classtype;
    public int ID;

    public Player(String name, String classtype) {
        this.name = name;
        this.classtype = classtype;
        this.ID = num_players;
        num_players++;

    }

}
