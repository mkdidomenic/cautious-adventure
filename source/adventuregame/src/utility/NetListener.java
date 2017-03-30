/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.util.ArrayList;

/**
 *
 * @author Mike
 */
public class NetListener {

    public ArrayList<Object> inbox;
    public String ip;
    public int port;
    public int timeout;
    public boolean alive;
    private Thread listenThread;

    public NetListener() {
        this.ip = "localhost";
        this.port = NetworkHandler.mainPort;
        this.timeout = 0;
        this.inbox = new ArrayList();

    }

    public synchronized void start() {
        listenThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    Object o = NetworkHandler.receiveMessage(timeout);
                    if (o != null) {
                        inbox.add(o);
                    }
                }
            }
        };
        listenThread.start();
    }

    public synchronized Object getMessage() {
        if (this.inbox.size() > 0) {
            Object o = this.inbox.get(0);
            this.inbox.remove(0);
            return o;
        } else {
            return null;
        }

    }

}
