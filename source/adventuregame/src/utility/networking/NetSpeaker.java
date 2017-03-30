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
public class NetSpeaker extends Thread {

    public ArrayList<Object> outbox;
    public String ip;
    public int port;
    public int timeout;
    public boolean alive;

    public NetSpeaker() {
        this.ip = "localhost";
        this.port = NetworkHandler.mainPort;
        this.timeout = 0;
        this.outbox = new ArrayList();

    }

    @Override
    public void run() {
        while (true) {
            this.send();
        }
    }

    public void send() {
        if (this.outboxSize() > 0) {
            Object o = this.getObject0();
            //System.out.println("sending: " + o);
            NetworkHandler.sendMessageTCP(ip, port, o, timeout);
        }
    }

    private synchronized int outboxSize() {
        return this.outbox.size();
    }

    private synchronized Object getObject0() {
        Object o = this.outbox.get(0);
        this.outbox.remove(0);
        return o;
    }

    public synchronized void addMessage(Object message) {
        this.outbox.add(message);
    }

}
