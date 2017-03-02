/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author Mike
 */
public class Game {

    public Space space;

    public Game() {
        this.space = new Space();
    }

    public void update() {
        this.space.update();
    }

}
