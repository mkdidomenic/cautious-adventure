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

    public boolean isHost;

    public GClient() {
        this.controller = new GController();
    }

    public void start() {
        this.startMenu = new StartMenu();
        this.startMenu.setVisible(true);
        while (!this.startMenu.start) {
            try {
                Thread.sleep(1);
                this.isHost = this.startMenu.isHost();
            } catch (InterruptedException ex) {
                System.out.println("INTERRUPTED CLIENT SLEEP - NONESSENTIAL");
            }
        }
        this.setup();
        this.run();

    }

    private void setup() {
        String pname = this.startMenu.getNameFromTextfield();
        String ct = this.startMenu.getClassTypeFromMenu();
        ArrayList<String> al = new ArrayList();
        al.add(pname);
        al.add(ct);
        this.controller.playerandct.add(al);
        this.controller.isHost = this.isHost;
        this.controller.friendlyFire = this.startMenu.getFriendlyFireRadio();
        this.startMenu.setVisible(false);
        this.startMenu.dispose();
    }

    public void run() {
        this.controller.setup();
        this.controller.run();
    }

    public void sendGameToClient() {
        if (this.isHost) {

        }
    }

    public void getGameFromServer() {
        if (!this.isHost) {

        }
    }

    public void sendCommToServer() {
        if (this.isHost) {

        }
    }

    public void getCommFromClient() {
        if (!this.isHost) {

        }
    }

}
