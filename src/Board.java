import java.io.Serializable;

/**
 * This class represents a tic-tac-toe board/grid. It contains the logic to
 * place markers and check win conditions.
 */
public class Board implements Serializable 
{
    //=========================================================================
    // Instance variables
    //=========================================================================
    /**
     * SerialVersionUID for serialization purposes.
     */
    private static final long serialVersionUID = -4309518207161622889L;
    /**
     * A 2D array representing a 3x3 tic-tac-toe grid.
     */
    private char[][] board;
    /**
     * A count for the number of turns that have been played.
     */
    private int markCount;

    //=========================================================================
    // Constructors
    //=========================================================================
    /**
     * Constructor for Board that sets markCount to zero and creates a 2D array
     * filled with space characters.
     */
    public Board() 
    {
        markCount = 0;
        board = new char[3][3];
        for (int i = 0; i < 3; i++) 
        {
            for (int j = 0; j < 3; j++) 
            {
                board[i][j] = ' ';
            }
        }
    }

    //=========================================================================
    // Public methods
    //=========================================================================
    /**
     * Checks a space to see if it is empty.
     * @param move Int[] containing row and column integers respectively.
     * @return True if space empty, false otherwise.
     */
    public boolean isBlank(int[] move) 
    {
        return board[move[0]][move[1]] == ' ';
    }

    /**
     * Checks to see if the board is full (9 moves have been executed).
     * @return True if the board is full.
     */
    public boolean isFull() 
    {
        return markCount >= 9;
    }

    /**
     * Checks if the game has been won by a player.
     * @return True if the game has been won, false otherwise.
     */
    public boolean hasWon() 
    {
        return isHorizontalWin() || isVerticalWin() || isDiagonalWin();
    }

    /**
     * Checks to see if the game has ended. The game is ended if either player
     * has won or the board is full. 
     * @return True if the game has ended, false otherwise.
     */
    public boolean hasEnded() 
    {
        return hasWon() || isFull();
    }

    /**
     * Adds input mark to a particular slot on the board and increments 
     * markCount.
     * @param row  Integer indicating row.
     * @param col  Integer indicating column.
     * @param mark Char indicating the player's mark (X or O)
     */
    public void addMark(int row, int col, char mark) 
    {
        board[row][col] = mark;
        markCount++;
    }

    //=========================================================================
    // Private methods
    //=========================================================================
    /**
     * Checks if a horizontal win case has occurred. 
     * @return True if a horizontal win case has occurred, false otherwise.
     */
    private boolean isHorizontalWin()
    {
        for (int row = 0; row < 3; row++)
        {
            if (board[row][0] == board[row][1] && board[row][1] == board[row][2] && board[row][2] != ' ')
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a vertical win case has occurred. 
     * @return True if a vertical win case has occurred, false otherwise.
     */
    private boolean isVerticalWin()
    {
        for (int col = 0; col < 3; col++)
        {
            if (board[0][col] == board[1][col] && board[1][col] == board[2][col] && board[2][col] != ' ')
            {
                return true;
            }   
        }
        return false;
    }

    /**
     * Checks if a diagonal win case has occurred. 
     * @return True if a diagonal win case has occurred, false otherwise.
     */
    private boolean isDiagonalWin()
    {
        return 
            (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] != ' ') ||
            (board[2][0] == board[1][1] && board[2][0] == board[0][2] && board[2][0] != ' ');
    }

    //=========================================================================
    // Getters and setters
    //=========================================================================
    public char[][] getBoard() { return board; }
}