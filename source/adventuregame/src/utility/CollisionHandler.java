/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import game.collision.Hitbox;

/**
 *
 * @author Mike
 */
public class CollisionHandler {

    public static boolean checkCollision(Hitbox a, Hitbox b) {
        return (checkCollisionA(a, b) || checkCollisionA(b, a));
    }

    public static boolean checkCollisionA(Hitbox a, Hitbox b) {
        if (a == b) {
            return false;
        }
        Spatial s111 = new Spatial(a.x1(), a.y1(), a.z1());
        Spatial s112 = new Spatial(a.x1(), a.y2(), a.z2());
        Spatial s121 = new Spatial(a.x1(), a.y2(), a.z1());
        Spatial s122 = new Spatial(a.x1(), a.y1(), a.z2());
        Spatial s211 = new Spatial(a.x2(), a.y2(), a.z1());
        Spatial s212 = new Spatial(a.x2(), a.y2(), a.z2());
        Spatial s221 = new Spatial(a.x2(), a.y1(), a.z1());
        Spatial s222 = new Spatial(a.x2(), a.y1(), a.z2());

        if (checkCollision(b, s111)) {
            return true;
        }
        if (checkCollision(b, s112)) {
            return true;
        }
        if (checkCollision(b, s121)) {
            return true;
        }
        if (checkCollision(b, s122)) {
            return true;
        }
        if (checkCollision(b, s211)) {
            return true;
        }
        if (checkCollision(b, s212)) {
            return true;
        }
        if (checkCollision(b, s221)) {
            return true;
        }
        if (checkCollision(b, s222)) {
            return true;
        }
        return false;

    }

    public static boolean checkCollision(Hitbox a, Spatial p) {
        if ((p.x >= a.x1()) && (p.x <= a.x2())) {
            if ((p.y >= a.y1()) && (p.y <= a.y2())) {
                if ((p.z >= a.z1()) && (p.z <= a.z2())) {
                    return true;
                }
            }
        }
        return false;
    }

}
