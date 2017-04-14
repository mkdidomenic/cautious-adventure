/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs.entity.character;

import game.Player;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class PlayerCharacter extends Gharacter {

    public Player player;

    public PlayerCharacter(Player p, Spatial position) {
        super(position);
        this.player = p;
        this.setAlly(true);
        this.addImages();
    }

    public PlayerCharacter(Player p, Spatial position, Spatial size) {
        super(position, size);
        this.player = p;
        this.setAlly(true);
        this.addImages();
    }

    private void addImages() {
        this.addImage("src/res/knight.png");
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

        if (true) {
            debugM();
        }

    }

    public int ID() {
        return this.player.ID;
    }

    public void debugM() {
        //System.out.println(this.getFramesSinceLastMovement());
        //System.out.println(this.state.toString());
    }

}
