/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import game.Game;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Mike
 */
public class View extends JFrame {

    public JPanel content;
    public SpacePanel panel;
    public HUDPanel hud;

    public Game game;
    private int width;
    private int height;

    public View(Game game) {
        this.width = 600;
        this.height = 600;
        this.setSize(this.width, this.height);
        this.setVisible(true);

        this.content = new JPanel();
        this.content.setVisible(true);
        this.content.setSize(this.width, this.height);
        this.add(this.content);

        this.content.setLayout(new BoxLayout(this.content, BoxLayout.Y_AXIS));
        // subcomponents
        //HUD subcomponent
        this.hud = new HUDPanel();
        this.content.add(this.hud);
        this.hud.setSize((int) (this.width * .95),
                         (int) (this.height * 0.1));
        this.hud.setPreferredSize(new Dimension((int) (this.width * .95),
                                                (int) (this.height * 0.1)));
        this.hud.setScale();
        this.hud.update();
        this.hud.setVisible(true);

        // spacepanel subcomponent
        this.panel = new SpacePanel(game.space);
        this.content.add(this.panel);
        this.panel.setSize((int) (this.width * .95),
                           (int) (this.height * 0.9));
        this.panel.setPreferredSize(new Dimension((int) (this.getWidth() * .95),
                                                  (int) (this.getHeight() * 0.9)));
        this.panel.setScale();
        this.panel.setVisible(true);
    }

    public void update() {
        if ((this.width != this.getWidth()) && (this.height != this.getHeight())) {
            this.width = this.getWidth();
            this.height = this.getHeight();
//            this.content.setSize(this.width, this.height);
            this.hud.setPreferredSize(new Dimension((int) (this.width * .95),
                                                    (int) (this.height * 0.1)));
            this.panel.setPreferredSize(new Dimension((int) (this.width * 0.95),
                                                      (int) (this.height * 0.9)));
            this.hud.setScale();
            this.panel.setScale();
            //this.panel.setLocation(0, (int) (this.height * 0.1));
        }
        this.hud.update();
    }

}
