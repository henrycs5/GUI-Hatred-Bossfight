/**
 * Player movement??? REAL?
 *
 * @Henry. C
 * @5/28/25
 */
public class Player
{
    private int HP;

    public Player(int HP) {
        this.HP = HP;
    }

    public Player() {
        HP = 100;
    }

    public int getHP() {
        return HP;
    }

    public void damage(int val) {
        HP -= val;
    }
}
