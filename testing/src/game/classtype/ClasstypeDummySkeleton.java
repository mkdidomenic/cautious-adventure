/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.classtype;

import game.Game;
import game.constructs.DamageBox;
import game.constructs.ImageBox;
import game.constructs.entity.character.Gharacter;
import game.constructs.entity.character.Gharacter.State;
import java.awt.image.BufferedImage;
import utility.ImageHandler;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class ClasstypeDummySkeleton extends Classtype {

    public ClasstypeDummySkeleton(Gharacter g) {
        super(g);
    }

    @Override
    public void setupAttributes() {
        this.gharacter.normal_move_speed = 1.0;
        this.gharacter.max_health = 90;
        this.gharacter.hitstunFrames = 2;
        this.gharacter.setAttr();

        //this.gharacter.mobileStates.add(State.ABILITY2);
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
            filename = State.MOVING.name() + "-" + (Game.instance.getCurrentFrame() % 3);
        }

        // abilities
        filename = ability1Image(filename);

        filename = ability2Image(filename);

        // STUNNED
        if (this.gharacter.state == State.STUNNED) {
            // mod number of moving images
            filename = State.STUNNED.name() + "-" + (Game.instance.getCurrentFrame() % 3);
        }
        // HITSTUNNED
        if (this.gharacter.state == State.HITSTUNNED) {
            // mod number of moving images
            filename = State.HITSTUNNED.name();
        }
        // FALLEN
        if (this.gharacter.state == State.FALLEN) {
            // mod number of moving images
            filename = State.FALLEN.name();
        }

        try {
            return ImageHandler.getPNG("dummyskeleton", filename);
        } catch (Exception e) {
            return ImageHandler.getPNG("dummyskeleton", State.IDLE.name());
        }
    }

    @Override
    public void update() {
        super.update();
        handleAbilities();
        handleAbilityTimers();

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
                case 3:
                    this.initAbility3();
                    break;
                case 4:
                    this.initAbility4();
                    break;
            }
        }
    }

    private boolean startAbility(int cooldown, double cost) {
        return (this.gharacter.setActionTimer(cooldown) && this.gharacter.useEnergy(
                cost));
    }

    private void handleAbilityTimers() {
        if (this.ability1CDTimer > 0) {
            this.ability1CDTimer--;
        }
        if (this.ability2CDTimer > 0) {
            this.ability2CDTimer--;
        }
        if (this.ability3CDTimer > 0) {
            this.ability3CDTimer--;
        }
        if (this.ability4CDTimer > 0) {
            this.ability4CDTimer--;
        }
    }

    private void handleAbilities() {
        switch (gharacter.state) {
            case ABILITY1:
                if (this.gharacter.actionTimer == this.ability1ExecFrame) {
                    this.execAbility1();
                }
                break;
            case ABILITY2:
                if (this.gharacter.actionTimer == this.ability2ExecFrame) {
                    this.execAbility2();
                }
                break;
        }
        resetAbility3();
        resetAbility4();

    }

    /**
     * Ability 1
     */
    public int ability1AT = 12;
    public double ability1Cost = 0;
    public int ability1ExecFrame = 4;
    public int ability1CD = 4 + ability1AT;
    public int ability1CDTimer = 0;
    public int ability1Damage = 10;

    private void initAbility1() {
        if (this.ability1CDTimer == 0) {
            if (this.startAbility(this.ability1AT, this.ability1Cost)) {
                this.gharacter.state = Gharacter.State.ABILITY1;
                this.ability1CDTimer = this.ability1CD;
            }
        }
    }

    private void execAbility1() {
        DamageBox db = new DamageBox(
                this.gharacter.position.copy(),
                new Spatial(1, 1, 10));
        db.setParent(this.gharacter);
        db.setDamage(this.ability1Damage);
        db.position.x += this.gharacter.x_orientation * (this.gharacter.size.x / 2 + db.size.x / 2 + 0.01);
        //System.out.println(sb);
        ImageBox ib = new ImageBox(db.position, db.size, 4,
                                   this.gharacter.x_orientation);
        ib.setImage("dummyskeleton", "SWIPE");
        Game.instance.space.addConstruct(db);
        Game.instance.space.addConstruct(ib);
    }

    private String ability1Image(String filename) {
        // ABILITY1
        if (this.gharacter.state == State.ABILITY1) {
            int f = this.gharacter.actionTimer;
            if (f > 8) {
                f = 0;
            } else if (f > ability1ExecFrame) {
                f = 1;
            } else {
                f = 2;
            }
            filename = State.ABILITY1.name() + "-" + (f);
        }
        return filename;
    }

    public int ability2AT = 24;
    public double ability2Cost = 0;
    public int ability2ExecFrame = 4;
    public int ability2CD = 12 + ability2AT;
    public int ability2CDTimer = 0;
    public int ability2Damage = 20;

    private void initAbility2() {
        if (this.ability2CDTimer == 0) {
            if (this.startAbility(this.ability2AT, this.ability2Cost)) {
                this.gharacter.state = Gharacter.State.ABILITY2;
                this.ability2CDTimer = this.ability2CD;
            }
        }
    }

    private void execAbility2() {
        DamageBox db = new DamageBox(
                this.gharacter.position.copy(),
                new Spatial(1, 1, 10));
        db.setParent(this.gharacter);
        db.setDamage(this.ability2Damage);
        db.position.x += this.gharacter.x_orientation * (this.gharacter.size.x / 2 + db.size.x / 2 + 0.01);
        //System.out.println(sb);
        ImageBox ib = new ImageBox(db.position, db.size, 4,
                                   this.gharacter.x_orientation);
        ib.setImage("dummyskeleton", "SWIPE");
        Game.instance.space.addConstruct(db);
        Game.instance.space.addConstruct(ib);
    }

    private String ability2Image(String filename) {
        // ABILITY1
        if (this.gharacter.state == State.ABILITY2) {
            int f = this.gharacter.actionTimer;
            if (f > 10) {
                f = 0;
            } else if (f > 4) {
                f = 1;
            } else {
                f = 2;
            }
            filename = State.ABILITY2.name() + "-" + (f);
        }
        return filename;
    }

    /**
     * Ability 3
     */
    public int ability3AT = 90;
    public double ability3Cost = 30;
    public int ability3CD = 60 + ability3AT;
    public int ability3CDTimer = 0;

    public int ability3ATimer = 0;

    private void initAbility3() {
        if ((this.ability3CDTimer == 0) && (ability3ATimer == 0)) {
            if (this.startAbility(0, this.ability3Cost)) {
                this.gharacter.state = Gharacter.State.ABILITY3;
                this.ability3CDTimer = this.ability3CD;
                this.ability3ATimer = this.ability3AT;
                this.gharacter.hitstunFrames = 0;
                ImageBox ib = new ImageBox(this.gharacter.position,
                                           this.gharacter.size.copy(),
                                           this.ability3AT, 1);
                ib.setParent(this.gharacter);
                ib.setImage("dummyskeleton", "RAGE-1");
                Game.instance.space.addConstruct(ib);
            }
        }
    }

    private void resetAbility3() {
        if (this.ability3ATimer > 0) {
            ability3ATimer--;
        }
        if (this.ability3ATimer == 1) {
            this.gharacter.hitstunFrames = this.gharacter.default_hitstun_frames;
        }
    }

    /**
     * Ability 4
     */
    public int ability4AT = 90;
    public double ability4Cost = 100;
    public int ability4CD = 20 + ability4AT;
    public int ability4CDTimer = 0;

    public int ability4ATimer = 0;

    private void initAbility4() {
        if ((this.ability4CDTimer == 0) && (ability4ATimer == 0)) {
            if (this.startAbility(0, this.ability4Cost)) {
                this.gharacter.state = Gharacter.State.ABILITY4;
                this.ability4CDTimer = this.ability4CD;
                this.ability4ATimer = this.ability4AT;
                this.gharacter.immune = true;
                ImageBox ib = new ImageBox(this.gharacter.position,
                                           this.gharacter.size.copy(),
                                           this.ability4AT, 1);
                ib.setImage("dummyskeleton", "RAGE-2");
                ib.setParent(this.gharacter);
                Game.instance.space.addConstruct(ib);
            }
        }
    }

    private void resetAbility4() {
        if (this.ability4ATimer > 0) {
            ability4ATimer--;
        }
        if (this.ability4ATimer == 1) {
            this.gharacter.immune = false;
        }
    }

}
