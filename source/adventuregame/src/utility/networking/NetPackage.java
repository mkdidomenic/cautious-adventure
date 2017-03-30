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

    public NetPackage(Packtype p, Object payload) {
        this.packageType = p;
        this.payload = payload;
    }

    public enum Packtype {
        JOINREQUEST,
        PAYLOAD,
        NULL;

    }

}
