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
import java.util.Random;
import main.Command;

/**
 *
 * @author Mike
 */
public class AISmack extends AI {

    public State melee;
    public State ranged;
    public Gharacter target;

    public AISmack(Gharacter gharacter) {
        super(gharacter);
        this.target = null;
    }

    public boolean hasTarget() {
        return (this.target != null) && (this.target.exists);
    }

    @Override
    public void init() {
        this.gharacter.setOrientation(-1);
        aquireTarget();
    }

    private void aquireTarget() {
        LinkedList<PlayerCharacter> players = Game.instance.space.getPlayers();
        if (players.size() > 0) {
            Random rand = new Random();
            int index = (int) Math.floor(players.size() * rand.nextDouble());
            this.target = players.get(index);
        }
    }

    @Override
    public void update() {
        if (!(this.hasTarget())) {
            this.aquireTarget();
        }
        faceTarget();
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

    public void faceTarget() {
        if (this.hasTarget()) {
            if (this.gharacter.position.x < this.target.position.x) {
                this.gharacter.x_orientation = 1;
            } else {
                this.gharacter.x_orientation = -1;;
            }
        }
    }

    public void attackTargetIfInRange() {
        if (this.hasTarget()) {
            if ((inLineX()) && (inLineY())) {
                this.gharacter.handleCommand(Command.ACTION1);
            }
        }
    }

}
