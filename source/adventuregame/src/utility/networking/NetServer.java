/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility.networking;

import game.Player;
import java.util.ArrayList;
import main.GClient;

/**
 *
 * @author Mike
 */
public class NetServer extends NetListener {

    public GClient client;

    public NetServer(GClient g) {
        super();
        this.client = g;

    }

    /**
     * takes a message object, returns what should be sent to the sender
     *
     * @param o - object received
     * @return - object to return to sender
     */
    @Override
    public Object handleMessage(Object o) {
        if (o instanceof NetPackage) {
            NetPackage pack = (NetPackage) o;
            if (pack.packageType == NetPackage.Packtype.PAYLOAD) {
                Object payload = ((NetPackage) o).payload;
                //System.out.println("Package: " + payload);
            } else if (pack.packageType == NetPackage.Packtype.JOINREQUEST) {
                o = handleJoinRequest(pack);
            } else if (pack.packageType == NetPackage.Packtype.LOBBYUPDATE) {
                ((NetPackage) o).payload = this.client.startMenu.start;
            }
        }
        return o;
    }

    private Object handleJoinRequest(NetPackage pack) {
        ArrayList<String> data = (ArrayList<String>) pack.payload;
        String name = data.get(0);
        String ct = data.get(1);
        Player newPlayer = new Player(name, ct);
        this.client.controller.players.add(newPlayer);
        this.client.startMenu.addPlayerToList(name, ct);
        // System.out.println("got: " + name + " (" + ct + ")");
        int id = newPlayer.ID;
        NetPackage ret = new NetPackage(NetPackage.Packtype.JOINRESPONSE, id);
        return ret;
    }

}
