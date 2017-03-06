/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs.entity.character;

import game.Game;
import game.constructs.Construct;
import game.constructs.entity.Entity;
import game.constructs.entity.Projectile;
import game.constructs.entity.ProjectileArrow;
import main.Command;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class Gharacter extends Entity {

    public static final Spatial default_size = new Spatial(7, 14, 12);

    // attributes
    public double max_health = 100;
    public double normal_move_speed = 1;
    public double move_speed = normal_move_speed;
    public double jump_velocity = 2.5;

    // state
    public double health;

    public Gharacter(Spatial position) {
        super(position, default_size.copy());
        // default size
        this.setupAttr();
    }

    public Gharacter(Spatial position, Spatial size) {
        super(position, size);
        this.setupAttr();
    }

    private void setupAttr() {
        this.health = this.max_health;
    }

    @Override
    public void outOfBounds(String s) {
        super.outOfBounds(s);
        if (s.equals("x+")) {
            this.position.x -= this.size.x;
        }
        if (s.equals("y+")) {
            this.position.y -= this.size.y;
        }
        if (s.equals("z+")) {
            this.position.z -= this.size.z;
        }
        //
        if (s.equals("x-")) {
            this.position.x += this.size.x;
        }
        if (s.equals("y-")) {
            this.position.y += this.size.y;
        }
        if (s.equals("z-")) {
            this.position.z += this.size.z;
        }
    }

    @Override
    public void onCollision(Construct c) {
        super.onCollision(c);
        if (c.tangible()) {
            //back up
        }
        this.damage(c.hurts());
    }

    @Override
    public void update() {
        super.update();
        if (this.health <= 0) {
            this.die();
        }
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
                this.jump();
                break;
            case ACTION1:
                this.action1();
                break;
            case ACTION2:
                this.action2();
                break;
            default:
                //System.out.println("Player class: Not a command = " + c);
                break;
        }
    }

    public void jump() {
        if (this.position.z == 0) {
            this.velocity.z = jump_velocity;
        }
    }

    public void die() {
        this.remove();
    }

    public void damage(double damage) {
        this.health += damage;
    }

    public void heal(double health) {
        this.health += health;
    }

    public void action1() {
        if (this.setAction(12)) {
            Projectile p = new ProjectileArrow(this.position.copy(),
                                               new Spatial(4, 2, 2));
            p.position.x += this.x_orientation * (this.size.x / 2 + p.size.x / 2);
            p.velocity.x = this.x_orientation * (2);
            p.position.z += this.size.z / 2;
            p.acceleration.z = 0.19;
            //System.out.println(p);
            Game.instance.space.addConstruct(p);
        }
    }

    public void action2() {
        NonPlayerCharacter npc = new NonPlayerCharacter(this.position.copy());
        Game.instance.space.addConstruct(npc);

    }

}
