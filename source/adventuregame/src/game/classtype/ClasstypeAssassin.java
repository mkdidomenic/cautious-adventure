/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.classtype;

import game.Game;
import game.constructs.DamageBox;
import game.constructs.entity.ProjectileArrow;
import game.constructs.entity.character.Gharacter;
import game.constructs.entity.character.Gharacter.State;
import java.awt.image.BufferedImage;
import main.GController;
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
    public void setupAttributes() {
        this.gharacter.normal_move_speed = 1.2;
        this.gharacter.max_health = 90;
    }

    @Override
    public BufferedImage getImage() {
        // IDLE
        String filename = State.IDLE.name();

        // JUMPING
        if (this.gharacter.state == State.JUMPING) {
            filename = State.JUMPING.name();
        }

        // MOVING
        if (this.gharacter.state == State.MOVING) {
            // mod number of moving images
            filename = State.MOVING.name() + "-" + (GController.instance.getCurrentFrame() % 3);
        }

        // ABILITY1
        if (this.gharacter.state == State.ABILITY1) {
            int f = this.gharacter.actionTimer;
            if (f > 8) {
                f = 0;
            } else if (f > 4) {
                f = 1;
            } else {
                f = 2;
            }
            filename = State.ABILITY1.name() + "-" + (f);
        }

        try {
            return ImageHandler.getPNG("assassin", filename);
        } catch (Exception e) {
            return ImageHandler.getPNG("assassin", State.IDLE.name());
        }
    }

    @Override
    public void update() {
        super.update();
        handleAbilities();

    }

    @Override
    public void ability(int i) {
        if (this.gharacter.canAct()) {
            switch (i) {
                case 1:
                    this.initAbility1();
                    break;
                case 2:
                    this.initAbility2();
                    break;
            }
        }
    }

    private boolean startAbility(int cooldown, double cost) {
        return (this.gharacter.setActionTimer(cooldown) && this.gharacter.useEnergy(
                cost));
    }

    private void handleAbilities() {
        switch (gharacter.state) {
            case ABILITY1:
                break;
            case ABILITY2:
                break;
        }

    }

    public int ability1CD = 5;
    public double ability1Cost = 0;

    private void initAbility1() {
        if (this.startAbility(this.ability1CD, this.ability1Cost)) {
            DamageBox db = new DamageBox(
                    this.gharacter.position.copy(),
                    new Spatial(4, 2, 10));
            db.setParent(this.gharacter);
            db.setDamage(5);
            db.position.x += this.gharacter.x_orientation * (this.gharacter.size.x / 2 + db.size.x / 2 + 0.01);
            //System.out.println(db);
            Game.instance.space.addConstruct(db);
            this.gharacter.state = Gharacter.State.ABILITY1;
        }
    }

    public int ability2CD = 12;
    public double ability2Cost = 20;

    private void initAbility2() {
        if (this.startAbility(this.ability2CD, this.ability2Cost)) {
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

    private void initAbility3() {

    }

    private void initAbility4() {

    }

}
