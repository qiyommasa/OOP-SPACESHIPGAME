package core;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

import objects.Background;
import objects.Spaceship;
import render.Renderer;
import update.Updater;
import objects.EnemySpaceshipSpawner;

public class Entry {
    private static int score = 0;  // Variabel untuk menyimpan skor
    private static boolean isGameOver = false;  // Status game

    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        while (true) {  // Game Loop Utama
            // Tampilkan menu awal
            int userChoice = showMainMenu();

            // Exit jika user memilih keluar
            if (userChoice == 1) {
                System.exit(0);
            }

            // Mulai permainan
            startGame();
        }
    }

    private static void startGame() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        // Reset variabel untuk permainan baru
        score = 0;
        isGameOver = false;

        // Inisialisasi game
        Window window = new Window("SPACE SHIP", Window.getWinWidth(), Window.getWinHeight());
        Renderer renderer = new Renderer();
        Updater updater = new Updater();

        window.addKeyListener(new Input());
        window.add(renderer);
        window.packWindow();
        window.setLocationRelativeTo(null);  // Pastikan window muncul di tengah layar
        window.setVisible(true);

        // Setup objek game
        new Spaceship((Window.getWinWidth() / 2) - (Spaceship.width / 2), Window.getWinHeight() - 150);
        new Background(-Window.getWinHeight());
        new EnemySpaceshipSpawner();

        FPS.calcBeginTime();

        // Loop game
        while (!isGameOver) { 
            updater.update();
            renderer.repaint();
            FPS.calcDeltaTime() ;

            // Periksa apakah game over
            if (isGameOver) {
                window.dispose();
                showGameOverScreen();
            }
        }
    }

    private static void showGameOverScreen() {
        // Menampilkan hanya skor dan pesan game over
        String message = "Game Over!\nYour Score: " + score + "\n\nPress OK to Exit.";
        JOptionPane.showMessageDialog(
            null,
            message,
            "Game Over",
            JOptionPane.PLAIN_MESSAGE
        );
        System.exit(0); // Keluar dari aplikasi setelah game over
    }

    // Menambah skor
    public static void addScore(int points) {
        score += points;
    }

    // Mendapatkan skor
    public static int getScore() {
        return score;
    }

    // Mengatur status game over
    public static void setGameOver() {
        isGameOver = true;
    }

    // Menampilkan menu utama menggunakan JOptionPane.
    private static int showMainMenu() {
         String[] options = {"Start Game", "Exit"};
        int choice = JOptionPane.showOptionDialog(
            null,
            "Welcome to SPACESHIP !\nChoose an option:",
            "Main Menu",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0] 
        );
        return choice;
    }
}
