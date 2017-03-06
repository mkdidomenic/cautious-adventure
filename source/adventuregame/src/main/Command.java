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

    NULL(false),
    MOVE_UP(true),
    MOVE_DOWN(true),
    MOVE_LEFT(true),
    MOVE_RIGHT(true),
    JUMP(true),
    ACTION1(true),
    ACTION2(true),
    DEBUG(false);

    public boolean isPlayerCommand;

    Command(boolean playercommand) {
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
            case '1':
                return ACTION1;
            case '2':
                return ACTION2;
            case '\\':
                return DEBUG;
            default:
                return NULL;
        }
    }

}
