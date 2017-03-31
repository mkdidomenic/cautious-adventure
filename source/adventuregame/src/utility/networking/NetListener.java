/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility.networking;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Mike
 */
public class NetListener extends Thread {

    public ArrayList<Object> inbox;
    public String ip;
    public int port;
    public int timeout;

    public NetListener() {
        this.ip = "localhost";
        this.port = 8601;
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
        this.receiveMessage();
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
    
    
    public Object receiveMessage(){
        Object recv = null;
        try {
            ObjectInputStream inToServer;
            ObjectOutputStream outFromServer;
            ServerSocket ssock = new ServerSocket(this.port);
            try (Socket sock = ssock.accept()) {
                //System.out.println("Accepting at: " + this.port);
                inToServer = new ObjectInputStream(sock.getInputStream());
                outFromServer = new ObjectOutputStream(
                        sock.getOutputStream());

                sock.setSoTimeout(this.timeout);
                recv = inToServer.readObject();
                recv = handleMessage(recv);
                outFromServer.writeObject(recv);

                outFromServer.close();
                inToServer.close();
                sock.close();
                ssock.close();
            }
        } catch (Exception ex) {
            System.out.println("Error receiving TCP");
            System.out.println(Arrays.toString(ex.getStackTrace()));
            System.out.println("");
        }
        //System.out.println("Reciever - message: " + recv + "\n");

        return recv;
    }
    
    public Object handleMessage(Object o){
        return o;
    }
    

}

