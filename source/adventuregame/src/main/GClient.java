/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import game.Player;
import java.util.ArrayList;
import menu.StartMenu;
import utility.networking.NetListener;
import utility.networking.NetPackage;
import utility.networking.NetSpeaker;

/**
 *
 * @author Mike
 */
public class GClient {

    public GController controller;
    public StartMenu startMenu;
    public NetListener netl;
    public NetSpeaker nets;

    public boolean isHost;

    public GClient() {
        this.controller = new GController();
        this.netl = new NetListener();
        this.nets = new NetSpeaker();
    }

    public void start() {
        this.netl.start();
        this.nets.start();
        this.startMenu = new StartMenu();
        this.startMenu.setVisible(true);

        while (!this.startMenu.start) {

            this.isHost = this.startMenu.isHost();
            if (this.isHost) {
                this.handleRequests();

            } else {
                this.handleSends();
                this.handleResponses();
            }
        }
        this.setup();
        this.run();

    }

    private void setup() {
        String pname = this.startMenu.getNameFromTextfield();
        String ct = this.startMenu.getClassTypeFromMenu();
        this.controller.localplayer = new Player(pname, ct);
        this.controller.players.add(this.controller.localplayer);
        this.controller.isHost = this.isHost;
        this.controller.friendlyFire = this.startMenu.getFriendlyFireRadio();
        this.startMenu.setVisible(false);
        this.startMenu.dispose();
    }

    public void run() {
        this.controller.setup();
        this.controller.run();
    }

    public void handleRequests() {
        Object message = this.netl.getMessage();
        if ((message != null) && false) {
            System.out.println(message);
        }
        if (message instanceof NetPackage) {
            NetPackage pack = (NetPackage) message;
            if (pack.packageType == NetPackage.Packtype.PAYLOAD) {
                Object payload = ((NetPackage) message).payload;
                //System.out.println("Package: " + payload);
            } else if (pack.packageType == NetPackage.Packtype.JOINREQUEST) {
                ArrayList<String> data = (ArrayList<String>) pack.payload;
                String name = data.get(0);
                String ct = data.get(1);
                this.controller.players.add(new Player(name, ct));
                this.startMenu.addPlayerToList(name, ct);
                // System.out.println("got: " + name + " (" + ct + ")");
            }
        }

    }
    
    
    public void handleResponses() {
        Object message = this.netl.getMessage();
        if ((message != null) && false) {
            System.out.println(message);
        }
        if (message instanceof NetPackage) {
            NetPackage pack = (NetPackage) message;
            if (pack.packageType == NetPackage.Packtype.PAYLOAD) {
                Object payload = ((NetPackage) message).payload;
                //System.out.println("Package: " + payload);
            } else if (pack.packageType == NetPackage.Packtype.JOINRESPONSE) {
                ArrayList<String> data = (ArrayList<String>) pack.payload;
                String name = data.get(0);
                String ct = data.get(1);
                this.controller.players.add(new Player(name, ct));
                this.startMenu.addPlayerToList(name, ct);
                // System.out.println("got: " + name + " (" + ct + ")");
            }
        }

    }

    public void handleSends() {
        //System.out.println(this.startMenu.sendJoinRequest());
        if (this.startMenu.sendJoinRequest()) {
            this.sendJoinRequest();
        }
    }

    public void sendJoinRequest() {
        String ip = this.startMenu.getIP();
        String name = this.startMenu.getNameFromTextfield();
        String ct = this.startMenu.getClassTypeFromMenu();
        ArrayList<String> s = new ArrayList();
        s.add(name);
        s.add(ct);
        NetPackage payload = new NetPackage(NetPackage.Packtype.JOINREQUEST,
                                            s);
        nets.setIP(ip);
        nets.addMessage(payload);

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
