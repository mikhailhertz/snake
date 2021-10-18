import java.util.concurrent.ThreadLocalRandom;

public class Food extends Dot{
    final int playAreaXStart;
    final int playAreaXEnd;
    final int playAreaYStart;
    final int playAreaYEnd;
    public Food(int width, int height, int playAreaXStart, int playAreaXEnd, int playAreaYStart, int playAreaYEnd) {
        super(width, height,
                ThreadLocalRandom.current().nextInt(playAreaXStart, playAreaXEnd + 1),
                ThreadLocalRandom.current().nextInt(playAreaYStart, playAreaYEnd + 1));
        this.playAreaXStart = playAreaXStart;
        this.playAreaXEnd = playAreaXEnd;
        this.playAreaYStart = playAreaYStart;
        this.playAreaYEnd = playAreaYEnd;
    }
    int random(int x, int y) {
        return ThreadLocalRandom.current().nextInt(x, y + 1);
    }
    public void respawn() {
        this.setLocation(random(this.playAreaXStart, this.playAreaXEnd),
                random(this.playAreaYStart, this.playAreaYEnd));
    }
}
