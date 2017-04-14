/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import game.Game;
import game.constructs.entity.character.PlayerCharacter;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JViewport;
import main.GController;

/**
 *
 * @author Mike
 */
public class GameView extends JFrame {

    public JPanel contentPanel;
    public SpacePanel spacePanel;
    public HUDPanel hudPanel;
    public JViewport viewport;

    public Game game;
    private int width;
    private int height;

    public GameView(Game game) {
        this.game = game;
        this.width = 600;
        this.height = 600;
        this.setSize(this.width, this.height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        int w;
        int h;
        // main content panel
        this.contentPanel = new JPanel();
        this.contentPanel.setVisible(true);
        this.contentPanel.setSize(this.width, this.height);
        this.add(this.contentPanel);

        this.contentPanel.setLayout(new BoxLayout(this.contentPanel,
                                                  BoxLayout.Y_AXIS));
        // subcomponents
        //HUD subcomponent
        w = (int) (this.width * .95);
        h = (int) (this.height * 0.1);
        this.hudPanel = new HUDPanel();
        this.contentPanel.add(this.hudPanel);
        this.hudPanel.setSize(w, h);
        this.hudPanel.setPreferredSize(new Dimension(w, h));
        this.hudPanel.setScale();
        this.hudPanel.update();
        this.hudPanel.setVisible(true);

        // viewport subcomponent
        this.viewport = new JViewport();

        //this.viewport.setView(this.spacePanel);
        this.viewport.setSize(w, h);
        this.viewport.setPreferredSize(new Dimension(w, h));
        this.viewport.setViewPosition(new Point(0, 0));
        this.viewport.setVisible(true);
        this.viewport.setOpaque(true);
        this.viewport.setScrollMode(
                JViewport.SIMPLE_SCROLL_MODE);
        this.contentPanel.add(this.viewport);

        // spacepanel subcomponent
        w = w * 2;
        h = h * 2;
        this.spacePanel = new SpacePanel();
        //this.contentPanel.add(this.spacePanel);
        this.spacePanel.setSize(w, h);
        this.spacePanel.setPreferredSize(new Dimension(w, h));
        this.spacePanel.setScale();
        this.spacePanel.setVisible(true);
        //this.contentPanel.add(this.spacePanel);
        this.viewport.setView(this.spacePanel);
    }
    
    public void setGame(Game game){
        this.game = game;
    }

    public synchronized void update() {
        if ((this.width != this.getWidth()) && (this.height != this.getHeight())) {
            this.width = this.getWidth();
            this.height = this.getHeight();
//            this.content.setSize(this.width, this.height);
            int w = (int) (this.width * 0.95);
            int h = (int) (this.height * 0.1);
            this.hudPanel.setPreferredSize(new Dimension(w, h));
            h = (int) (this.height * 0.9);
            this.viewport.setPreferredSize(new Dimension(w, h));
            w = (int) (w * 1.5);
            h = (int) (h * 1.0);
            this.spacePanel.setPreferredSize(new Dimension(w, h));
            this.hudPanel.setScale();
            this.spacePanel.setScale();
            //this.panel.setLocation(0, (int) (this.height * 0.1));
        }
        this.hudPanel.update();
        this.updateViewport();
        this.revalidate();
        this.repaint();
    }

    public synchronized void updateViewport() {
        int scrollAmount = 4;
        int x0;
        int y0;
        if (this.game == null) {
            return;
        }
        y0 = (this.spacePanel.getHeight() - this.viewport.getHeight()) / 2;

        if (this.game.player == null) {
            x0 = (this.spacePanel.getWidth() - this.viewport.getWidth()) / 2;

        } else {
            PlayerCharacter pc = this.game.space.getPlayerCharacter(
                    GController.instance.localID);
            if (pc != null) {
                x0 = this.spacePanel.mapX(pc.position.x);
                x0 = x0 - this.viewport.getWidth() / 2;
                if (Math.abs(x0 - this.viewport.getViewPosition().x) <= scrollAmount) {
                    x0 = this.viewport.getViewPosition().x;
                } else if (x0 > this.viewport.getViewPosition().x) {
                    x0 = this.viewport.getViewPosition().x + scrollAmount;
                } else if (x0 < this.viewport.getViewPosition().x) {
                    x0 = this.viewport.getViewPosition().x - scrollAmount;
                } else {
                    x0 = this.viewport.getViewPosition().x;
                }
            } else {
                x0 = (this.spacePanel.getWidth() - this.viewport.getWidth()) / 2;
            }
        }
        if (x0 < 0) {
            x0 = 0;
        }
        if ((x0 + this.viewport.getWidth()) > (this.spacePanel.getWidth())) {
            x0 = (this.spacePanel.getWidth() - this.viewport.getWidth());
        }
        this.viewport.setViewPosition(new Point(x0, y0));

    }

}
