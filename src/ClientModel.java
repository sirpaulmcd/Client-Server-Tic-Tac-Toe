/**
 * This class is the model of the client MVC pattern. It contains the 
 * tic-tac-toe game logic for the client.
 */
public class ClientModel 
{
    //=========================================================================
    // Instance variables
    //=========================================================================
    /**
     * The board that the game is to be played on.
     */
    private Board board;
    /**
     * The active player in the current turn.
     */
    private Player activePlayer;

    //=========================================================================
    // Constructor
    //=========================================================================
    /**
     * Constructs a ClientModel object.
     */
    public ClientModel() 
    {
        this.board = new Board();
    }

    //=========================================================================
    // Public methods
    //=========================================================================
    /**
     * Pauses the game until a valid move has been made (i.e. a valid move
     * button has been pressed).
     */
    public void performOneMove() 
    {
        activePlayer.makeMove();
    }

    /**
     * Checks if a position on the game board has not yet been claimed by a 
     * player.
     * @param position Int[] containing row and column integers respectively.
     * @return true if the space is currently empty. Otherwise, returns false.
     */
    public boolean isBlank(int[] position) 
    {
        return board.isBlank(position);
    }

    /**
     * Adds a player's marker to a given spot the board.
     * @param row The row in which the marker is to be placed on the game board.
     * @param col The col in which the marker is to be placed on the game board.
     * @param mark The player's marker.
     */
    public void addMark(int row, int col, char mark) 
    {
        board.addMark(row, col, mark);
    }

    /**
     * Makes the input move if the corresponding board location is free. 
     * @param move Int[] containing row and column integers respectively.
     * @return True if the move was made successfully, false otherwise.
     */
    public boolean attemptMove(int[] move) 
    {
        if (isBlank(move)) 
        {
            addMark(move[0], move[1], activePlayer.getMark());
            activePlayer.setHasPlayed(true);
            return true;
        } 
        return false;
    }

    /**
     * Gets the char array belonging to the currently active game board.
     */
    public char[][] getBoardArr() 
    { 
        return board.getBoard(); 
    }

    //=========================================================================
    // Getters and setters
    //=========================================================================
    public Board getBoard() { return board; }
    public void setBoard(Board board) { this.board = board; }
    public Player getActivePlayer() { return activePlayer; }
    public void setActivePlayer(Player activePlayer) { this.activePlayer = activePlayer; }
}