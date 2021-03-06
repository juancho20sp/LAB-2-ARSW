package snakepackage;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

import enums.GridSize;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jd-
 *
 */
public class SnakeApp {

    private static SnakeApp app;
    public static final int MAX_THREADS = 8;
    Snake[] snakes = new Snake[MAX_THREADS];
    private static final Cell[] spawn = {
        new Cell(1, (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell(GridSize.GRID_WIDTH - 2,
        3 * (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell(3 * (GridSize.GRID_WIDTH / 2) / 2, 1),
        new Cell((GridSize.GRID_WIDTH / 2) / 2, GridSize.GRID_HEIGHT - 2),
        new Cell(1, 3 * (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell(GridSize.GRID_WIDTH - 2, (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell((GridSize.GRID_WIDTH / 2) / 2, 1),
        new Cell(3 * (GridSize.GRID_WIDTH / 2) / 2,
        GridSize.GRID_HEIGHT - 2)};
    private JFrame frame;
    private static Board board;
    int nr_selected = 0;
    Thread[] thread = new Thread[MAX_THREADS];

    // $
    private boolean isGameStarted = false;
    private final Object pauseLock = new Object();
    private Snake longestSnake = null;
    private Snake firstToDie = null;

    private JPanel actionsBPabel = null;

    public SnakeApp() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame("The Snake Race");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(618, 640);
        frame.setSize(GridSize.GRID_WIDTH * GridSize.WIDTH_BOX + 17,
                GridSize.GRID_HEIGHT * GridSize.HEIGH_BOX + 40);
        frame.setLocation(dimension.width / 2 - frame.getWidth() / 2,
                dimension.height / 2 - frame.getHeight() / 2);
        board = new Board();
        
        
        frame.add(board,BorderLayout.CENTER);
        
        this.actionsBPabel=new JPanel();
        actionsBPabel.setLayout(new FlowLayout());
        actionsBPabel.add(new JButton("Action "));

        // $
        JButton startButton = new JButton("Iniciar");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        actionsBPabel.add(startButton);

        JButton stopButton = new JButton("Pausar");
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pauseGame();
            }
        });
        actionsBPabel.add(stopButton);

        JButton resumeButton = new JButton("Reanudar");
        resumeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resumeGame();
            }
        });
        actionsBPabel.add(resumeButton);

        frame.add(actionsBPabel,BorderLayout.SOUTH);

    }

    public static void main(String[] args) {
        app = new SnakeApp();
        app.init();
    }

    private void init() {
        for (int i = 0; i != MAX_THREADS; i++) {
            
            snakes[i] = new Snake(i + 1, spawn[i], i + 1, pauseLock);
            snakes[i].addObserver(board);
            thread[i] = new Thread(snakes[i]);
            // $
            // thread[i].start();
        }

        frame.setVisible(true);

        boolean isFirstDead = false;
        while (true) {
            // $ -> ELIMINACI??N DE ZONAS CR??TICAS
            // int x = 0;
            AtomicInteger x = new AtomicInteger(0);

            for (int i = 0; i != MAX_THREADS; i++) {
                if (snakes[i].isSnakeEnd() == true) {
                    // $ -> ELIMINACI??N DE ZONAS CR??TICAS
                    // x++;
                    x.getAndIncrement();

                    if (!isFirstDead) {
                        this.firstToDie = snakes[i];

                        System.out.println(" ------------ ");
                        System.out.println("La primera serpiente en morir es: " + this.firstToDie.getIdt());
                        System.out.println(" ------------ ");

                        isFirstDead = true;
                    }
                }
            }
            // $ -> ELIMINACI??N DE ZONAS CR??TICAS
            // if (x == MAX_THREADS) {
            if (x.get() == MAX_THREADS) {
                break;
            }
        }


        System.out.println("Thread (snake) status:");
        for (int i = 0; i != MAX_THREADS; i++) {
            System.out.println("["+i+"] :"+thread[i].getState());
        }
        

    }

    // $
    private void startGame() {
        if (!isGameStarted) {
            for (int i = 0; i != MAX_THREADS; i++) {
                thread[i].start();
            }

            this.isGameStarted = true;
        }
    }

    // $
    private void pauseGame() {
        for (int i = 0; i != MAX_THREADS; i++) {
            snakes[i].pauseSnake();
        }

        calculateLongestSnake();
        System.out.println(" ------------ ");
        System.out.println("Serpiente m??s larga: " + this.longestSnake.getBody().size());
        System.out.println(" ------------ ");
    }

    // $
    private void resumeGame() {
        for (int i = 0; i != MAX_THREADS; i++) {
            snakes[i].resumeSnake();
        }

        synchronized (pauseLock) {
            pauseLock.notifyAll();
        }
    }

    public static SnakeApp getApp() {
        return app;
    }

    // $
    private void calculateLongestSnake() {
        Snake longest = null;
        int size = -1;

        for (int i = 0; i != MAX_THREADS; i++) {
            if (snakes[i] != null && (snakes[i].getBody().size() > size)) {
                size = snakes[i].getBody().size();
                longest = snakes[i];
            }
        }

        this.setLongestSnake(longest);
    }

    public Snake getLongestSnake() {
        if (this.longestSnake != null) {
            return longestSnake;
        } else {
            return null;
        }
    }

    public void setLongestSnake(Snake longestSnake) {
        this.longestSnake = longestSnake;
    }
}
