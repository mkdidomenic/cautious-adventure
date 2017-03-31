/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Mike
 */
public class GMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Thread t1;
        t1 = new Thread() {
            @Override
            public void run() {
                GClient client1 = new GClient();
                client1.netl.port = 8061;
                client1.nets.port = 8061;
                client1.start();
            }
        };
        t1.start();

        Thread t2;
        t2 = new Thread() {
            @Override
            public void run() {
                GClient client2 = new GClient();
                client2.netl.port = 8062;
                client2.nets.port = 8061;
                client2.start();
            }
        };
        //t2.start();

    }

}
