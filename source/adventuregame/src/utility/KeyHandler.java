/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import view.View;

/**
 *
 * @author Mike
 */
public class KeyHandler implements KeyListener, MouseListener {

    public final boolean debug = false;

    public List<Character> currentkeys;
    public int mouseX;
    public int mouseY;

    public KeyHandler(View view) {
        view.addKeyListener(this);
        view.addMouseListener(this);
        this.currentkeys = new ArrayList();
    }

    public synchronized boolean haskey() {
        return !(this.currentkeys.isEmpty());
    }

    public synchronized void addkey(char c) {
        if (!(this.currentkeys.contains(c))) {
            this.currentkeys.add(c);
        }
    }

    public synchronized void removekey(char c) {
        if (this.currentkeys.contains(c)) {
            int i = this.currentkeys.indexOf(c);
            this.currentkeys.remove(i);
        }
    }

    public synchronized List<Character> getKeys() {
        ArrayList<Character> k = (ArrayList) this.currentkeys;
        return (ArrayList<Character>) k.clone();
    }

    public synchronized void updateMouse() {
        Point mp = MouseInfo.getPointerInfo().getLocation();
        this.mouseX = mp.x;
        this.mouseY = mp.y;
        if (false) {
            System.out.println("Mouse: " + mp.x + " : " + mp.y);
        }
    }

    public synchronized int getMouseX() {
        return this.mouseX;
    }

    public synchronized int getMouseY() {
        return this.mouseY;
    }

    ///////////////////////////////////////////////////////////////
    @Override
    public void keyTyped(KeyEvent e) {
        if (debug) {
            System.out.println(e.getKeyChar() + " typed");
        }
        //To change, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();
        this.addkey(c);
        this.updateMouse();
        if (debug) {
            System.out.println(c + " pressed");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        char c = e.getKeyChar();
        this.removekey(c);
        if (debug) {
            System.out.println(c + " released");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        char c = Character.forDigit(e.getButton(), 10);
        if (debug) {
            System.out.println(c + " mouse clicked");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        char c = Character.forDigit(e.getButton(), 10);
        this.addkey(c);
        this.updateMouse();
        if (debug) {
            System.out.println(c + " mouse pressed");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        char c = Character.forDigit(e.getButton(), 10);
        this.removekey(c);
        if (debug) {
            System.out.println(c + " mouse released");
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.updateMouse();
        if (debug) {
            System.out.println("Mouse entered window");
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (debug) {
            System.out.println("Mouse exited window");
        }
    }

}
