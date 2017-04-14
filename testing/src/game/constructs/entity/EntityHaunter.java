/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs.entity;

import game.Game;
import game.constructs.ImageBox;
import game.constructs.entity.character.Gharacter;
import java.awt.image.BufferedImage;
import utility.ImageHandler;
import utility.Spatial;

/**
 *
 * @author mike
 */
public class EntityHaunter extends Entity{
    
    public int lifetime;
    public Gharacter victim;
    public boolean hasVictim;
    public Gharacter parental;
    
    public double speed;
    
    public EntityHaunter(Spatial pos, Spatial size, int lifetime, Gharacter parental){
        super(pos, size);
        this.lifetime = lifetime;
        this.x_orientation = parental.x_orientation;
        this.parent = parental;
        this.parental = parental;
        this.victim = null;
        this.damage = .1;
        this.speed = 1;
        this.tangibility = false;
    }
    
    
    @Override
    public void update(){
        if (!this.hasVictim){
            this.position.x += this.speed * this.x_orientation;
        }
        else if (this.ticksExisted() > this.lifetime){
            this.remove();
        } else if (this.hasVictim && ((this.victim == null) || (!this.victim.isExistant()))){
            this.parental.heal(this.parental.max_health * .5);
            ImageBox ib = new ImageBox(parental.position, parental.size, 40, parental.x_orientation);
            ib.setImage("necromancer", "DARKHEAL");
            Game.instance.space.addConstruct(ib);
            this.remove();
        } else if ((this.victim != null) && this.victim.isExistant()){
            this.victim.damage(damage);
            this.x_orientation = victim.x_orientation;
        }
    }
    
    @Override
    public void gharacteract(Gharacter g){
        if (!this.hasVictim){
            if (g.isAlly() != this.isAlly()){
                this.victim = g;
                this.velocity = new Spatial();
                this.position = g.position;
                this.size = g.size;
                this.hasVictim = true;
            }
        }
    }
    
    @Override
    public BufferedImage getImage(){
        return ImageHandler.getPNG("necromancer", "HAUNT-0");
    }
    
}
