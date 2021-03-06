/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs.entity.character;

import game.Game;
import game.classtype.Classtype;
import game.classtype.ClasstypeAssassin;
import game.constructs.Construct;
import game.constructs.entity.Entity;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import main.Command;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class Gharacter extends Entity {

    public static final Spatial DEFAULT_SIZE = new Spatial(7, 14, 12);

    // attributes
    public double max_health = 100;
    public double max_energy = 100;
    public int default_hitstun_frames = 2;
    public double energyGenerationPerFrame = 0.4;
    public double normal_move_speed = 1;
    public double move_speed = normal_move_speed;
    public double jump_velocity = 4;
    public int hitstunFrames = default_hitstun_frames;

    // class type
    public Classtype classtype;
    public ArrayList<State> mobileStates;

    // frame number that we last moved at
    public long lastMove;

    // state information
    public State state;
    public boolean sheilded;
    public boolean immune;
    public double health;
    public double energy;

    public Gharacter(Spatial position) {
        super(position, DEFAULT_SIZE.copy());
        // default size
        this.setupGharacter();

    }

    public Gharacter(Spatial position, Spatial size) {
        super(position, size);
        this.setupGharacter();
    }

    private void setupGharacter() {
        // default classtype
        this.setAttr();
        // state
        this.mobileStates = new ArrayList();
        this.state = State.IDLE;
        this.sheilded = false;
        this.immune = false;
        // movement
        this.lastMove = Game.instance.getCurrentFrame();
        // classtype
        this.classtype = new ClasstypeAssassin(this);
        this.classtype.setupAttributes();
    }

    public void setAttr() {
        //setup attributes
        this.health = this.max_health;
        this.energy = this.max_energy;
        this.move_speed = this.normal_move_speed;
        this.hitstunFrames = this.default_hitstun_frames;
    }

    public void setClasstype(Classtype ct) {
        this.classtype = ct;
        this.classtype.setupAttributes();
    }

    @Override
    public BufferedImage getImage() {
        return this.classtype.getImage();
    }

    @Override
    public void move(Spatial s) {
        this.move(s.x, s.y, s.z);
    }

    @Override
    public void move(double x, double y, double z) {
        if (this.canMove()) {
            if (this.state == State.IDLE) {
                super.move(x, y, z);
                this.lastMove = Game.instance.getCurrentFrame();
                this.state = State.MOVING;
            } else if (this.state == State.MOVING) {
                super.move(x, y, z);
                this.lastMove = Game.instance.getCurrentFrame();
            } // should not be able to move in the air while doing other things
            else if ((this.state == State.JUMPING) && false) {
                super.move(x, y, z);
                this.lastMove = Game.instance.getCurrentFrame();
            } // should be able to move in the air while doing other things?
            else if ((!this.isGrounded()) && true) {
                super.move(x, y, z);
                this.lastMove = Game.instance.getCurrentFrame();
            } else if ((this.mobileStates.contains(this.state)) && true) {
                super.move(x, y, z);
                this.lastMove = Game.instance.getCurrentFrame();
            }
        }

    }

    public boolean canMove() {
        return (this.state != State.STUNNED) && (this.state != State.FALLEN);
    }

    @Override
    public boolean canAct() {
        return (super.canAct()) && (this.state != State.STUNNED);
    }

    @Override
    public void outOfBounds(String s) {
        super.outOfBounds(s);
        if (s.equals("x+")) {
            this.position.x = Game.instance.space.dimensions.x;
        }
        if (s.equals("y+")) {
            this.position.y = Game.instance.space.dimensions.y;
        }
        if (s.equals("z+")) {
            this.position.z = Game.instance.space.dimensions.z;
        }
        //
        if (s.equals("x-")) {
            this.position.x = 0;
        }
        if (s.equals("y-")) {
            this.position.y = 0;
        }
        if (s.equals("z-")) {
            this.position.z = 0;
        }
    }

    @Override
    public void onCollision(Construct c) {
        super.onCollision(c);
        if (c.isTangible()) {
            //back up
            this.collideWith(c);
        }
        c.gharacteract(this);
    }

    public long getFramesSinceLastMovement() {
        return Game.instance.getCurrentFrame() - this.lastMove;
    }

    private void collideWith(Construct c) {
        double distance;
        double sizes;
        double direction;

        // for now at least, no collision with other characters
        if (c instanceof Gharacter) {
            if (true) {
                return;
            }
            // dont move if you shouldnt be moving
            if ((Game.instance.getCurrentFrame() - this.lastMove) > 9) {
                return;
            }
        }
        // x
        if (((this.position.angleXY(c.position) < 45) || ((this.position.angleXY(
                c.position) > 315))) || ((this.position.angleXY(c.position) > 135) && ((this.position.angleXY(
                        c.position) < 225)))) {
            distance = Math.abs(this.position.x - c.position.x);
            sizes = this.size.x / 2 + c.size.x / 2;
            if (this.position.x >= c.position.x) {
                direction = 1;
            } else {
                direction = -1;
            }
            this.position.add(
                    new Spatial((direction) * (sizes - distance), 0, 0));
        }
        // y
        if (((this.position.angleXY(c.position) > 45) && ((this.position.angleXY(
                c.position) < 135))) || ((this.position.angleXY(c.position) > 225) && ((this.position.angleXY(
                        c.position) < 315)))) {
            distance = Math.abs(this.position.y - c.position.y);
            sizes = this.size.y / 2 + c.size.y / 2;
            if (this.position.y >= c.position.y) {
                direction = 1;
            } else {
                direction = -1;
            }
            this.position.add(
                    new Spatial(0, (direction) * (sizes - distance), 0));
        }

    }

    @Override
    public void update() {
        super.update();

        handleState();

        this.classtype.update();

        handleHealth();
        handleEnergy();

    }

    private void handleHealth() {
        if (this.health <= 0) {
            this.die();
        }
        if (this.health > this.max_health) {
            this.health = this.max_health;
        }
    }

    private void handleEnergy() {
        this.energy = this.energy + this.energyGenerationPerFrame;
        if (this.energy < 0) {
            this.energy = 0;
        }
        if (this.energy > this.max_energy) {
            this.energy = this.max_energy;
        }
    }

    private void handleState() {
        int idleFramesAfterMoving = 2;
        if (this.actionTimer == 0) {
            if (!(this.isGrounded())) {
                this.setState(State.JUMPING);
            } else {
                if ((this.getFramesSinceLastMovement() > idleFramesAfterMoving)) {
                    this.setState(State.IDLE);
                } else {
                    this.setState(State.MOVING);
                }

            }
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
                this.ability(1);
                break;
            case ACTION2:
                this.ability(2);
                break;
            case ACTION3:
                this.ability(3);
                break;
            case ACTION4:
                this.ability(4);
                break;
            default:
                //System.out.println("Player class: Not a command = " + c);
                break;
        }
    }

    public void jump() {
        if (this.isGrounded() && this.canAct()) {
            this.velocity.z = jump_velocity;
            this.state = State.JUMPING;
        }
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState(State state) {
        return this.state;
    }

    public void die() {
        this.remove();
    }

    public boolean vulnerable(Construct c) {
        if (this.immune) {
            return false;
        } else if (this.sheilded && (Math.signum(c.position.x - this.position.x) == this.x_orientation)) {
            return false;
        } else {
            return true;
        }
    }

    public void damage(double damage) {
        this.health -= damage;
        if ((damage > 2) && (this.state != State.STUNNED) && (this.state != State.FALLEN)) {
            this.hitstun();
        }
    }

    public void heal(double health) {
        this.health += health;
    }

    public void stun(int frames) {
        this.interruptActionTimer();
        this.setActionTimer(frames);
        this.state = State.STUNNED;
    }

    public void knockDown(int frames) {
        this.interruptActionTimer();
        this.setActionTimer(frames);
        this.state = State.FALLEN;
    }

    public void hitstun() {
        if (this.hitstunFrames > 0) {
            this.interruptActionTimer();
            this.setActionTimer(this.hitstunFrames);
            this.state = State.HITSTUNNED;
        }
    }

    public boolean useEnergy(double cost) {
        if (this.energy >= cost) {
            this.energy -= cost;
            return true;
        } else {
            return false;
        }
    }

    public void ability(int i) {
        this.classtype.ability(i);
    }

    public enum State implements Serializable {
        IDLE,
        MOVING,
        JUMPING,
        STUNNED,
        HITSTUNNED,
        FALLEN,
        ABILITY1,
        ABILITY2,
        ABILITY3,
        ABILITY4;
    }

}
