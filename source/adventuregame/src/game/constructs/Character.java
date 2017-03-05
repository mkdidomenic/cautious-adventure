/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs;

import main.Command;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class Character extends Entity {

    public double move_speed = 1;
    public double jump_velocity = 0.2;

    public Character(Spatial position) {
        super(position, new Spatial(5, 5, 10));
        // default size
    }

    public Character(Spatial position, Spatial size) {
        super(position, size);
    }

    public void handleCommand(Command c) {
        switch (c) {
            case MOVE_UP:
                this.move(0, move_speed, 0);
                break;
            case MOVE_DOWN:
                this.move(0, -1 * move_speed, 0);
                break;
            case MOVE_LEFT:
                this.move(-1 * move_speed, 0, 0);
                break;
            case MOVE_RIGHT:
                this.move(move_speed, 0, 0);
                break;
            case JUMP:
                if (this.position.z == 0) {
                    this.velocity.z = jump_velocity;
                }
                break;
            default:
                //System.out.println("Player class: Not a command = " + c);
                break;
        }
    }

}
