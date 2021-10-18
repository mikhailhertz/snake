import javax.swing.*;

public class Dot extends JFrame {
    public Dot (int width, int height, int x, int y) {
        this.setSize(width, height);
        this.setLocation(x, y);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public boolean collides(Dot d) {
        return this.getBounds().intersects(d.getBounds());
    }
}
