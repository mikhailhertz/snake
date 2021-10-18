import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Snake extends Dot implements KeyListener {
    int x;
    int y;
    final int playAreaXStart;
    final int playAreaXEnd;
    final int playAreaYStart;
    final int playAreaYEnd;
    final ArrayList<Dot> tail = new ArrayList<>();
    // contains offsets corresponding to key presses
    final HashMap<Integer, Point> map = new HashMap<>();
    public Snake(int width, int height, int x, int y, int playAreaXStart, int playAreaXEnd, int playAreaYStart, int playAreaYEnd) {
        super(width, height, x, y);
        JLabel label = new JLabel(":^)", SwingConstants.CENTER);
        label.setFont(new Font("System", Font.PLAIN, height / 2));
        this.add(label);
        addKeyListener(this);
        this.setFocusable(true);
        this.toFront();
        this.requestFocus();
        this.x = x;
        this.y = y;
        this.playAreaXStart = playAreaXStart;
        this.playAreaXEnd = playAreaXEnd;
        this.playAreaYStart = playAreaYStart;
        this.playAreaYEnd = playAreaYEnd;
        // calculate directional offsets
        this.map.put(KeyEvent.VK_LEFT,  new Point(-(this.getWidth() / 3), 0));
        this.map.put(KeyEvent.VK_RIGHT,  new Point(this.getWidth() / 3, 0));
        this.map.put(KeyEvent.VK_UP,  new Point(0, -(this.getHeight() / 3)));
        this.map.put(KeyEvent.VK_DOWN,  new Point(0, this.getHeight() / 3));
    }
    int keyDirection = KeyEvent.VK_RIGHT;
    boolean boost = false;
    boolean needsToGrow = false;
    public void moveSnake() {
        int prevX = this.x;
        int prevY = this.y;
        Point p = this.map.get(keyDirection);
        this.x += (boost) ? p.x * 1.5 : p.x;
        this.y += (boost) ? p.y * 1.5 : p.y;
        this.setLocation(this.x, this.y);
        if (this.needsToGrow) {
            // insert a new dot behind snake's head instead of moving
            this.tail.add(new Dot(this.getWidth(), this.getHeight(), prevX, prevY));
            this.needsToGrow = false;
        }
        else {
            // move dot to the next dot's location
            for (int i = tail.size() - 1; i >= 0; i--) {
                int tempX = prevX;
                int tempY = prevY;;
                prevX = tail.get(i).getX();
                prevY = tail.get(i).getY();
                tail.get(i).setLocation(tempX, tempY);
            }
        }
        this.toFront();
        this.requestFocus();
    }
    public boolean isDead() {
// https://stackoverflow.com/questions/17095324/fastest-way-to-determine-if-an-integer-is-between-two-integers-inclusive-with
        if (!(Integer.toUnsignedLong(this.x - playAreaXStart) <= (playAreaXEnd - playAreaXStart)) ||
                !(Integer.toUnsignedLong(this.y - playAreaYStart) <= (playAreaYEnd - playAreaYStart))) {
            return true; // if snake went off screen
        }
        for (int i = 0; i < tail.size() - 5; i++) {
            if (tail.get(i).collides(this)) {
                return true; // if snake collided with itself
            }
        }
        return false;
    }
    public void grow() {
        this.needsToGrow = true;
    }
    @Override
    public void dispose() {
        for (Dot d : this.tail) {
            d.dispose();
        }
        super.dispose();
    }
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN -> keyDirection = keyEvent.getKeyCode();
            case KeyEvent.VK_SHIFT -> boost = true;
        }
    }
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_SHIFT -> boost = false;
        }
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) {}
}
