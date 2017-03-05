/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs;

import game.Player;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class PlayerCharacter extends Character {

    public Player player;

    public PlayerCharacter(Player p, Spatial position) {
        super(position);
        this.player = p;
        this.addImage("src/res/frogglet.jpg");
    }

    public PlayerCharacter(Player p, Spatial position, Spatial size) {
        super(position, size);
        this.player = p;
        this.addImage("src/res/frogglet.jpg");
    }

    @Override
    public String toString() {
        String s = "NAME: " + this.player.name + "\n";
        s = s + super.toString();
        return s;
    }

    @Override
    public void update() {
        super.update();

    }

    public int ID() {
        return this.player.ID;
    }

}
