import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;

/**
 * This class represents a player in a game of tic-tac-toe. It contains
 * information specific to a player such as their name, mark, and corresponding
 * client socket.
 */
public class Player implements Serializable 
{
    //=========================================================================
    // Instance variables
    //=========================================================================
    /**
     * The name of the player.
     */
    private String name;
    /**
     * The marker ('X' or 'O') on the board the the player owns.
     */
    private char mark;
    /**
     * Whether or not the player has made a move on their turn.
     */
    private boolean hasPlayed;
    /**
     * Writer associated with the player's client.
     */
    private transient PrintWriter socketOut;
    /**
     * Reader associated with the player's client.
     */
    private transient BufferedReader socketIn;
    /**
     * Input stream associated with the player's client.
     */
    private transient ObjectInputStream objectInputStream;
    /**
     * Output stream associated with the player's client.
     */
    private transient ObjectOutputStream objectOutputStream;
    /**
     * SerialVersionUID for serialization purposes.
     */
    private static final long serialVersionUID = 720785984605791249L;

    //=========================================================================
    // Constructors
    //=========================================================================
    /**
     * Constructs a Player object that is associated with a client through
     * a socket.
     */
    public Player(Socket socket, String name, char mark) 
    {
        try 
        {
            this.name = name;
            this.mark = mark;
            socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socketOut = new PrintWriter((socket.getOutputStream()), true);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (IOException e) { e.printStackTrace(); }
    }

    //=========================================================================
    // Public methods
    //=========================================================================
    /**
     * Pauses the game until a valid move has been made (i.e. a valid move
     * button has been pressed).
     */
    public void makeMove() 
    {
        hasPlayed = false;
        while (!hasPlayed) 
        {
            try { Thread.sleep(0); }
            catch (Exception e) { e.printStackTrace(); }
        }
    }

    //=========================================================================
    // Getters and setters
    //=========================================================================
    public String getName() { return name; }
    public char getMark() { return mark; }
    public void setHasPlayed(boolean hasPlayed) { this.hasPlayed = hasPlayed; }
    public PrintWriter getSocketOut() { return socketOut; }
    public BufferedReader getSocketIn() { return socketIn; }
    public ObjectInputStream getObjectInputStream() { return objectInputStream; }
    public ObjectOutputStream getObjectOutputStream() { return objectOutputStream; }
}