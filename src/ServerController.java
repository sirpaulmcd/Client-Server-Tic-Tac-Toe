import java.io.IOException;
import java.net.SocketException;

/**
 * This class is the controller of the server MVC pattern. It is a runnable 
 * thread that controls the relationship between the tic-tac-toe game logic 
 * from ServerModel and handles game related communication between players.
 */
public class ServerController implements Runnable 
{
    //=========================================================================
    // Instance variables
    //=========================================================================
    /**
     * The model component of MVC on the server side. Responsible for server
     * side game logic.
     */
    private ServerModel model;

    //=========================================================================
    // Constructors
    //=========================================================================
    /**
     * Constructs a Game object capable of managing a game of tic-tac-toe
     * between two input players. Note that P2 is set active initially because
     * the active player is toggled at the beginning of every turn.
     * @param p1 The first player in the game.
     * @param p2 The second player in the game.
     */
    public ServerController(Player p1, Player p2) 
    {
        this.model = new ServerModel();
        model.setActivePlayer(p2);
        model.setIdlePlayer(p1);
    }

    //=========================================================================
    // Public methods
    //=========================================================================
    /**
     * Controls a new game of tic-tac-toe at a high level.
     */
    @Override
    public void run() 
    {
        try
        {
            notifyGameStart();
            while (!model.hasEnded()) { playTurn(); }
            endGame();
        }
        catch (SocketException e) { handleOpponentDisconnect(); }
    }

    //=========================================================================
    // Private methods
    //=========================================================================
    /**
     * Notifies both players that the game is about to begin. Note that a wait
     * time is added to allow the players to read the prompt before the game
     * begins.
     */
    private void notifyGameStart()
    {
        System.out.println("Game started between " + model.getIdlePlayer().getName() + 
            " and " + model.getActivePlayer().getName() + ".");
        model.getActiveSocketOut().println("Opponent found. A new game has started!");
        model.getIdleSocketOut().println("Opponent found. A new game has started!");
        sleep(2500);
    }

    /**
     * Puts the thread to sleep for the input amount of milliseconds.
     * @param milliseconds The number of milliseconds that the thread should be paused.
     */
    private void sleep(int milliseconds) 
    {
        try { Thread.sleep(milliseconds); } 
        catch (Exception e) { e.printStackTrace(); }
    }

    /**
     * Plays one turn of tic-tac-toe.
     * @throws SocketException Thrown when player disconnects.
     */
    private void playTurn() throws SocketException
    {
        model.toggleActivePlayer();
        sendGameStateInfo();
        updateServerGameState();
    }

    /**
     * Sends the most up-to-date details from the server's model to the 
     * players.
     * @throws SocketException Thrown when player disconnects.
     */
    private void sendGameStateInfo() throws SocketException
    {
        try
        {
            GameState gameState = new GameState(model.getBoard(), model.getActivePlayer());
            model.getActiveOutputStream().writeObject(gameState);
            model.getIdleOutputStream().writeObject(gameState);
        }
        catch (IOException e) { throw new SocketException(); }
    }

    /**
     * Take a game update from the client and applies them to the server's
     * game model.
     * @throws SocketException Thrown when player disconnects.
     */
    private void updateServerGameState() throws SocketException
    {
        try 
        {
            GameState gameState = (GameState) model.getActiveInputStream().readObject();
            model.setBoard(gameState.getBoard());
        }
        catch (IOException e) { throw new SocketException();  }
        catch (ClassNotFoundException e) { e.printStackTrace(); }
    }

    /**
     * Sends the ended GameState to the players and prints a game over prompt
     * to the server.
     * @throws SocketException Thrown when player disconnects.
     */
    private void endGame() throws SocketException
    {
        try
        {
            GameState gameState = new GameState(model.getBoard(), model.getActivePlayer());
            model.getActiveOutputStream().writeObject(gameState);
            model.getIdleOutputStream().writeObject(gameState);
            System.out.println("Game ended between " + model.getIdlePlayer().getName() + " and " + 
                model.getActivePlayer().getName() + ". " + model.getActivePlayer().getName() + " won!"); 
        }
        catch (IOException e) { throw new SocketException(); }
    }

    /**
     * Prints a disconnection prompt to the players and server.
     */
    private void handleOpponentDisconnect()
    {
        System.out.println("One or more players have rage quit. Ending game between " + 
            model.getIdlePlayer().getName() + " and " + model.getActivePlayer().getName() + ".");
        model.getActiveSocketOut().println("Your opponent has rage quit. Game over.");
        model.getIdleSocketOut().println("Your opponent has rage quit. Game over.");
    }
}