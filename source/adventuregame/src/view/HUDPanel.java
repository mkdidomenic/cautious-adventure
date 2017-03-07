/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Label;
import javax.swing.JPanel;

/**
 *
 * @author Mike
 */
public class HUDPanel extends JPanel {

    public HUDPanel() {
        Label l = new Label();
        l.setText("HUD");
        this.add(l);
    }

}
