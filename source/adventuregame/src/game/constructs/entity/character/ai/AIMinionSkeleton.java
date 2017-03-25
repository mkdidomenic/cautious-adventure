/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs.entity.character.ai;

import game.Game;
import game.constructs.entity.character.Gharacter;
import game.constructs.entity.character.Gharacter.State;
import main.Command;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class AIMinionSkeleton extends AI {

    public State melee;
    public State ranged;
    public Gharacter target;
    public aitask task;

    public boolean angry;
    public double previousHealth;

    public enum aitask {
        ATTACK,
        WAIT,
        RETURN,
        APPROACH;
    }

    public AIMinionSkeleton(Gharacter gharacter) {
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
    }

    private void setTarget(Gharacter g) {
        this.target = g;
    }

    public boolean hasParent() {
        if (this.gharacter == null) {
            return false;
        }
        return this.gharacter.parent != null;
    }

    @Override
    public void update() {
        super.update();
        if (this.hasTarget()) {
            faceTarget();
            this.attackTargetIfInRange();

        } else if (this.hasParent()) {
            //this.faceParent();
            this.handleTask();
        } else {
            this.gharacter.die();
        }
        this.attackNearbyEnemy();

    }

    private void handleTask() {
        if (this.task == aitask.RETURN) {
            if (!this.nearParent()) {
                this.approachParent();
            } else {
                this.task = aitask.WAIT;
            }
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

    public void attackNearbyEnemy() {
        for (Gharacter g : Game.instance.space.getGharacters()) {
            if (g.isAlly() != this.gharacter.isAlly()) {
                if (this.inRange(g.position)) {
                    if (!(this.gharacter.isInFrontOf(g.position))) {
                        this.gharacter.x_orientation *= -1;
                    }
                    this.gharacter.handleCommand(Command.ACTION1);
                }
            }
        }
    }

    public void attackTarget() {
        approachTargetY(this.target.position);
        approachTargetX(this.target.position);
        attackTargetIfInRange();
    }

    public boolean nearParent() {
        double xlimit = 5;
        double ylimit = 5;
        double ydistance = this.gharacter.position.y - this.gharacter.parent.position.y;
        double xdistance = this.gharacter.position.x - this.gharacter.parent.position.x;
        if (true) {
            // a little bit of randomization in my life
            double rfactor = 3;
            xlimit += (this.random.nextDouble() * rfactor) - rfactor / 2;
            rfactor *= 4;
            ylimit += (this.random.nextDouble() * rfactor) - rfactor / 2;
        }
        return (Math.abs(ydistance) <= ylimit) && (Math.abs(xdistance) <= xlimit);
    }

    public double yPrecision = 2;

    public boolean inLineY(Spatial targetpos) {
        double ydistance = this.gharacter.position.y - targetpos.y;
        return Math.abs(ydistance) <= this.yPrecision;
    }

    public void approachTargetY(Spatial targetpos) {
        if (!this.inLineY(targetpos)) {
            if (this.gharacter.position.y < targetpos.y) {
                this.gharacter.handleCommand(Command.MOVE_UP);
            } else {
                this.gharacter.handleCommand(Command.MOVE_DOWN);
            }
        }

    }

    public double xPrecision = 8;

    public boolean inLineX(Spatial targetpos) {
        double xdistance = this.gharacter.position.x - targetpos.x;
        return Math.abs(xdistance) <= this.xPrecision;
    }

    public void approachTargetX(Spatial targetpos) {

        if (!inLineX(targetpos)) {
            if (this.gharacter.position.x < targetpos.x) {
                this.gharacter.handleCommand(Command.MOVE_RIGHT);
            } else {
                this.gharacter.handleCommand(Command.MOVE_LEFT);
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

    public void faceParent() {
        if (this.hasParent()) {
            if (this.gharacter.position.x < this.gharacter.parent.position.x) {
                this.gharacter.x_orientation = 1;
            } else {
                this.gharacter.x_orientation = -1;
            }
        }
    }

    public void approachParent() {
        if (this.hasParent()) {
            if (this.gharacter.position.y >= this.gharacter.parent.position.y) {
                this.gharacter.handleCommand(Command.MOVE_DOWN);
            } else {
                this.gharacter.handleCommand(Command.MOVE_UP);
            }
            if (this.gharacter.position.x >= this.gharacter.parent.position.x) {
                this.gharacter.handleCommand(Command.MOVE_LEFT);
            } else {
                this.gharacter.handleCommand(Command.MOVE_RIGHT);
            }

        }
    }

    public boolean inRange(Spatial targetpos) {
        return (inLineX(targetpos) && inLineY(targetpos));
    }

    public void attackTargetIfInRange() {
        if (this.hasTarget()) {
            if (this.inRange(this.target.position)) {
                this.gharacter.handleCommand(Command.ACTION1);
            }
        }
    }

}
