import javax.swing.SwingUtilities;

/**
 * Tester for Virus_Boss
 *
 * @Henry. C
 * @5/27/25
 */
public class Game
{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
           @Override
           public void run() {
               Bossfight boss = new Bossfight();
               boss.show();
           }
        });
    }
}
