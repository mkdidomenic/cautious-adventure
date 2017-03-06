/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constructs;

import utility.Spatial;

/**
 *
 * @author Mike
 */
public class NonPlayerCharacter extends Character {
    
    public NonPlayerCharacter(Spatial position) {
        super(position);
        this.addImages();
    }

    public NonPlayerCharacter(Spatial position, Spatial size) {
        super(position, size);
        this.addImages();
    }
    
    private void addImages(){
        this.addImage("src/res/barbarian.png");
    }
    @Override
    public void update() {
        super.update();

    }

}
