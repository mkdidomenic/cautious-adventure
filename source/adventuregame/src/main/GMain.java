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
        GClient client = new GClient();
        if (args.length == 1){
           client.netl.port =  Integer.valueOf(args[0]);
           client.nets.port =  Integer.valueOf(args[0]);
        } else if (args.length < 2){
            client.netl.port = 8264;
            client.nets.port = 8265;
        } else {
            client.netl.port = Integer.valueOf(args[0]);
            client.nets.port = Integer.valueOf(args[1]);
        }
        
        client.start();
    }

}
