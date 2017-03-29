/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import menu.StartMenu;

/**
 *
 * @author Mike
 */
public class GClient {

    public GController controller;
    public StartMenu startMenu;

    public GClient() {
        this.controller = new GController();
    }

    public void start() {
        this.startMenu = new StartMenu();
        this.startMenu.setVisible(true);
        while (!this.startMenu.start) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println("INTERRUPTED CLIENT SLEEP - NONESSENTIAL");
            }
        }
        String pname = this.startMenu.getNameFromTextfield();
        String ct = this.startMenu.getClassTypeFromMenu();
        ArrayList<String> al = new ArrayList();
        al.add(pname);
        al.add(ct);
        this.controller.playerandct.add(al);
        this.controller.friendlyFire = this.startMenu.getFriendlyFireRadio();
        this.startMenu.setVisible(false);
        this.startMenu.dispose();
        this.run();
    }

    public void run() {
        this.controller.setup();
        System.out.println(this.controller.game.friendlyFire);
        this.controller.run();
    }

}
