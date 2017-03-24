/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.classtype;

import game.Game;
import game.constructs.ImageBox;
import game.constructs.StunBox;
import game.constructs.entity.character.Gharacter;
import game.constructs.entity.character.Gharacter.State;
import game.constructs.entity.character.NonPlayerCharacter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.GController;
import utility.ImageHandler;
import utility.Spatial;

/**
 *
 * @author Mike
 */
public class ClasstypeNecromancer extends Classtype {

    ArrayList<Gharacter> minions;

    public ClasstypeNecromancer(Gharacter g) {
        super(g);
        this.minions = new ArrayList();
    }

    @Override
    public void setupAttributes() {
        this.gharacter.normal_move_speed = 1.0;
        this.gharacter.max_health = 75;
        this.gharacter.setAttr();

        this.gharacter.mobileStates.add(State.ABILITY1);
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
            return ImageHandler.getPNG("necromancer", filename);
        } catch (Exception e) {
            return ImageHandler.getPNG("necromancer", State.IDLE.name());
        }
    }

    public boolean hasMaxMinions() {
        int maxMinions = 3;
        return this.minions.size() == maxMinions;
    }

    @Override
    public void update() {
        super.update();
        handleAbilities();
        handleAbilityTimers();
        handleMinions();

    }

    private void handleMinions() {
        removeDeadMinions();
    }

    private void removeDeadMinions() {
        ArrayList<Gharacter> toRemove = new ArrayList();
        for (Gharacter g : ((ArrayList<Gharacter>) this.minions.clone())) {
            if (!(g.isExistant())) {
                toRemove.add(g);
            }
        }
        for (Gharacter g : toRemove) {
            if (!(g.isExistant())) {
                this.minions.remove(g);
            }
        }
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
        } else if ((i == 1)){
            this.ability1Update();
        }
    }

    private boolean startAbility(int cooldown, double cost) {
        return (this.gharacter.setActionTimer(cooldown) && this.gharacter.useEnergy(
                cost));
    }

    private void handleAbilityTimers() {
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
                if (this.ability1ATimer == this.ability1ExecFrame) {
                    this.execAbility1();
                }
                break;
            case ABILITY2:
                if (this.gharacter.actionTimer == this.ability2ExecFrame) {
                    this.execAbility2();
                }
                break;
            case ABILITY3:
                if (this.gharacter.actionTimer == this.ability3ExecFrame) {
                    this.execAbility3();
                }
                break;
            case ABILITY4:
                if (this.gharacter.actionTimer == this.ability4ExecFrame) {
                    this.execAbility4();
                }
                break;
        }

    }

    /**
     * Ability 1
     */
    public int ability1AT = 2;
    public int ability1ExecFrame = 0;
    public int ability1ATime = 18;
    public int ability1ATimer = 0;
    
    private void ability1Update(){
        if (this.ability1ATimer > 0){
            this.ability1ATimer--;
        } 
        this.gharacter.interruptActionTimer();
        this.gharacter.setActionTimer(this.ability1AT);
        this.gharacter.state = Gharacter.State.ABILITY1;
        
    }

    private void initAbility1() {
        if (this.gharacter.state != Gharacter.State.ABILITY1){
            if (this.gharacter.setActionTimer(ability1AT) && (this.ability1ATimer == 0)) {
                this.gharacter.state = Gharacter.State.ABILITY1;
                this.ability1ATimer = this.ability1ATime;

            }
        }
    }

    private void execAbility1() {
        System.out.println("go");
    }

    private String ability1Image(String filename) {
        // ABILITY1
        if (this.gharacter.state == State.ABILITY1) {
            int f = this.ability1ATimer;
            if (f > 12) {
                f = 0;
            } else if (f > 6) {
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
    public int ability2AT = 24;
    public double ability2Cost = 20;
    public int ability2ExecFrame = 2;
    public int ability2CD = 2 + ability2AT;
    public int ability2CDTimer = 0;

    public int ability2SpawnDistance = 10;

    private void initAbility2() {
        if ((this.ability2CDTimer == 0) && this.gharacter.isGrounded() && !(this.hasMaxMinions())) {
            if (this.startAbility(this.ability2AT, this.ability2Cost)) {
                this.gharacter.state = Gharacter.State.ABILITY2;
                this.ability2CDTimer = this.ability2CD;
            }
        }
    }

    private void execAbility2() {
        NonPlayerCharacter g = new NonPlayerCharacter(
                this.gharacter.position.copy());
        g.position.x += this.gharacter.x_orientation * ability2SpawnDistance;
        g.setParent(this.gharacter);
        //g.ally = true;
        g.setClasstype(new ClasstypeDummySkeleton(g));
        //g.setAI(new AISimple(g));

        Game.instance.space.addConstruct(g);
        this.minions.add(g);
    }

    private String ability2Image(String filename) {
        // ABILITY2
        if (this.gharacter.state == State.ABILITY2) {
            ImageBox ib = new ImageBox(this.gharacter.position.copy(),
                                       this.gharacter.size.copy(), 1,
                                       this.gharacter.x_orientation);
            ib.position.x += this.gharacter.x_orientation * ability2SpawnDistance;
            int f = this.gharacter.actionTimer;
            if (f > (this.ability2AT * (3.0 / 4.0))) {
                f = 0;
            } else if (f > (this.ability2AT * (2.0 / 4.0))) {
                f = 1;
            } else if (f > (this.ability2AT * (1.0 / 4.0))) {
                f = 2;
            } else {
                f = 3;
            }
            ib.setImage("necromancer", "SPAWN-" + f);
            Game.instance.space.addConstruct(ib);
            filename = State.ABILITY2.name() + "-" + (f);
        }
        return filename;
    }

    /**
     * Ability 3
     */
    public int ability3AT = 20;
    public double ability3Cost = 40;
    public int ability3ExecFrame = 2;
    public int ability3CD = 30 + ability3AT;
    public int ability3CDTimer = 0;

    public double ability3Damage = 15;

    private void initAbility3() {
        if (this.ability3CDTimer == 0) {
            if (this.startAbility(this.ability3AT, this.ability3Cost)) {
                this.gharacter.state = Gharacter.State.ABILITY3;
                this.ability3CDTimer = this.ability3CD;
            }
        }
    }

    private void execAbility3() {
        Gharacter target = Game.instance.space.trace(this.gharacter,
                                                     Gharacter.DEFAULT_SIZE.x / 2);
        if (target != null) {
            this.gharacter.position.set(target.position);
            this.gharacter.position.x += target.x_orientation * -1 * (this.gharacter.size.x / 2 + target.size.x / 2);
            this.gharacter.x_orientation = target.x_orientation;
        }
    }

    private String ability3Image(String filename) {
        // ABILITY3
        if (this.gharacter.state == State.ABILITY3) {
            int f = this.gharacter.actionTimer;
            if (f > 15) {
                f = 0;
            } else if (f > 9) {
                f = 1;
            } else if (f > 5) {
                f = 2;
            } else {
                f = 3;
            }
            filename = State.ABILITY3.name() + "-" + (f);
        }
        return filename;
    }

    /**
     * Ability 4
     */
    public int ability4AT = 8;
    public double ability4Cost = 40;
    public int ability4ExecFrame = 6;
    public int ability4CD = 60 + ability4AT;
    public int ability4CDTimer = 0;

    private void initAbility4() {
        if (this.ability4CDTimer == 0) {
            if (this.startAbility(this.ability4AT, this.ability4Cost)) {
                this.gharacter.state = Gharacter.State.ABILITY4;
                this.ability4CDTimer = this.ability4CD;
            }
        }
    }

    private void execAbility4() {
        StunBox sb = new StunBox(
                this.gharacter.position.copy(),
                new Spatial(4, 2, 10));
        sb.setParent(this.gharacter);
        sb.setStun(30);
        sb.setDamage(2);
        sb.position.x += this.gharacter.x_orientation * (this.gharacter.size.x / 2 + sb.size.x / 2 + 0.01);
        //System.out.println(sb);
        Game.instance.space.addConstruct(sb);
    }

    private String ability4Image(String filename) {
        // ABILITY4
        if (this.gharacter.state == State.ABILITY4) {
            int f = this.gharacter.actionTimer;
            if (f > 6) {
                f = 0;
            } else if (f > 4) {
                f = 1;
            } else {
                f = 2;
            }
            filename = State.ABILITY4.name() + "-" + (f);
        }
        return filename;
    }

}
