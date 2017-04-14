/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility.networking;

import java.io.Serializable;

/**
 *
 * @author Mike
 */
public class NetPackage implements Serializable {

    public Packtype packageType;
    public Object payload;
    public int ID;

    public NetPackage(int ID, Packtype p, Object payload) {
        this.packageType = p;
        this.payload = payload;
        this.ID = ID;
    }

    public enum Packtype {
        JOINREQUEST,
        JOINRESPONSE,
        LOBBYUPDATE,
        GAME,
        PAYLOAD,
        NULL;

    }

}
