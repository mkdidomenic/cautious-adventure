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
public class NetSpeaker {

    public ArrayList<Object> outbox;
    public String ip;
    public int port;
    public int timeout;
    public boolean alive;
    private Thread speakThread;

    public NetSpeaker() {
        this.ip = "localhost";
        this.port = NetworkHandler.mainPort;
        this.timeout = 0;
        this.outbox = new ArrayList();

    }

    public synchronized void start() {
        speakThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (outbox.size() > 0) {
                        Object o = outbox.get(0);
                        outbox.remove(0);
                        NetworkHandler.sendMessage(ip, o, timeout);
                    }
                }
            }
        };
        speakThread.start();
    }

    public synchronized void addMessage(Object message) {
        this.outbox.add(message);
    }

}
