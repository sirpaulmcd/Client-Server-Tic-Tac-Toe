import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The class establishes connections between clients and the runnable
 * game thread (i.e. ServerController).
 */
public class Server
{
    //=========================================================================
    // Instance variables
    //=========================================================================
    /**
     * Socket used to establish connection between server and client(s).
     */
    private ServerSocket serverSocket;
    /**
     * Writer used to write out to socket.
     */
    private PrintWriter socketOut;
    /**
     * Reader used to read from socket.
     */
    private BufferedReader socketIn;
    /**
     * Thread pool used to run multiple instances of Game for clients.
     */
    private ExecutorService pool;
    /**
     * Wether or not the first player for a game has been found.
     */
    private boolean firstPlayerFound;

    //=========================================================================
    // Constructors
    //=========================================================================
    /**
     * Constructs the Server object capable of managing 5 parallel tic-tac-toe
     * game instances through port 9898.
     */
    public Server() 
    {
        try 
        {
            firstPlayerFound = false;
            serverSocket = new ServerSocket(9898);
            pool = Executors.newFixedThreadPool(5);
            System.out.println("Server is running...");
        } 
        catch (IOException e) { e.printStackTrace(); }
    }

    //=========================================================================
    // Public methods
    //=========================================================================
    /**
     * Continually attempts to host new tic-tac-toe games until the program
     * is stopped.
     */
    public void runServer() 
    {
        try { while (true) { startNewGame(); } }
        catch (Exception e) { e.printStackTrace(); }
        finally { closeServer(); }
    }

    //=========================================================================
    // Private methods
    //=========================================================================
    /**
     * Starts a new game instance of tic-tac-toe. Once two clients connect and
     * send their name, a game is initialized and started between them.
     */
    private void startNewGame()
    {
        Player p1 = getPlayer();
        Player p2 = getPlayer();
        pool.execute(new ServerController(p1, p2));
    }

    /**
     * Connects to client and creates a valid Player object.
     * @return Player object ready to be connected to a game instance.
     */
    private Player getPlayer() 
    {
        while (true)
        {
            try 
            {
                Socket socket = serverSocket.accept();
                socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                socketOut = new PrintWriter((socket.getOutputStream()), true);
                Player player = new Player(socket, socketIn.readLine(), getPlayerMark());
                firstPlayerFound = !firstPlayerFound;
                System.out.println(player.getName() + " connected.");
                return player;
            } 
            catch (IOException e) { System.out.println("Problem setting up player. Retrying..."); }
        }
    }

    /**
     * Sources the appropriate player mark for the newly created player.
     */
    private char getPlayerMark()
    {
        if (!firstPlayerFound) { return 'X'; }
        else return 'O';
    }

    /**
     * Closes the server sockets.
     */
    private void closeServer()
    {
        try 
        {
            socketIn.close();
            socketOut.close();
        } 
        catch (IOException e) { System.out.println(e.getMessage()); }
    }

    //=========================================================================
    // Main
    //=========================================================================
    public static void main(String[] args) throws IOException 
    {
        Server server = new Server();
        server.runServer();
    }
}