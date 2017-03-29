/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import game.Game;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.GMain;

/**
 *
 * @author Mike
 */
public class NetworkHandler {

    public static final int port = 8071;

    public static void sendMessage(String ip, Object message, int timeout) {
        Object recv = null;
        try {
            ObjectOutputStream outToServer;
            ObjectInputStream inFromServer;
            try (Socket sock = new Socket(ip, NetworkHandler.port)) {
                outToServer = new ObjectOutputStream(
                        sock.getOutputStream());
                inFromServer = new ObjectInputStream(sock.getInputStream());
                outToServer.writeObject(message);
                sock.setSoTimeout(timeout);
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

    }

    public static Object receiveMessage(int timeout) {
        Object recv = null;
        try {
            ObjectInputStream inToServer;
            ObjectOutputStream outFromServer;
            ServerSocket ssock = new ServerSocket(NetworkHandler.port);
            try (Socket sock = ssock.accept()) {
                inToServer = new ObjectInputStream(sock.getInputStream());
                outFromServer = new ObjectOutputStream(
                        sock.getOutputStream());

                sock.setSoTimeout(timeout);
                recv = inToServer.readObject();
                //String r = (String) recv;
                //r += "-SERVER";
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

    public static void test() {
        Object SEND = new Game();
        Thread sender;
        sender = new Thread() {
            @Override
            public void run() {
                System.out.println("STARTING S");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GMain.class.getName()).log(Level.SEVERE,
                                                                null,
                                                                ex);
                }
                while (true) {
                    NetworkHandler.sendMessage("localhost", SEND,
                                               3000);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GMain.class.getName()).log(Level.SEVERE,
                                                                    null,
                                                                    ex);
                    }
                    Game g = (Game) SEND;
                    //g.addPlayer("mikey", "ka bam");
                }
            }
        };
        sender.start();

        Thread recver;
        recver = new Thread() {
            @Override
            public void run() {
                System.out.println("STARTING R");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GMain.class.getName()).log(Level.SEVERE,
                                                                null,
                                                                ex);
                }
                while (true) {
                    Object got = NetworkHandler.receiveMessage(3000);
                    System.out.print("Received: " + got);
                    if (got instanceof String) {
                        System.out.println(" (STRING)");
                    } else if (got instanceof Game) {
                        Game game = (Game) got;
                        System.out.println(
                                " (number of players: " + game.players.size() + ")");
                    } else {
                        System.out.println("");
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GMain.class.getName()).log(Level.SEVERE,
                                                                    null,
                                                                    ex);
                    }
                }
            }
        };
        recver.start();

    }

}
