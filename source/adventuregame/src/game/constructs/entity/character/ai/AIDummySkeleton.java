/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs.entity.character.ai;

import game.Game;
import game.constructs.entity.character.Gharacter;
import game.constructs.entity.character.Gharacter.State;
import game.constructs.entity.character.PlayerCharacter;
import java.util.LinkedList;
import main.Command;

/**
 *
 * @author Mike
 */
public class AIDummySkeleton extends AI {

    public State melee;
    public State ranged;
    public Gharacter target;
    public aitask task;

    public boolean angry;
    public double previousHealth;

    public enum aitask {
        ATTACK,
        WAIT,
        RETREAT,
        APPROACH;
    }

    public AIDummySkeleton(Gharacter gharacter) {
        super(gharacter);
        this.target = null;
        this.task = aitask.WAIT;
        this.previousHealth = gharacter.health;
        this.angry = false;
        this.timerMax = 40;
    }

    public boolean hasTarget() {
        return (this.target != null) && (this.target.exists);
    }

    @Override
    public void init() {
        super.init();
        this.gharacter.setOrientation(-1);
        aquireTarget();
    }

    private void aquireTarget() {
        LinkedList<PlayerCharacter> players = Game.instance.space.getPlayers();
        if (players.size() > 0) {
            int index = (int) Math.floor(
                    players.size() * this.random.nextDouble());
            this.target = players.get(index);
        }
    }

    @Override
    public void update() {
        super.update();
        if (!(this.hasTarget())) {
            this.aquireTarget();
        }
        if (this.hasTarget()) {
            faceTarget();
            if (this.inRange()) {
                this.attackTargetIfInRange();
            }
            handleTask();
            healthAbilityBehavior();
            if (this.task == aitask.ATTACK) {
                this.attackTarget();
            }
            if (this.task == aitask.APPROACH) {
                double original = this.gharacter.move_speed;
                this.gharacter.move_speed /= 2;
                this.approachTargetX();
                this.approachTargetY();
                this.gharacter.move_speed = original;
            }
            if (this.task == aitask.RETREAT) {
                this.retreatFromTarget();
            }
        }

    }

    private void handleTask() {
        if ((this.gharacter.immune) || (this.gharacter.hitstunFrames == 0)) {
            this.task = aitask.ATTACK;
        } else if (this.task == aitask.ATTACK) {
            this.task = aitask.RETREAT;
        }

        if ((this.timer == 0)) {
            if (this.task == aitask.RETREAT) {
                this.task = aitask.WAIT;
            } else if (this.percentChance(60)) {
                this.task = aitask.APPROACH;
            } else if (this.percentChance(10)) {
                this.task = aitask.RETREAT;
            } else {
                this.task = aitask.WAIT;
            }
            System.out.println(this.task);
        }
    }

    private void healthAbilityBehavior() {
        if (this.previousHealth > this.gharacter.health) {
            this.previousHealth = this.gharacter.health;
            this.angry = true;
        }
        if (this.angry) {
            if (this.gharacter.health < (0.2 * this.gharacter.max_health)) {
                if (this.gharacter.canAct()) {
                    this.gharacter.handleCommand(Command.ACTION4);
                    this.angry = false;
                }
            } else if (this.gharacter.health < (0.8 * this.gharacter.max_health)) {
                if (this.gharacter.canAct()) {
                    this.gharacter.handleCommand(Command.ACTION3);
                    this.angry = false;
                }
            }
        }

    }

    public void attackTarget() {
        approachTargetY();
        approachTargetX();
        attackTargetIfInRange();
    }

    public double yPrecision = 2;

    public boolean inLineY() {
        double ydistance = this.gharacter.position.y - this.target.position.y;
        return Math.abs(ydistance) <= this.yPrecision;
    }

    public void approachTargetY() {
        if (this.hasTarget()) {
            if (!this.inLineY()) {
                if (this.gharacter.position.y < this.target.position.y) {
                    this.gharacter.handleCommand(Command.MOVE_UP);
                } else {
                    this.gharacter.handleCommand(Command.MOVE_DOWN);
                }
            }
        }
    }

    public double xPrecision = 8;

    public boolean inLineX() {
        double xdistance = this.gharacter.position.x - this.target.position.x;
        return Math.abs(xdistance) <= this.xPrecision;
    }

    public void approachTargetX() {
        if (this.hasTarget()) {

            if (!inLineX()) {
                if (this.gharacter.position.x < this.target.position.x) {
                    this.gharacter.handleCommand(Command.MOVE_RIGHT);
                } else {
                    this.gharacter.handleCommand(Command.MOVE_LEFT);
                }
            }
        }
    }

    public void retreatFromTarget() {
        if (this.hasTarget()) {
            if (this.gharacter.position.y < this.target.position.y) {
                this.gharacter.handleCommand(Command.MOVE_DOWN);
            } else {
                this.gharacter.handleCommand(Command.MOVE_UP);
            }
            if (this.gharacter.position.x < this.target.position.x) {
                this.gharacter.handleCommand(Command.MOVE_LEFT);
            } else {
                this.gharacter.handleCommand(Command.MOVE_RIGHT);
            }

        }
    }

    public void faceTarget() {
        if (this.hasTarget()) {
            if (this.gharacter.position.x < this.target.position.x) {
                this.gharacter.x_orientation = 1;
            } else {
                this.gharacter.x_orientation = -1;;
            }
        }
    }

    public boolean inRange() {
        return (inLineX() && inLineY());
    }

    public void attackTargetIfInRange() {
        if (this.hasTarget()) {
            if (this.inRange()) {
                this.gharacter.handleCommand(Command.ACTION1);
            }
        }
    }

}
