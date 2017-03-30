/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility.networking;

import game.Game;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.GMain;

/**
 *
 * @author Mike
 */
public class NetworkHandler {

    public static final int mainPort = 8071;
    public static boolean debug = false;

    public ArrayList<Object> inbox;
    public ArrayList<Object> outbox;
    public String ip;
    public int port;
    public int timeout;
    public boolean alive;

    public NetworkHandler() {
        this.inbox = new ArrayList();
        this.outbox = new ArrayList();
        this.ip = "localhost";
        this.port = NetworkHandler.mainPort;
        this.timeout = 0;
        this.alive = true;

    }

    public synchronized void start() {
        Thread sender = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (outbox.size() > 0) {
                        Object m = outbox.get(0);
                        outbox.remove(0);
                        NetworkHandler.sendMessage(ip, m, timeout);
                    }
                }
            }
        };
        Thread receiver = new Thread() {
            @Override
            public void run() {
                while (true) {
                    Object m = NetworkHandler.receiveMessage(timeout);
                    if (m != null) {
                        inbox.add(m);
                    }
                }

            }
        };
        sender.start();
        receiver.start();
    }

    public synchronized Object getMessage() {
        if (this.inbox.size() > 0) {
            Object m = this.inbox.get(0);
            this.inbox.remove(0);
            return m;
        } else {
            return null;
        }
    }

    public synchronized void addMessage(Object message) {
        this.outbox.add(message);
    }

    public static void sendMessage(String ip, Object message, int timeout) {
        try {
            ObjectOutputStream outToServer;
            try (Socket sock = new Socket(ip, NetworkHandler.mainPort)) {
                sock.setSoTimeout(timeout);
                outToServer = new ObjectOutputStream(
                        sock.getOutputStream());
                outToServer.writeObject(message);
                sock.close();
            }
            outToServer.close();
        } catch (Exception ex) {
            if (debug) {
                System.out.println("Error sending TCP");
                System.out.println(Arrays.toString(ex.getStackTrace()));
                System.out.println("");
                ex.printStackTrace();
            }
        }

    }

    public static Object receiveMessage(int timeout) {
        Object recv = null;
        try {
            ObjectInputStream inToServer;
            ServerSocket ssock = new ServerSocket(NetworkHandler.mainPort);
            ssock.setSoTimeout(timeout);
            try (Socket sock = ssock.accept()) {
                inToServer = new ObjectInputStream(sock.getInputStream());
                sock.setSoTimeout(timeout);
                recv = inToServer.readObject();
                //String r = (String) recv;
                //r += "-SERVER";

                inToServer.close();
                sock.close();
                ssock.close();
            }
        } catch (Exception ex) {
            if (debug) {
                System.out.println("Error receiving TCP");
                System.out.println(Arrays.toString(ex.getStackTrace()));
                System.out.println("");
            }
        }
        //System.out.println("Reciever - message: " + recv + "\n");

        return recv;
    }

    public static void sendMessageVerify(String ip, Object message, int timeout) {
        Object recv = null;
        try {
            ObjectOutputStream outToServer;
            ObjectInputStream inFromServer;
            try (Socket sock = new Socket(ip, NetworkHandler.mainPort)) {
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

    public static Object receiveMessageVerify(int timeout) {
        Object recv = null;
        try {
            ObjectInputStream inToServer;
            ObjectOutputStream outFromServer;
            ServerSocket ssock = new ServerSocket(NetworkHandler.mainPort);
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
                    g.addPlayer("mikey", "ka bam");
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

    public static void test2() {
        Object SEND = new Game();

        NetworkHandler nh = new NetworkHandler();
        nh.start();

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
                    nh.addMessage("hi");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GMain.class.getName()).log(Level.SEVERE,
                                                                    null,
                                                                    ex);
                    }
                    Game g = (Game) SEND;
                    g.addPlayer("mikey", "ka bam");
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
                    Object got = nh.getMessage();
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
