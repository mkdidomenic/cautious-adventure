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
        GClient client1 = new GClient();
        client1.netl.port = 8061;
        client1.nets.port = 8062;
        client1.start();
    }

}
