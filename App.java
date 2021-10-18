import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class App {
    static void showEndScreen(int score) {
        JDialog dialog = new JDialog();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(150, 100);
        dialog.setLocationRelativeTo(null);
        JLabel label = new JLabel("Your score: " + score, SwingConstants.CENTER);
        label.setFont(new Font("System", Font.PLAIN, 12));
        JButton button = new JButton("OK");
        button.setDefaultCapable(true);
        button.addActionListener(e -> dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING)));
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(label);
        panel.add(Box.createVerticalGlue());
        panel.add(button);
        dialog.add(panel);
        dialog.getRootPane().setDefaultButton(button);
        dialog.setVisible(true);
    }
    static public void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int dotSize = (int)Math.sqrt(screenSize.height * screenSize.width) / 12;
        Food food = new Food(dotSize / 2, dotSize / 2, 10, screenSize.width - 10, 10, screenSize.height - 10);
        Snake snake = new Snake(dotSize, dotSize, 0, 0, 0, screenSize.width, 0, screenSize.height);
        int score = 0;
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                break;
            }
            snake.moveSnake();
            if (snake.isDead()) {
                break;
            }
            else if (snake.collides(food)) {
                food.respawn();
                snake.grow();
                score += 1;
            }
        }
        showEndScreen(score);
        snake.dispose();
        food.dispose();
    }
}
