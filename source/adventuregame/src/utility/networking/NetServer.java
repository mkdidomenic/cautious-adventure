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
    
    @Override
    public Object handleMessage(Object o){
        if (o instanceof NetPackage) {
            NetPackage pack = (NetPackage) o;
            if (pack.packageType == NetPackage.Packtype.PAYLOAD) {
                Object payload = ((NetPackage) o).payload;
                //System.out.println("Package: " + payload);
            } else if (pack.packageType == NetPackage.Packtype.JOINREQUEST) {
                ArrayList<String> data = (ArrayList<String>) pack.payload;
                String name = data.get(0);
                String ct = data.get(1);
                this.client.controller.players.add(new Player(name, ct));
                this.client.startMenu.addPlayerToList(name, ct);
                // System.out.println("got: " + name + " (" + ct + ")");
            }
        }
        return o;
    }
    

}

