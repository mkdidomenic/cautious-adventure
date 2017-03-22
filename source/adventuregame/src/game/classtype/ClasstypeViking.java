/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.classtype;

import game.Game;
import game.constructs.DamageBox;
import game.constructs.ImageBox;
import game.constructs.KnockDownBox;
import game.constructs.StunBox;
import game.constructs.entity.character.Gharacter;
import game.constructs.entity.character.Gharacter.State;
import java.awt.image.BufferedImage;
import java.util.Random;
import main.GController;
import utility.ImageHandler;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class ClasstypeViking extends Classtype {

    public ClasstypeViking(Gharacter g) {
        super(g);
    }

    @Override
    public void setupAttributes() {
        this.gharacter.normal_move_speed = .75;
        this.gharacter.max_health = 125;
        this.gharacter.mobileStates.add(State.ABILITY2);
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
            filename = State.MOVING.name() + "-" + (GController.instance.getCurrentFrame() % 3);
        }

        // abilities
        filename = ability1Image(filename);

        filename = ability2Image(filename);

        filename = ability3Image(filename);

        filename = ability4Image(filename);

        // STUNNED
        if (this.gharacter.state == State.STUNNED) {
            // mod number of moving images
            filename = State.STUNNED.name() + "-" + (GController.instance.getCurrentFrame() % 3);
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
            return ImageHandler.getPNG("viking", filename);
        } catch (Exception e) {
            return ImageHandler.getPNG("viking", State.IDLE.name());
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
        } else if (i == 2) {
            this.initAbility2();
        }
    }

    private boolean startAbility(int frames, double cost) {
        return (this.gharacter.setActionTimer(frames) && this.gharacter.useEnergy(
                cost));
    }

    private boolean continueAbility(State ability, int frames, double cost) {
        if (this.gharacter.state == ability) {
            this.gharacter.interruptActionTimer();
            this.gharacter.setActionTimer(frames);
            return this.gharacter.useEnergy(cost);
        } else {
            return false;
        }

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
        this.resetAbilityEffects();
        //System.out.println(this.gharacter.actionTimer);
        switch (gharacter.state) {
            case ABILITY1:
                if (this.gharacter.actionTimer == this.ability1ExecFrame) {
                    this.execAbility1();
                }
                break;
            case ABILITY2:
                this.execAbility2();
                break;
            case ABILITY3:
                this.execAbility3();
                break;
            case ABILITY4:
                this.execAbility4();
                break;
        }

    }

    private void resetAbilityEffects() {
        // ability 2
        this.resetAbility2Effects();
    }

    /**
     * Ability 1
     */
    public int ability1AT = 15;
    public double ability1Cost = 0;
    public int ability1ExecFrame = 9;
    public int ability1CD = 4 + ability1AT;
    public int ability1CDTimer = 0;

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
                new Spatial(2, 1, 10));
        db.setParent(this.gharacter);
        db.setDamage(10);
        db.position.x += this.gharacter.x_orientation * (this.gharacter.size.x / 2 + db.size.x / 2 + 0.01);
        ImageBox ib = new ImageBox(db.position, db.size, 4,
                                   this.gharacter.x_orientation);
        ib.setImage("viking", "PUNCH");
        Game.instance.space.addConstruct(db);
        Game.instance.space.addConstruct(ib);
    }

    private String ability1Image(String filename) {
        // ABILITY1
        if (this.gharacter.state == State.ABILITY1) {
            int f = this.gharacter.actionTimer;
            if (f > 12) {
                f = 0;
            } else if (f > 8) {
                f = 1;
            } else {
                f = 2;
            }
            filename = State.ABILITY1.name() + "-" + (f);
        }
        return filename;
    }

    /**
     * Ability 2
     */
    public int ability2AT = 2;
    public double ability2Cost = 0;
    public int ability2ExecFrame = 2;
    public int ability2CD = 0;
    public int ability2CDTimer = 0;

    private void initAbility2() {
        if (this.ability2CDTimer == 0) {
            if (this.startAbility(this.ability2AT, this.ability2Cost)) {
                this.gharacter.state = Gharacter.State.ABILITY2;
                this.ability2CDTimer = this.ability2CD;
            } else if (this.continueAbility(State.ABILITY2,
                                            this.ability2AT,
                                            this.ability2Cost)) {

                //this.ability2CDTimer = this.ability2CD;
            }
        }
    }

    private void execAbility2() {
        this.gharacter.sheilded = true;
        this.gharacter.orientationCanChange = false;
        this.gharacter.move_speed *= 0.25;
    }

    private String ability2Image(String filename) {
        // ABILITY2
        if (this.gharacter.state == State.ABILITY2) {
            int f = 0;
            if (this.gharacter.getFramesSinceLastMovement() > 2) {
                f = 0;
            } else if (this.gharacter.isGrounded()) {
                f = (int) this.gharacter.ticksExisted() % 3;
            }
            filename = State.ABILITY2.name() + "-" + (f);
        }
        return filename;
    }

    private void resetAbility2Effects() {
        this.gharacter.sheilded = false;
        this.gharacter.orientationCanChange = true;
        this.gharacter.move_speed = this.gharacter.normal_move_speed;
    }

    /**
     * Ability 3
     */
    public int ability3AT = 2;
    public double ability3Cost = 40;
    public int ability3ExecFrame = 0;
    public int ability3CD = 60 + ability3AT;
    public int ability3CDTimer = 0;

    private void initAbility3() {
        if (this.ability3CDTimer == 0) {
            if (this.startAbility(this.ability3AT, this.ability3Cost)) {
                this.gharacter.state = Gharacter.State.ABILITY3;
                this.ability3CDTimer = this.ability3CD;
                if (this.gharacter.isGrounded()) {
                    this.gharacter.velocity.z = this.gharacter.jump_velocity * 1.2;
                    this.gharacter.position.z += (this.gharacter.size.z * 0.01);
                }
            }
        }
    }

    private void execAbility3() {
        if (this.gharacter.isGrounded()) {
            Spatial sbsize = new Spatial(this.gharacter.size.x * 1.25,
                                         this.gharacter.size.y * 1.25,
                                         this.gharacter.size.z * 0.25);
            StunBox sb = new StunBox(this.gharacter.position.copy(),
                                     sbsize);
            sb.position.y = sb.position.y - (sb.size.y / 4);
            sb.setParent(this.gharacter);
            sb.setDamage(10);
            sb.setStun(40);
            ImageBox ib = new ImageBox(sb.position, sb.size, 60,
                                       this.gharacter.x_orientation);
            ib.setImage("viking", "GROUNDSLAM");
            Game.instance.space.addConstruct(sb);
            Game.instance.space.addConstruct(ib);

        } else {
            this.continueAbility(State.ABILITY3, ability3AT, 0);
        }

    }

    private String ability3Image(String filename) {
        // ABILITY3
        if (this.gharacter.state == State.ABILITY3) {
            int f = 0;
            filename = State.JUMPING.name();
        }
        return filename;
    }

    /**
     * Ability 4
     */
    public int ability4AT = 80;
    public double ability4Cost = 80;
    public int ability4ExecFrame = 5;
    public int ability4CD = 180 + ability4AT;
    public int ability4CDTimer = 0;

    public double ability4defaultdistance = 20;
    public Spatial ability4defaultsize = new Spatial(20,4,20);
    public double ability4cloudheight = 60;
    public int ability4strikeframes = 10;

    private void initAbility4() {
        if (this.ability4CDTimer == 0) {
            if (this.startAbility(this.ability4AT, this.ability4Cost)) {
                this.gharacter.state = Gharacter.State.ABILITY4;
                this.ability4CDTimer = this.ability4CD;
            }
        }
    }

    private void execAbility4() {
        int f = this.gharacter.actionTimer;
        if (f == (ability4AT * 11 / 12)) {
            //System.out.println("1");
        } else if ((f < (ability4AT * 9 / 12)) && (f > (ability4AT * 1 / 12))) {
            Spatial skypos = this.gharacter.position.copy();
            Spatial cloudsize = new Spatial(16, 1, 16);
            skypos.z = skypos.z + ability4cloudheight;
            ImageBox ib = new ImageBox(skypos, cloudsize, 1,
                                       this.gharacter.x_orientation);
            ib.setImage("viking",
                        "THUNDER-" + GController.instance.getCurrentFrame() % 3);
            
            Spatial skypos2 = skypos.copy();
            skypos2.x += ability4defaultdistance * this.gharacter.x_orientation;
            ImageBox ib2 = new ImageBox(skypos2, ability4defaultsize.copy(), 2, Game.instance.random.nextInt() % 2);
            ib2.setImage("viking", "THUNDER-" + Integer.toString( ((int)Math.abs((f/12) - 4)) / 2) );
            Game.instance.space.addConstruct(ib);
            Game.instance.space.addConstruct(ib2);
            
        } else if (f == (ability4AT * 1 / 12)) {
            Spatial skypos = this.gharacter.position.copy();
            skypos.z = skypos.z + ability4cloudheight;
            skypos.x += ability4defaultdistance * this.gharacter.x_orientation;
            ImageBox ib = new ImageBox(skypos, ability4defaultsize.copy(), ability4strikeframes, this.gharacter.x_orientation);
            ib.setImage("viking", "THUNDER-2" );
            
            Spatial boltpos = skypos.copy();
            Spatial boltsize = ability4defaultsize.copy();
            boltsize.z = skypos.copy().z;
            boltpos.z = 0;     
            ImageBox boltIb = new ImageBox(boltpos, boltsize, ability4strikeframes, this.gharacter.x_orientation);
            boltIb.setImage("viking", "LIGHTNING");
            boltsize = boltsize.copy();
            boltsize.y *= 2;
            KnockDownBox db = new KnockDownBox(boltpos, boltsize);
            db.setDamage(50);
            db.setKnockDown(15);
            
            Game.instance.space.addConstruct(ib);
            Game.instance.space.addConstruct(boltIb);
            Game.instance.space.addConstruct(db);
            
        } else if (f == 1) {
            System.out.println("BAM");
        }
    }

    private String ability4Image(String filename) {
        // ABILITY4
        if (this.gharacter.state == State.ABILITY4) {
            int f = this.gharacter.actionTimer;
            if (f > (ability4AT * 11 / 12)) {
                f = 0;
            } else if (f > (ability4AT * 10 / 12)) {
                f = 1;
            } else if (f > (ability4AT * 9 / 12)) {
                f = 2;
            } else if (f > (ability4AT * 1 / 12)) {
                f = 3;
            } else {
                f = 4;
            }
            filename = State.ABILITY4.name() + "-" + (f);
        }
        return filename;
    }

}
