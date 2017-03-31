/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility.networking;

import java.util.ArrayList;

/**
 *
 * @author Mike
 */
public class NetListener extends Thread {

    public ArrayList<Object> inbox;
    public String ip;
    public int port;
    public int timeout;
    public boolean alive;

    public NetListener() {
        this.ip = "localhost";
        this.port = NetworkHandler.mainPort;
        this.timeout = 0;
        this.inbox = new ArrayList();

    }

    @Override
    public void run() {
        while (true) {
            this.receive();
        }
    }

    public void receive() {
        Object o = NetworkHandler.receiveMessageTCP(port, timeout);
        if (o != null) {
            addObject(o);
        }
    }
    
    private synchronized ArrayList<Object> getInbox(){
        return (ArrayList<Object>) this.inbox.clone();
    }

    private synchronized void addObject(Object o) {
        inbox.add(o);
        //System.out.println("Received: " + o);
    }

    public synchronized Object getMessage() {
        //System.out.println(this.inbox);
        if (this.inbox.size() > 0) {
            Object o = this.inbox.get(0);
            this.inbox.remove(0);
            return o;
        } else {
            return null;
        }

    }

}
