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
public enum Command {

    NULL(0, false),
    MOVE_UP(1, true),
    MOVE_DOWN(2, true),
    MOVE_LEFT(3, true),
    MOVE_RIGHT(4, true),
    JUMP(5, true),
    ACTION1(6, true);

    public int num;
    public boolean isPlayerCommand;

    Command(int num, boolean playercommand) {
        this.num = num;
        this.isPlayerCommand = playercommand;
    }

    public static Command map(char c) {
        switch (c) {
            case 'w':
                return MOVE_UP;
            case 's':
                return MOVE_DOWN;
            case 'a':
                return MOVE_LEFT;
            case 'd':
                return MOVE_RIGHT;
            case ' ':
                return JUMP;
            case 'f':
                return ACTION1;
            default:
                return NULL;
        }
    }

}
