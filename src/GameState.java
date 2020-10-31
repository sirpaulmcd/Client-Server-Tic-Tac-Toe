import java.io.Serializable;

/**
 * This class represents the current state of a tic-tac-toe game (i.e. markers 
 * on the board, which player's turn it is). It is used to communicate game
 * updates between client and server.
 */
public class GameState implements Serializable 
{
    //=========================================================================
    // Instance variables
    //=========================================================================
    /**
     * Is the tic-tac-toe game board in which game is played on.
     */
    private Board board;
    /**
     * Is the player who's current turn it is (i.e. the player who is currently
     * allowed to place a marker on the board).
     */
    private Player activePlayer;
    /**
     * SerialVersionUID for serialization purposes.
     */
    private static final long serialVersionUID = 3002053260033745936L;

    //=========================================================================
    // Constructors
    //=========================================================================
    /**
     * Constructs a GameState object which represents the current state of 
     * a game.
     */
    public GameState(Board board, Player activePlayer) 
    {
        this.board = board;
        this.activePlayer = activePlayer;
    }

    //=========================================================================
    // Public methods
    //=========================================================================
    /**
     * Checks to see if there has been a winner or if the game is tied.
     */
    public boolean hasEnded() 
    {
        return board.hasEnded();
    }

    /**
     * Checks to see which player ('X' or 'O') has won the game.
     */
    public boolean hasWon() 
    {
        return board.hasWon();
    }

    //=========================================================================
    // Getters and setters
    //=========================================================================
    public Board getBoard() { return board; }
    public void setBoard(Board board) { this.board = board; }
    public Player getActivePlayer() { return activePlayer; }
    public void setActivePlayer(Player activePlayer) { this.activePlayer = activePlayer; }
    public String getActiveName() { return activePlayer.getName(); }
    public String getActiveMark() { return activePlayer.getMark() + ""; }
}