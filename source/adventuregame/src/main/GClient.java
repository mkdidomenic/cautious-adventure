/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import game.Game;
import game.Player;
import java.util.ArrayList;
import java.util.List;
import menu.StartMenu;
import utility.networking.NetPackage;
import utility.networking.NetServer;
import utility.networking.NetSpeaker;

/**
 *
 * @author Mike
 */
public class GClient {

    public GController controller;
    public StartMenu startMenu;
    public NetServer netl;
    public NetSpeaker nets;

    public boolean isHost;
    public boolean joined;

    public GClient() {
        this.controller = new GController();
        this.netl = new NetServer(this);
        this.nets = new NetSpeaker();
        this.isHost = false;
        this.joined = false;
    }

    public void start() {
        this.netl.start();
        //this.nets.start();
        this.startMenu = new StartMenu();
        this.startMenu.setVisible(true);

        while (!this.startMenu.start) {
            this.isHost = this.startMenu.isHost();
            if (this.isHost) {

            } else {
                this.controller.client = this;
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
        Player p = new Player(pname, ct);
        this.controller.localID = p.ID;
        this.controller.players.add(p);
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
        if (this.joined) {
            this.sendLobbyUpdateRequest();
        } else if (this.startMenu.sendJoinRequest()) {
            this.sendJoinRequest();
        }
    }

    public void sendLobbyUpdateRequest() {
        NetPackage p = new NetPackage(this.controller.localID, NetPackage.Packtype.LOBBYUPDATE, null);
        Object o = null;
        try {
            o = this.nets.sendMessage(p);
        } catch (Exception e) {
            System.out.println("NONESSENTIAL - error getting lobby update");
        }
        //System.out.println(o);
        if (o != null) {
            NetPackage np = (NetPackage) o;
            if (np.packageType == NetPackage.Packtype.LOBBYUPDATE) {
                if ((this.startMenu != null)) {
                    Object pl = np.payload;
                    if (pl != null) {
                        this.startMenu.start = (boolean) pl;
                    }
                }
            } else if (np.packageType == NetPackage.Packtype.GAME) {
                this.startMenu.start = true;
            }
        }

    }

    public void sendJoinRequest() {
        String ip = this.startMenu.getIP();
        String name = this.startMenu.getNameFromTextfield();
        String ct = this.startMenu.getClassTypeFromMenu();
        ArrayList<String> s = new ArrayList();
        s.add(name);
        s.add(ct);
        NetPackage payload = new NetPackage(this.controller.localID, NetPackage.Packtype.JOINREQUEST,
                                            s);
        nets.setIP(ip);
        Object got = nets.sendMessage(payload);
        int id = ((int) (((NetPackage) got).payload));
        this.controller.localID = id;
        this.joined = true;
        this.startMenu.setJoinLabelText("Joined with ID: " + id);

    }

    public Game sendGameMessage() {
        List<Character> keys = this.controller.keyHandler.getKeys();
        List<Object> pack;
        NetPackage n = new NetPackage(this.controller.localID, NetPackage.Packtype.GAME, keys);
        // TODO FIX NULL POINTER ERROR ON LINE 178 when sending
        Game g = (Game) ((NetPackage) this.nets.sendMessage(n)).payload;
        return g;
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
