import java.awt.*;
import java.awt.event.*;
import javax.swing.*; //used for panels, windows, buttons, etc.

public class TicTacToe {
    int boardWidth = 600;
    int boardHeight = 700; //50 px for text panel on top and restart button

    JFrame frame = new JFrame("Tic-Tac-Toe"); //the window the program will be on 
    JLabel textLabel = new JLabel(); //the label at the top of the window
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JButton restartButton = new JButton();

    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    int openSpace;
    boolean gameOver = false;


    TicTacToe() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);
        
        textPanel.setLayout((new BorderLayout()));
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel, BorderLayout.CENTER);

        restartButton.setBackground(Color.darkGray);
        restartButton.setForeground(Color.white);
        restartButton.setPreferredSize(new Dimension(boardWidth, 50));
        restartButton.setFont(new Font("Arial", Font.BOLD, 50));
        restartButton.setFocusPainted(false); //no dotted focus outline
        frame.add(restartButton, BorderLayout.SOUTH);
        //temporarily off until game over
        restartButton.setText("");
        restartButton.setEnabled(false);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) return;

                        JButton tile =  (JButton) e.getSource();
                        if (tile.getText().equals("")) {
                            tile.setText(currentPlayer);
                            if (currentPlayer.equals(playerX)) {
                                currentPlayer = playerO;
                                checkWinner();
                                if (gameOver) {
                                    textLabel.setText("Player X Wins");
                                    askReset();
                                } else {
                                textLabel.setText("O's Turn");
                                }
                            } else {
                                currentPlayer = playerX;
                                checkWinner();
                                if (gameOver) {
                                    textLabel.setText("Player O Wins");
                                    askReset();
                                } else {
                                textLabel.setText("X's Turn");
                                }
                            }
                        }
                        if (checkTie() == true && !gameOver) {
                            for (int r = 0; r < 3; r++) {
                                for (int c = 0; c < 3; c++) {
                                    board[r][c].setBackground(Color.black);
                                    board[r][c].setForeground(Color.gray);
                                }
                            }
                            textLabel.setText("Tie");
                            askReset();
                        }
                    }
                });
                
            }
        }
        
    }

    public boolean checkTie() {
        openSpace = 0;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c].getText().equals("")) {
                    openSpace++;
                }
            }
        }

        if (openSpace > 0) {
            return false;
        }
        return true;
    }

    void checkWinner() {
        //horizontal
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText() == "") {
                continue;
            } else {
                if (board[r][0].getText() == board[r][1].getText() &&
                    board[r][1].getText() == board[r][2].getText()) {
                    //change color
                    for (int x = 0; x < 3; x++) {
                        setWinner(board[r][x]);
                    }
                    gameOver = true;
                    return;
                }
            }
        }
        //vertical
        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText() == "") {
                continue;
            } else {
                if (board[0][c].getText() == board[1][c].getText() &&
                    board[1][c].getText() == board[2][c].getText()) {
                    //change color
                    for (int x = 0; x < 3; x++) {
                        setWinner(board[x][c]);
                    }
                    gameOver = true;
                    return;
                }
            }
        }
        //diagonal
        if (board[0][0].getText() == board[1][1].getText() &&
            board[1][1].getText() == board[2][2].getText() &&
            board[0][0].getText() != "") {
            //change color
            int c = 0;
            for (int r = 0; r < 3; r++) {
                setWinner(board[r][c]);
                c++;
            }
            gameOver = true;
            return;
        }
        if (board[0][2].getText() == board[1][1].getText() &&
            board[1][1].getText() == board[2][0].getText() &&
            board[0][2].getText() != "") {
            //change color
            int c = 2;
            for (int r = 0; r < 3; r++) {
                setWinner(board[r][c]);
                c--;
            }
            gameOver = true;
            return;
        }
    }

    void setWinner(JButton tile) {
        tile.setForeground(Color.gray);
        tile.setBackground(Color.black);
    }

    void askReset() {
        restartButton.setEnabled(true);
        restartButton.setText("Restart?");
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        board[r][c].setBackground(Color.darkGray);
                        board[r][c].setForeground(Color.white);
                        board[r][c].setText("");
                        gameOver = false;
                        restartButton.setEnabled(false);
                        restartButton.setText("");
                        textLabel.setText("Tic-Tac-Toe");
                    }
                }
            }
        });
    }
}