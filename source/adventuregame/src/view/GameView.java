/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import game.Game;
import game.Player;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JViewport;

/**
 *
 * @author Mike
 */
public class GameView extends JFrame {

    public JPanel contentPanel;
    public SpacePanel spacePanel;
    public HUDPanel hudPanel;
    public JViewport viewportPanel;

    public Game game;
    private int width;
    private int height;
    public Player target;

    public GameView(Game game) {
        this.width = 600;
        this.height = 600;
        this.setSize(this.width, this.height);
        this.setVisible(true);
        
        this.target = null;

        int w;
        int h;
        // main content panel
        this.contentPanel = new JPanel();
        this.contentPanel.setVisible(true);
        this.contentPanel.setSize(this.width, this.height);
        this.add(this.contentPanel);

        this.contentPanel.setLayout(new BoxLayout(this.contentPanel, BoxLayout.Y_AXIS));
        // subcomponents
        //HUD subcomponent
        w = (int) (this.width * .95);
        h = (int) (this.height * 0.1);
        this.hudPanel = new HUDPanel();
        this.contentPanel.add(this.hudPanel);
        this.hudPanel.setSize(w,h);
        this.hudPanel.setPreferredSize(new Dimension(w, h));
        this.hudPanel.setScale();
        this.hudPanel.update();
        this.hudPanel.setVisible(true);

        // viewport subcomponent
        this.viewportPanel = new JViewport();
        this.contentPanel.add(this.viewportPanel);
        //this.viewportPanel.setView(this.spacePanel);
        this.viewportPanel.setSize(w, h);
        this.viewportPanel.setPreferredSize(new Dimension(w, h));
        this.viewportPanel.setViewPosition(new Point(0,0));
        this.viewportPanel.setVisible(true);
        this.viewportPanel.setOpaque(true);
        
        // spacepanel subcomponent
        w = w * 2;
        h = h * 2;
        this.spacePanel = new SpacePanel(game.space);
        //this.contentPanel.add(this.spacePanel);
        this.spacePanel.setSize(w, h);
        this.spacePanel.setPreferredSize(new Dimension(w, h));
        this.spacePanel.setScale();
        this.spacePanel.setVisible(true);
        
        this.viewportPanel.setView(this.spacePanel);
    }

    public void update() {
        if ((this.width != this.getWidth()) && (this.height != this.getHeight())) {
            this.width = this.getWidth();
            this.height = this.getHeight();
//            this.content.setSize(this.width, this.height);
            int w = (int) (this.width * 0.95);
            int h = (int) (this.height * 0.1);
            this.hudPanel.setPreferredSize(new Dimension(w,h));
            h = (int) (this.height * 0.9);
            this.viewportPanel.setPreferredSize(new Dimension(w, h));
            w = (int)(w * 1.5);
            h = (int)(h * 1.5);
            this.spacePanel.setPreferredSize(new Dimension(w, h));
            this.hudPanel.setScale();
            this.spacePanel.setScale();
            //this.panel.setLocation(0, (int) (this.height * 0.1));
        }
        this.hudPanel.update();
        this.updateViewport();
    }
    
    
    public void updateViewport(){
        if (this.target == null){
            int x0 = (this.spacePanel.getWidth() - this.viewportPanel.getWidth())/2;
            int y0 = (this.spacePanel.getHeight() - this.viewportPanel.getHeight())/2;
            this.viewportPanel.setViewPosition(new Point(x0, y0));
        } else {
            
        }
    }

}
