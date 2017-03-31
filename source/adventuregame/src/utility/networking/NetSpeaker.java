/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility.networking;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

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
    
    public synchronized void setIP(String ip){
        this.ip = ip;
    }
    
    public Object sendMessage(Object message) {
        //System.out.println("sending to: " + this.ip + ":" + this.port);
        Object recv = null;
        try {
            ObjectOutputStream outToServer;
            ObjectInputStream inFromServer;
            try (Socket sock = new Socket(this.ip, this.port)) {
                outToServer = new ObjectOutputStream(
                        sock.getOutputStream());
                inFromServer = new ObjectInputStream(sock.getInputStream());
                outToServer.writeObject(message);
                sock.setSoTimeout(this.timeout);
                recv = inFromServer.readObject();
                sock.close();
            }
            outToServer.close();
            inFromServer.close();
        } catch (Exception ex) {
            System.out.println("Error sending TCP");
            System.out.println(Arrays.toString(ex.getStackTrace()));
            System.out.println("");
            ex.printStackTrace();
        }
        //System.out.println("Sender - message: " + recv + "\n");
        return recv;
    }

}
