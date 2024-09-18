import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.*;

public class ipod extends JFrame {

    private JLabel songLabel;
    private JLabel artistLabel;
    private JLabel imageLabel;
    private Clip clip;
    private ImageIcon ipodIcon; // New iPod icon
    private String[] songs = {"fortnite.wav", "griddy.wav", "turi.wav", "hamood.wav"};
    private String[] artists = {"Epic Games", "NBA YoungBoy", "Kitty", "Hamoodi"};
    private String[] images = {"fortnitepic.jpeg", "griddypic.jpeg", "cat.jpeg", "blob.jpg"};
    private int currentSongIndex;
    private boolean isPaused = false;

    public ipod() {
        setTitle("iPod - Leo Chow");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create the UI components
        JPanel mainPanel = new JPanel(new BorderLayout()); // Use BorderLayout
        songLabel = new JLabel(songs[currentSongIndex]);
        artistLabel = new JLabel(artists[currentSongIndex]);
        ipodIcon = new ImageIcon("wheel.png"); // Load the wheel icon
        Image image = ipodIcon.getImage().getScaledInstance(350, 350, Image.SCALE_DEFAULT);
        imageLabel = new JLabel(new ImageIcon(image));

        // Create a subpanel for song details
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Use FlowLayout
        topPanel.add(songLabel);
        topPanel.add(artistLabel);

        // Create a subpanel for the iPod icon
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)); // Use FlowLayout
        bottomPanel.add(new JLabel(ipodIcon)); // Add the iPod icon to the bottom panel

        // Add the components to the main panel
        mainPanel.add(topPanel, BorderLayout.NORTH); // Position the topPanel at the top left
        mainPanel.add(bottomPanel, BorderLayout.SOUTH); // Position the bottomPanel at the bottom
        mainPanel.add(imageLabel, BorderLayout.CENTER);

        // Add a mouse listener to the panel
        mainPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                System.out.println("Mouse clicked at (" + x + ", " + y + ")");

                // Handle clicks at the specified coordinates
                if (x >= 163 && x <= 213 && y >= 389 && y <= 439) { // Previous song
                    playPreviousSong();
                    System.out.println("Back");
                } else if (x >= 428 && x <= 478 && y >= 389 && y <= 439) { // Next song
                    playNextSong();
                    System.out.println("Skip");
                } else if (x >= 295 && x <= 345 && y >= 529 && y <= 579) { // Pause/resume
                    pauseResumeSong();
                    System.out.println(isPaused ? "Pause" : "Resume");
                }
            }
        });

        add(mainPanel);

        setVisible(true);
    }

    private void playPreviousSong() {
        currentSongIndex--;
        if (currentSongIndex < 0) {
            currentSongIndex = songs.length - 1;
        }
        songLabel.setText(songs[currentSongIndex]);
        artistLabel.setText(artists[currentSongIndex]);
        imageLabel.setIcon(new ImageIcon(images[currentSongIndex]));
        if (clip != null) {
            clip.stop();
            clip.close();
        }
        playSong();
    }

    private void playNextSong() {
        currentSongIndex++;
        if (currentSongIndex >= songs.length) {
            currentSongIndex = 0;
        }
        songLabel.setText(songs[currentSongIndex]);
        artistLabel.setText(artists[currentSongIndex]);
        imageLabel.setIcon(new ImageIcon(images[currentSongIndex]));
        if (clip != null) {
            clip.stop();
            clip.close();
        }
        playSong();
    }

    private void pauseResumeSong() {
        if (clip == null) {
            playSong();
            return;
        }
        if (isPaused) {
            clip.start();
            isPaused = false;
        } else {
            clip.stop();
            isPaused = true;
        }
    }

    private void playSong() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(songs[currentSongIndex]));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
            System.out.println("Error playing audio.");
        }
    }

    public static void main(String[] args) {
        ipod musicPlayer = new ipod();
    }
}
