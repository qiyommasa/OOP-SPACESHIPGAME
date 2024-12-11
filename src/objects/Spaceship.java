package objects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import core.FPS;
import core.Input;
import core.Sound;
import core.Timer;
import core.Window;
import core.Entry; // Import Entry untuk akses setGameOver()
import render.Renderable;
import render.Renderer;
import update.Updateable;
import update.Updater;

public class Spaceship implements Renderable, Updateable {
    public static double width = 75;
    private static double height = 75;
    private double x;
    private double y;

    private int layer = 1;

    private static BufferedImage spaceShip;

    private double speed = 200;

    private static int shootTimerMillis = 500;

    Timer timer = new Timer(shootTimerMillis);

    public Spaceship(double x, double y) throws IOException {
        this.x = x;
        this.y = y;

        spaceShip = ImageIO.read(new File("res/Spaceship.png"));
        Renderer.addRenderableObject(this);
        Updater.addUpdateableObject(this);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public static BufferedImage getSpaceShip() {
        return spaceShip;
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public void update() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if (Input.keys[Input.LEFT] && x >= 0)
            x -= speed * FPS.getDeltaTime();
        if (Input.keys[Input.RIGHT] && x <= Window.getWinWidth() - width)
            x += speed * FPS.getDeltaTime();
        if (Input.keys[Input.UP] && y >= 0)
            y -= speed * FPS.getDeltaTime();
        if (Input.keys[Input.DOWN] && y <= Window.getWinHeight() - height)
            y += speed * FPS.getDeltaTime();

        if (Input.keys[Input.SPACE] && timer.isRinging()) {
            new Bullet(x + (getWidth() / 2), y);
            timer.resetTimer();

            Sound.playSound("res/Shooting.wav");
        }

        // Deteksi tabrakan
        Updateable collidingObject = isCollding(this, "enemyspaceship");
        if (collidingObject != null) {
            // Hapus objek spaceship dan musuh
            Updater.removeUpdateableObject(this);
            Renderer.removeRenderableObject(this);

            Updater.removeUpdateableObject(collidingObject);
            Renderer.removeRenderableObject(collidingObject.getRenderable());

            Sound.playSound("res/Death.wav");

            // Set game over
            Entry.setGameOver();
        }
    }

    @Override
    public BufferedImage getBufferedImage() {
        return spaceShip;
    }

    @Override
    public String getID() {
        return "spaceship";
    }

    @Override
    public Renderable getRenderable() {
        return this;
    }

    @Override
    public boolean drawCollisionBox() {
        return true;
    }
}