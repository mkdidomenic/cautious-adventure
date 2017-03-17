/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.classtype;

import game.Game;
import game.constructs.entity.ProjectileArrow;
import game.constructs.entity.character.Gharacter;
import java.awt.image.BufferedImage;
import utility.ImageHandler;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class ClasstypeAssassin extends Classtype {

    public ClasstypeAssassin(Gharacter g) {
        super(g);
    }

    @Override
    public BufferedImage getImage() {
        return ImageHandler.getPNG("knight");
    }

    @Override
    public void ability(int i) {
        switch (i) {
            case 1:
                this.ability1();
                break;
            case 2:
                this.ability2();
                break;
        }
    }

    private void ability1() {
        if (this.gharacter.setActionTimer(10)) {
            this.gharacter.state = Gharacter.State.ABILITY1;
        }
    }

    private void ability2() {
        if (this.gharacter.setActionTimer(12)) {
            ProjectileArrow p = new ProjectileArrow(
                    this.gharacter.position.copy(),
                    new Spatial(4, 2, 2));
            p.position.x += this.gharacter.x_orientation * (this.gharacter.size.x / 2 + p.size.x / 2 + 1);
            p.velocity.x = this.gharacter.x_orientation * (2);
            p.position.z += this.gharacter.size.z / 2;
            p.acceleration.z = 0.19;
            p.setParent(this.gharacter);
            //System.out.println(p);
            Game.instance.space.addConstruct(p);
            this.gharacter.state = Gharacter.State.ABILITY2;
        }
    }

}
