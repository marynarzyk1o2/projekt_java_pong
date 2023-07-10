import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PongGame extends JPanel implements KeyListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PADDLE_WIDTH = 20;
    private static final int PADDLE_HEIGHT = 100;
    private static final int BALL_SIZE = 20;
    private static final int PADDLE_SPEED = 10;

    private int paddle1Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
    private int paddle2Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
    private int ballX = WIDTH / 2 - BALL_SIZE / 2;
    private int ballY = HEIGHT / 2 - BALL_SIZE / 2;
    private int ballXSpeed = -3;
    private int ballYSpeed = -3;
    private int score1 = 0;
    private int score2 = 0;

    public PongGame() {
        JFrame frame = new JFrame("Pong");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.addKeyListener(this);
        frame.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.WHITE);
        g.fillRect(10, paddle1Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillRect(WIDTH - PADDLE_WIDTH - 10, paddle2Y, PADDLE_WIDTH, PADDLE_HEIGHT);

        g.fillRect(ballX, ballY, BALL_SIZE, BALL_SIZE);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Gracz 1: " + score1, 10, 30);
        g.drawString("Gracz 2: " + score2, WIDTH - 100, 30);
        g.drawString("Przyciski:", WIDTH / 2 - 50, 30);
        g.drawString("Gracz 1: W, S", WIDTH / 2 - 70, 60);
        g.drawString("Gracz 2: Strzałka w górę, Strzałka w dół", WIDTH / 2 - 90, 90);
    }

    public void movePaddle1Up() {
        if (paddle1Y - PADDLE_SPEED > 0) {
            paddle1Y -= PADDLE_SPEED;
        }
    }

    public void movePaddle1Down() {
        if (paddle1Y + PADDLE_HEIGHT + PADDLE_SPEED < HEIGHT) {
            paddle1Y += PADDLE_SPEED;
        }
    }

    public void movePaddle2Up() {
        if (paddle2Y - PADDLE_SPEED > 0) {
            paddle2Y -= PADDLE_SPEED;
        }
    }

    public void movePaddle2Down() {
        if (paddle2Y + PADDLE_HEIGHT + PADDLE_SPEED < HEIGHT) {
            paddle2Y += PADDLE_SPEED;
        }
    }

    public void moveBall() {
        ballX += ballXSpeed;
        ballY += ballYSpeed;

        if (ballY <= 0 || ballY >= HEIGHT - BALL_SIZE) {
            ballYSpeed *= -1;
        }

        if (ballX <= PADDLE_WIDTH + 10 && ballY + BALL_SIZE >= paddle1Y && ballY <= paddle1Y + PADDLE_HEIGHT) {
            ballXSpeed *= -1;
        }

        if (ballX >= WIDTH - PADDLE_WIDTH - BALL_SIZE - 10 && ballY + BALL_SIZE >= paddle2Y && ballY <= paddle2Y + PADDLE_HEIGHT) {
            ballXSpeed *= -1;
        }

        if (ballX <= 0) {
            score2++;
            reset();
        } else if (ballX >= WIDTH - BALL_SIZE) {
            score1++;
            reset();
        }
    }

    public void reset() {
        ballX = WIDTH / 2 - BALL_SIZE / 2;
        ballY = HEIGHT / 2 - BALL_SIZE / 2;
        ballXSpeed *= -1;
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W) {
            movePaddle1Up();
        } else if (keyCode == KeyEvent.VK_S) {
            movePaddle1Down();
        } else if (keyCode == KeyEvent.VK_UP) {
            movePaddle2Up();
        } else if (keyCode == KeyEvent.VK_DOWN) {
            movePaddle2Down();
        }
    }

    public void keyTyped(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        PongGame game = new PongGame();

        while (true) {
            game.moveBall();
            game.repaint();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
