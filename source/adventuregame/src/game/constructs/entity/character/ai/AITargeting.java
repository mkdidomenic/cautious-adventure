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
import java.util.List;
import java.util.Random;
import main.Command;

/**
 *
 * @author Mike
 */
public class AITargeting extends AI {
    
    public State melee;
    public State ranged;
    public Gharacter target;

    public AITargeting(Gharacter gharacter) {
        super(gharacter);
        this.target = null;
    }
    
    public boolean hasTarget(){
        return (this.target != null) && (this.target.exists);
    }

    @Override
    public void init() {
        this.gharacter.setOrientation(-1);
        aquireTarget();
    }

    private void aquireTarget() {
        LinkedList<PlayerCharacter> players = Game.instance.space.getPlayers();
        if (players.size() > 0){
            Random rand = new Random();
            int index = (int) Math.floor(players.size() * rand.nextDouble());
            this.target = players.get(index);
        }
    }

    @Override
    public void update() {
        if (!(this.hasTarget())){
            this.aquireTarget();
        }
        lineUp();
        
    }
    
    public void lineUp(){
        if (this.hasTarget()){
            double ydistance = this.gharacter.position.y - this.target.position.y;
            if (Math.abs(ydistance) > 10){
                if (this.gharacter.position.y < this.target.position.y){
                    this.gharacter.handleCommand(Command.MOVE_UP);
                } else {
                    this.gharacter.handleCommand(Command.MOVE_DOWN);
                }
            } else {
                this.gharacter.handleCommand(Command.ACTION2);
            }
        }
    }
    
    public void approach(){
        
    }

}
