import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Label;
import javax.swing.JButton;
import java.awt.event.*;
import javax.swing.Timer;
import java.util.TimerTask;

/**
 * Idiot tries to make a Java Boss.
 *
 * @Henry. C
 * @5/27/25
 */
public class Bossfight
{
    private Player player;
    private Boss boss;
    private boolean ongoing;
    private int width;
    private int height;
    private int time;

    private Timer[] stoppers;

    private JPanel[] panels;
    private JLabel[] labels;
    private JFrame[] frames;
    private JButton[] buttons;

    // private String[] staticAttacks = {"CLICK OR DIE!", "CLICK TO DIE!", "ATTACK!"};
    private JFrame[] frames2;
    private JPanel[] panels2;
    private JButton[] buttons2;
    // private ArrayList <String> attacks;

    public Bossfight() {
        player = new Player();
        boss = new Boss();
        ongoing = false;
        time = 0;

        initialize();
        initialize2();
    }

    public void start() {
        if(ongoing) {
            if(time == 0) {
                attack();
            }
            else if(time == 1) {
                time = 0;
                System.out.println("Recursion!");
                start();
            }
        }
        System.out.println(ongoing);
    }

    public void attack() {
        stoppers[3].start();
        frames2[2].setVisible(true);
        stoppers[0].start();
        frames2[0].setVisible(true);
        stoppers[1].start();
        frames2[1].setVisible(true);
        stoppers[2].start();
        System.out.println("Running?");

        updateHealthBars();
    }

    // Attack GUIS
    private JButton clickDieButton() {
        JButton button = new JButton("CLICK OR DIE!");
        button.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    stoppers[1].stop();
                    frames2[0].setVisible(false);
                    System.out.println("Ya live!");
                }
            });
        return button;
    }

    private JButton clickToDieButton() {
        JButton button = new JButton("CLICK TO DIE!");
        button.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    stoppers[2].stop();
                    frames2[1].dispose();
                    player.damage(100);
                    checkHealth();
                    System.out.println("Ya DIE!"); 
                }
            });
        return button;
    }

    private JButton attackButton() {
        JButton button = new JButton("ATTACK!");
        button.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    boss.damage(5);
                    System.out.println("Attacking!");
                    updateHealthBars();
                    checkHealth();
                }
            });
        return button;
    }

    // -------------------------- Initializers  --------------------------
    public void initialize() {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)size.getWidth();
        height = (int)size.getHeight();

        // Initialize
        frames = new JFrame[3];
        panels = new JPanel[4];
        labels = new JLabel[4];
        buttons = new JButton[2];

        // Mainframe (frames[0])
        frames[0] = new JFrame();
        frames[0].setTitle("Bossfight");
        frames[0].setSize(width,height);
        frames[0].setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frames[0].setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frames[0].setResizable(false);
        frames[0].setLocationRelativeTo(null);

        // Game Over Frame (frames[1])
        frames[1] = new JFrame();
        frames[1].setTitle("LOSE");
        frames[1].setSize(width,height);
        frames[1].setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frames[1].setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frames[1].setResizable(false);
        frames[1].setLocationRelativeTo(null);

        // You Win Frame (frames[1])
        frames[2] = new JFrame();
        frames[2].setTitle("WIN!");
        frames[2].setSize(width,height);
        frames[2].setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frames[2].setExtendedState(JFrame.MAXIMIZED_BOTH);
        frames[2].setResizable(false);
        frames[2].setLocationRelativeTo(null);

        // Title Screen (frames[0] -> button[0])
        buttons[0] = new JButton("Start?");
        buttons[0].setBackground(Color.WHITE);
        buttons[0].setForeground(Color.BLACK);
        buttons[0].setFont(new Font("Sans-serif", Font.BOLD, 50));
        buttons[0].setFocusable(false);
        frames[0].add(buttons[0], BorderLayout.CENTER);

        // Boss HP GUI (frames[0] -> panels[0] - > labels[0])
        panels[0] = new JPanel();
        panels[0].setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panels[0].setBackground(Color.RED);
        frames[0].add(panels[0], BorderLayout.NORTH);
        panels[0].setVisible(false);

        labels[0] = new JLabel("HATRED BOSS HP: " + boss.getHP());
        labels[0].setForeground(Color.WHITE);
        labels[0].setFont(new Font("Sans-serif", Font.BOLD, 36));
        panels[0].add(labels[0]);

        // Player HP GUI (frames[0] -> panels[1] -> labels[1])
        panels[1] = new JPanel();
        panels[1].setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panels[1].setBackground(Color.BLUE);
        frames[0].add(panels[1], BorderLayout.SOUTH);

        labels[1] = new JLabel("YOUR HP: " + player.getHP());
        labels[1].setForeground(Color.WHITE);
        labels[1].setFont(new Font("Sans-serif", Font.BOLD, 20));
        panels[1].add(labels[1]);
        panels[1].setVisible(false);
        
        // Random Labels
        JLabel bossIcon = new JLabel();
        bossIcon.setIcon(new ImageIcon("Hatred.gif"));
        panels[0].add(bossIcon);
        
        JLabel playerIcon = new JLabel();
        playerIcon.setIcon(new ImageIcon("Griefer.gif"));
        panels[1].add(playerIcon);

        // Game Over (frame[0] -> panel[2] -> labels[2])
        panels[2] = new JPanel();
        panels[2].setBackground(Color.BLACK);
        frames[1].add(panels[2], BorderLayout.CENTER);

        labels[2] = new JLabel("GAME OVER!");
        labels[2].setForeground(Color.RED);
        JLabel icon2 = new JLabel();
        icon2.setSize(400,400);
        icon2.setIcon(new ImageIcon("Cope Mald.gif"));
        panels[2].add(icon2);
        labels[2].setFont(new Font("Sans-serif", Font.BOLD, 200));
        panels[2].add(labels[2]);

        // Win! (frame[0] -> panel[3] -> labels[3])
        panels[3] = new JPanel();
        panels[3].setBackground(Color.GREEN);
        JLabel icon = new JLabel();
        icon.setSize(500,500);
        icon.setIcon(new ImageIcon("Goku Dance.gif"));
        panels[3].add(icon);
        frames[2].add(panels[3], BorderLayout.CENTER);

        labels[3] = new JLabel("YOU WIN!");
        labels[3].setForeground(Color.WHITE);
        labels[3].setFont(new Font("Sans-serif", Font.BOLD, 200));
        panels[3].add(labels[3]);

        buttons[0].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Started!");
                    buttons[0].setVisible(false);
                    panels[0].setVisible(true);
                    panels[1].setVisible(true);
                    ongoing = true;

                    System.out.println("THE BOSS BATTLE HAS BEGUN!");
                    checkHealth();
                    start();
                }
            });
    }

    public void initialize2() {
        frames2 = new JFrame[3];
        panels2 = new JPanel[frames2.length];
        buttons2 = new JButton[frames2.length];
        stoppers = new Timer[4];

        // attacks = new ArrayList<>(Arrays.asList(staticAttacks));
        // Attack Frames because yes!

        // Click or Die!
        frames2[0] = new JFrame();
        frames2[0].setTitle("DIE!");
        frames2[0].setSize(150,150);
        frames2[0].setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frames2[0].setResizable(false);
        frames2[0].setLocation(rngWidth(), rngHeight());

        buttons2[0] = clickDieButton();
        buttons2[0].setFocusable(false);

        frames2[0].add(buttons2[0], BorderLayout.CENTER);

        stoppers[1] = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    stoppers[1].stop();
                    frames2[0].dispose();
                    player.damage(50);
                    checkHealth();
                    System.out.println("Die >:)!");
                }
            });
        stoppers[1].setRepeats(false);

        // Click to Die!
        frames2[1] = new JFrame();
        frames2[1].setTitle("LIVE!");
        frames2[1].setSize(150,150);
        frames2[1].setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frames2[1].setResizable(false);
        frames2[1].setLocation(rngWidth(), rngHeight());

        buttons2[1] = clickToDieButton();
        buttons2[1].setFocusable(false);

        frames2[1].add(buttons2[1], BorderLayout.CENTER);

        stoppers[2] = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    stoppers[2].stop();

                    frames2[1].setVisible(false);
                    System.out.println("Survived");
                }
            });
        stoppers[2].setRepeats(false);

        // Attack!
        frames2[2] = new JFrame();
        frames2[2].setTitle("ATTACK!");
        frames2[2].setSize(150,150);
        frames2[2].setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frames2[2].setResizable(false);
        frames2[2].setLocation(rngWidth(), rngHeight());

        buttons2[2] = attackButton();
        buttons2[2].setFocusable(false);

        frames2[2].add(buttons2[2], BorderLayout.CENTER);

        stoppers[0] = new Timer(3000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    stoppers[0].stop();
                    frames2[2].setVisible(false);
                    System.out.println("No more attack!");
                }
            });
        stoppers[0].setRepeats(false);

        // Attack stuff
        stoppers[3] = new Timer(3500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    attackRelocated();
                    time++;
                    start();
                    // for(String attackPattern : staticAttacks) {
                    // attacks.add(attackPattern);
                    // }
                    // System.out.println(attacks);
                }
            });
        stoppers[3].setRepeats(false);
    }

    // -------------------------- Misc  --------------------------

    public void checkHealth() {
        if(boss.getHP() <= 0) {
            ongoing = false;
            frames[0].dispose();
            for(int i = 0; i < frames2.length; i++) {
                frames2[i].dispose();
            }
            frames[2].setVisible(true);
            System.out.println("You win!");
        }
        else if(player.getHP() <= 0) {
            ongoing = false;
            frames[0].dispose();
            for(int i = 0; i < frames2.length; i++) {
                frames2[i].dispose();
            }
            frames[1].setVisible(true);
            System.out.println("You lose!");
        }
    }

    public void attackRelocated() {
        for(int i = 0; i < frames2.length; i++) {
            frames2[i].setLocation(rngWidth(), rngHeight());
        }
    }

    public int rngWidth() {
        int rng = width - (int)(Math.random()*width);
        if(rng >= width - 150) {
            rng -= 150;
        }
        else if(rng <= 150) {
            rng += 150;
        }
        return rng;
    }

    public int rngHeight() {
        int rng = height - (int)(Math.random()*height);
        if(rng >= height - 150) {
            rng -= 150;
        }
        else if(rng <= 150) {
            rng += 150;
        }
        return rng;
    }

    private void updateHealthBars() {
        labels[0].setText("HATRED BOSS HP: " + boss.getHP());
        labels[1].setText("YOUR HP: " + player.getHP());
    }

    public void show() {
        // Shows a frame becaue yes!
        this.frames[0].setVisible(true);
    }
    
}