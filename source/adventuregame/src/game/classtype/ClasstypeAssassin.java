/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.classtype;

import game.Game;
import game.constructs.DamageBox;
import game.constructs.StunBox;
import game.constructs.entity.ProjectileNinjaStar;
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
    public int ability1CD = 6;
    public double ability1Cost = 0;
    public int ability1ExecFrame = 4;

    private void initAbility1() {
        if (this.startAbility(this.ability1CD, this.ability1Cost)) {
            this.gharacter.state = Gharacter.State.ABILITY1;
        }
    }

    private void execAbility1() {
        DamageBox db = new DamageBox(
                this.gharacter.position.copy(),
                new Spatial(2, 2, 10));
        db.setParent(this.gharacter);
        db.setDamage(5);
        db.position.x += this.gharacter.x_orientation * (this.gharacter.size.x / 2 + db.size.x / 2 + 0.01);
        //System.out.println(sb);
        Game.instance.space.addConstruct(db);
    }

    private String ability1Image(String filename) {
        // ABILITY1
        if (this.gharacter.state == State.ABILITY1) {
            int f = this.gharacter.actionTimer;
            if (f > 4) {
                f = 0;
            } else if (f > 2) {
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
    public int ability2CD = 12;
    public double ability2Cost = 20;
    public int ability2ExecFrame = 6;

    private void initAbility2() {
        if (this.startAbility(this.ability2CD, this.ability2Cost)) {
            this.gharacter.state = Gharacter.State.ABILITY2;
        }
    }

    private void execAbility2() {
        ProjectileNinjaStar p = new ProjectileNinjaStar(
                this.gharacter.position.copy());
        p.position.x += this.gharacter.x_orientation * (this.gharacter.size.x / 2 + p.size.x / 2 + 1);
        p.velocity.x = this.gharacter.x_orientation * (p.moveSpeed);
        p.position.z += this.gharacter.size.z / 2;
        p.acceleration.z = -1 * p.gravity * (0.95);
        p.setParent(this.gharacter);
        p.setDamage(10);
        //System.out.println(p);
        Game.instance.space.addConstruct(p);
    }

    private String ability2Image(String filename) {
        // ABILITY2
        if (this.gharacter.state == State.ABILITY2) {
            int f = this.gharacter.actionTimer;
            if (f > 9) {
                f = 0;
            } else if (f > 6) {
                f = 1;
            } else if (f > 3) {
                f = 2;
            } else {
                f = 3;
            }
            filename = State.ABILITY2.name() + "-" + (f);
        }
        return filename;
    }

    /**
     * Ability 3
     */
    public int ability3CD = 30;
    public double ability3Cost = 40;
    public int ability3ExecFrame = 1;

    private void initAbility3() {
        if (this.startAbility(this.ability3CD, this.ability3Cost)) {
            this.gharacter.state = Gharacter.State.ABILITY3;
        }
    }

    private void execAbility3() {
        Gharacter target = Game.instance.space.trace(this.gharacter, .1);
        if (target != null) {
            this.gharacter.position.set(target.position);
            this.gharacter.position.x += target.x_orientation * -1 * (this.gharacter.size.x / 2 + target.size.x / 2);
            this.gharacter.x_orientation = target.x_orientation;
            this.execAbility1();
        }
    }

    private String ability3Image(String filename) {
        // ABILITY3
        if (this.gharacter.state == State.ABILITY3) {
            int f = this.gharacter.actionTimer;
            if (f > 20) {
                f = 0;
            } else if (f > 10) {
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
    public int ability4CD = 8;
    public double ability4Cost = 40;
    public int ability4ExecFrame = 6;

    private void initAbility4() {
        if (this.startAbility(this.ability4CD, this.ability4Cost)) {
            this.gharacter.state = Gharacter.State.ABILITY4;
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
