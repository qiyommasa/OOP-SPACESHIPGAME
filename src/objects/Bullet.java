package objects;

import core.FPS;
import core.Entry;
import core.Sound;
import render.Renderable;
import render.Renderer;
import update.Updateable;
import update.Updater;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bullet implements Updateable, Renderable {
    private static double width = 20;
    private static double height = 30;
    private double x;
    private double y;

    private final int layer = 1;

    private static BufferedImage bullet;

    private static double speed = 500;

    public Bullet(double x, double y) throws IOException {
        this.x = x - (getWidth() / 2);
        this.y = y - (getHeight() / 2);

        if (bullet == null) {
            bullet = ImageIO.read(new File("res/Bullet.png"));
        }

        Renderer.addRenderableObject(this);
        Updater.addUpdateableObject(this);
    }

    @Override
    public void update() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        // Gerakan peluru
        y -= speed * FPS.getDeltaTime();

        // Jika peluru keluar layar, hapus dari daftar
        if (y < -getHeight()) {
            removeBullet();
            return;
        }

        // Deteksi tabrakan dengan musuh
        Updateable collidingObject = isCollding(this, "enemyspaceship");
        if (collidingObject != null) {
            // Hapus peluru dan musuh
            removeBullet();
            removeCollidingObject(collidingObject);

            // Mainkan suara dan tambah skor
            Sound.playSound("res/EnemyDeath.wav");
            Entry.addScore(10);
        }
    }

    // Hapus peluru dari daftar update dan render
    private void removeBullet() {
        Updater.removeUpdateableObject(this);
        Renderer.removeRenderableObject(this);
    }

    // Hapus objek musuh dari daftar update dan render
    private void removeCollidingObject(Updateable collidingObject) {
        Updater.removeUpdateableObject(collidingObject);
        Renderer.removeRenderableObject(collidingObject.getRenderable());
    }

    @Override
    public String getID() {
        return "bullet";
    }

    @Override
    public Renderable getRenderable() {
        return this;
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public BufferedImage getBufferedImage() {
        return bullet;
    }

    @Override
    public boolean drawCollisionBox() {
        return true;
    }
}
