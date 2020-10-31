import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 * This class is the controller of the client MVC pattern. It controls the
 * relationship between the tic-tac-toe game logic from ClientModel and the
 * GUI from ClientView. This controller is also responsible for communicating
 * with the tic-tac-toe server.
 */
public class ClientController
{
    //=========================================================================
    // Instance variables
    //=========================================================================
    /**
     * Is the socket used to communicate to and from the server.
     */
    private Socket socket;
    /**
     * Writer used to write strings to the server.
     */
    private PrintWriter socketOut;
    /**
     * Reader used to read strings from the server.
     */
    private BufferedReader socketIn;
    /**
     * Serialization stream used to read serialized objects from the server.
     */
    private ObjectInputStream objectInputStream;
    /**
     * Serialization stream used to write serialized objects to the server.
     */
    private ObjectOutputStream objectOutputStream;
    /**
     * GUI for the tic-tac-toe game.
     */
    private ClientView view;
    /**
     * Controller for the tic-tac-toe game on the client side.
     */
    private ClientModel model;
    /**
     * Boolean used to check if another client (and therefore player/opponent) has
     * been found to initiate a game of tic-tac-toe.
     */
    private boolean isLive;
    /**
     * Name of the client.
     */
    private String name;

    //=========================================================================
    // Constructors
    //=========================================================================
    /**
     * Constructs the client MVC pattern and connects to the server.
     * @param serverName The server name or IP address.
     * @param portNumber The server port number.
     */
    public ClientController(String serverName, int portNumber) 
    {
        connectToServer(serverName, portNumber);
        initClientVariables();
        addButtonFunctionality();
    }

    //=========================================================================
    // Public methods
    //=========================================================================
    /**
     * Sets up, runs, and ends the game and client connection.
     */
    public void communicate()
    {
        setUpGame();
        runGame();
        disconnectFromServer();
    }

    //=========================================================================
    // Private methods
    //=========================================================================
    /**
     * Initializes the connections between client and server.
     * @param serverName The server name or IP address.
     * @param portNumber The server port number.
     */
    private void connectToServer(String serverName, int portNumber)
    {
        try
        {
            socket = new Socket(serverName, portNumber);
            socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socketOut = new PrintWriter(socket.getOutputStream(), true);
        } 
        catch (UnknownHostException e) { e.printStackTrace(); } 
        catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * Initializes the client MVC model and game variables.
     */
    private void initClientVariables()
    {
        view = new ClientView();
        model = new ClientModel();
        isLive = false;
        name = null;
    }

    /**
     * Adds functionality to the buttons in the View.
     */
    private void addButtonFunctionality()
     {
        // Add button functionality
        view.addr0c0Listener((ActionEvent e) -> 
        {
            int[] move = { 0, 0 }; 
            handleMove(move);
        });
        view.addr0c1Listener((ActionEvent e) -> 
        {
            int[] move = { 0, 1 };
            handleMove(move);
        });
        view.addr0c2Listener((ActionEvent e) -> 
        {
            int[] move = { 0, 2 };
            handleMove(move);
        });
        view.addr1c0Listener((ActionEvent e) -> 
        {
            int[] move = { 1, 0 };
            handleMove(move);
        });
        view.addr1c1Listener((ActionEvent e) -> 
        {
            int[] move = { 1, 1 };
            handleMove(move);
        });
        view.addr1c2Listener((ActionEvent e) -> 
        {
            int[] move = { 1, 2 };
            handleMove(move);
        });
        view.addr2c0Listener((ActionEvent e) -> 
        {
            int[] move = { 2, 0 };
            handleMove(move);
        });
        view.addr2c1Listener((ActionEvent e) -> 
        {
            int[] move = { 2, 1 };
            handleMove(move);
        });
        view.addr2c2Listener((ActionEvent e) -> 
        {
            int[] move = { 2, 2 };
            handleMove(move);
        });
    }

    /**
     * Attempts to make the input move on the board. If the slot on the board
     * is already taken, prompts the user to select a blank space.
     * @param move Int[] containing row and column integers respectively.
     */
    private void handleMove(int[] move) 
    {
        if (!model.attemptMove(move)) 
        { 
            view.setTextArea("Please select a blank space."); 
        }
    }

    /**
     * Sets up the game by initializing the player and waiting for the game to
     * begin.
     */
    private void setUpGame()
    {
        setUpPlayer();
        waitForGameStart();
    }

    /**
     * Sets up the player by assigning the player name and adding the player to
     * the server hosted game.
     */
    private void setUpPlayer()
    {
        assignPlayerName();
        addPlayerToGame();
    }

    /**
     * Prompts the user to input their name. Repeats until a valid name is
     * recieved.
     */
    private void assignPlayerName() 
    {
        while (name == null || name.isEmpty() || name == "")
        {
            name = JOptionPane.showInputDialog("Please enter your name:", "");
        }
    }

    /**
     * Adds the player to the game hosted by the server.
     */
    private void addPlayerToGame()
    {
        try
        {
            view.setNameField(name);
            socketOut.println(name);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e) { e.printStackTrace(); } 
    }

    /**
     * Waits for the game to begin once the player has been initialized. Begins
     * the game once a start response is recieved from the server.
     */
    private void waitForGameStart()
    {
        try
        {
            view.setTextArea("Waiting for opponent...");
            while (!isLive) 
            {
                String response = socketIn.readLine();
                if (response.equals("Opponent found. A new game has started!")) 
                {
                    view.setTextArea(response);
                    isLive = true;
                }
            }
        }
        catch (IOException e) { e.printStackTrace(); } 
    }

    /**
     * Runs the game. Begins by waiting for a game session update from the 
     * server. Once an update is recieved, the local game session is updated to
     * reflect the current state of the game. The game session is then 
     * continued by the client.
     */
    private void runGame()
    {
        while (isLive) 
        {
            GameState gameState = recieveGameStateUpdate();
            if (gameState == null) { break; }
            updateClientGameState(gameState);
            continueGameState(gameState);
        }
    }

    /**
     * Recieves the latest version of the GameState from the server.
     * @return The most up-to-date version of the GameState if properly
     * received from the server, null otherwise.
     */
    private GameState recieveGameStateUpdate()
    {
        try { return (GameState)objectInputStream.readObject(); }
        catch (StreamCorruptedException e) 
        { 
            view.setTextArea("Your opponent has rage quit. Game over.");
            isLive = false;
        }
        catch (IOException e) { e.printStackTrace(); } 
        catch (ClassNotFoundException e) { e.printStackTrace(); }
        return null;
    }

    /**
     * Updates the local game state to reflect the newest GameState recieved
     * by the server.
     * @param gameState The current GameState instance.
     */
    private void updateClientGameState(GameState gameState) 
    {
        model.setBoard(gameState.getBoard());
        model.setActivePlayer(gameState.getActivePlayer());
        view.updateButtonText(model.getBoardArr());
        view.setNameField(gameState.getActiveName());
        view.setMarkField(gameState.getActiveMark());
    }

    /**
     * Continues the game session according to the newest updates. If the game
     * has not ended, a new turn is played. If a player has won the game, a
     * congratulations is displayed on the view. If no player has won the game,
     * a tie prompt is displayed on the view.
     * @param gameState The current GameState instance.
     */
    private void continueGameState(GameState gameState)
    {
        if (!gameState.hasEnded()) { processTurn(gameState); } 
        else if (gameState.hasWon()) { handleWinEnding(gameState); } 
        else { handleTieEnding(); }
    }

    /**
     * If it is the client's turn, a buttons are enabled and the client
     * performs their turn. If it is not the client's turn, they wait for 
     * their opponent to make thier move.
     * @param gameState The current GameState instance.
     */
    private void processTurn(GameState gameState)
    {
        // If active player
        if (gameState.getActiveName().equals(name)) 
        {
            playTurn(gameState);
        }
        // If non-active player
        else 
        {
            view.setTextArea(gameState.getActiveName() + " is making their move...");
        }
    }

    /**
     * Plays one turn by allowing the player to make a move, updating the
     * GameState to reflect that move, then sending a GameState update to
     * the server.
     * @param gameState The current GameState instance.
     */
    private void playTurn(GameState gameState)
    {
        setUpTurn();
        model.performOneMove();
        endTurn(gameState);
    }

    /**
     * Informs the user that it is their turn and enables the buttons.
     */
    private void setUpTurn()
    {
        view.setTextArea("It's your turn to make a move");
        view.enableButtons(true);
    }

    /**
     * Ends the turn by disabling buttons, updating the local GameState to 
     * reflect the latest changes, and sending a GameState update to the 
     * server.
     * @param gameState The current GameState instance.
     */
    private void endTurn(GameState gameState)
    {
        view.enableButtons(false);
        view.updateButtonText(model.getBoardArr());
        gameState.setBoard(model.getBoard());
        gameState.setActivePlayer(model.getActivePlayer());
        sendGameStateUpdate(gameState);
    }

    /**
     * Sends the most up-to-date version of the GameState to the server. 
     * @param gameState The current GameState instance.
     */
    private void sendGameStateUpdate(GameState gameState)
    {
        try { objectOutputStream.writeObject(gameState); }
        catch (IOException e) { e.printStackTrace(); } 
    }

    /**
     * Displays the winning player to the View and ends the game loop.
     * @param gameState The current GameState instance.
     */
    private void handleWinEnding(GameState gameState) 
    {
        view.setTextArea(gameState.getActiveName() + " has won!");
        isLive = false;
    }

    /**
     * Displays a tie prompt to the View and ends the game loop.
     */
    private void handleTieEnding() 
    {
        view.setTextArea("There was a tie!");
        isLive = false;
    }

    /**
     * Ends the connection between client and server.
     */
    private void disconnectFromServer() 
    {
        try 
        {
            socketIn.close();
            socketOut.close();
            objectInputStream.close();
            objectOutputStream.close();
        } 
        catch (IOException e) { e.printStackTrace(); }
    }

    //=========================================================================
    // Instance variables
    //=========================================================================
    public static void main(String[] args) throws IOException 
    {
        ClientController clientController = new ClientController("localhost", 9898);
        clientController.communicate();
    }
}