import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.ComponentOrientation;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.event.ActionListener;

/**
 * This class is the view of the client MVC pattern. It contains the 
 * tic-tac-toe GUI for the client.
 */
public class ClientView extends JFrame 
{
    //=========================================================================
    // Instance variables
    //=========================================================================
    /**
     * SerialVersionUID for serialization purposes.
     */
    private static final long serialVersionUID = 1L;

    //=========================================================================
    // North components for border layout
    /**
     * North panel of the frame. Contains the active player mark and name 
     * fields.
     */
    private JPanel northPanel;
    /**
     * Text field where the active player's marker is displayed.
     */
    private JTextField markField;
    /**
     * Text field where the name of the active player is displayed.
     */
    private JTextField nameField;

    //=========================================================================
    // Center components for border layout
    /**
     * Center panel of the frame. Contains the tic-tac-toe board buttons.
     */
    private JPanel centerPanel;
    /**
     * Button representing space (0,0) on the board.
     */
    private JButton r0c0Button;
    /**
     * Button representing space (0,1) on the board.
     */
    private JButton r0c1Button;
    /**
     * Button representing space (0,2) on the board.
     */
    private JButton r0c2Button;
    /**
     * Button representing space (1,0) on the board.
     */
    private JButton r1c0Button;
    /**
     * Button representing space (1,1) on the board.
     */
    private JButton r1c1Button;
    /**
     * Button representing space (1,2) on the board.
     */
    private JButton r1c2Button;
    /**
     * Button representing space (2,0) on the board.
     */
    private JButton r2c0Button;
    /**
     * Button representing space (2,1) on the board.
     */
    private JButton r2c1Button;
    /**
     * Button representing space (2,2) on the board.
     */
    private JButton r2c2Button;

    //=========================================================================
    // East components for border layout
    /**
     * East panel of the frame. Contains the prompt text area.
     */
    private JPanel eastPanel;
    /**
     * Text area where game dialog is displayed.
     */
    private JTextArea textArea;
    /**
     * Scroll pane used on textArea for when game dialog gets too long.
     */
    private JScrollPane scrollPane;

    //=========================================================================
    // Constructors
    //=========================================================================
    /**
     * Constructs the View component of the Client MVC pattern.
     */
    public ClientView() 
    {
        super("Tic-Tac-Toe");
        initVariables();
        initContainer();
        initPanels();
    }

    //=========================================================================
    // Public methods
    //=========================================================================
    /**
     * Toggles the tic-tac-toe buttons according to the input boolean.
     * @param enable Whether or not the buttons should be enabled.
     */
    public void enableButtons(boolean enable)
    {
        r0c1Button.setEnabled(enable);
        r0c0Button.setEnabled(enable);
        r0c2Button.setEnabled(enable);
        r1c0Button.setEnabled(enable);
        r1c1Button.setEnabled(enable);
        r1c2Button.setEnabled(enable);
        r2c0Button.setEnabled(enable);
        r2c1Button.setEnabled(enable);
        r2c2Button.setEnabled(enable);
    }

    /**
     * Updates the text markers on the JButtons.
     * @param board is a 2D array holding the game markers represented on a 3x3 game grid.
     */
    public void updateButtonText(char[][] board) 
    {
        r0c0Button.setText(board[0][0] + "");
        r0c1Button.setText(board[0][1] + "");
        r0c2Button.setText(board[0][2] + "");
        r1c0Button.setText(board[1][0] + "");
        r1c1Button.setText(board[1][1] + "");
        r1c2Button.setText(board[1][2] + "");
        r2c0Button.setText(board[2][0] + "");
        r2c1Button.setText(board[2][1] + "");
        r2c2Button.setText(board[2][2] + "");
    }

    //=========================================================================
    // Private methods
    //=========================================================================
    /**
     * Initializes the variables associated with the View.
     */
    private void initVariables() 
    {
        // North
        northPanel = new JPanel();
        markField = new JTextField(5);
        nameField = new JTextField(5);
        // Center
        centerPanel = new JPanel();
        r0c0Button = new JButton("");
        r0c1Button = new JButton("");
        r0c2Button = new JButton("");
        r1c0Button = new JButton("");
        r1c1Button = new JButton("");
        r1c2Button = new JButton("");
        r2c0Button = new JButton("");
        r2c1Button = new JButton("");
        r2c2Button = new JButton("");
        // East
        eastPanel = new JPanel();
        textArea = new JTextArea(10, 25);
        scrollPane = new JScrollPane(textArea);
        // Disable the tic-tac-toe buttons
        enableButtons(false);
        // Make textFields not editable
        makeTextFieldsUneditable();
    }

    /**
     * Sets all text fields as uneditable.
     */
    private void makeTextFieldsUneditable() 
    {
        markField.setEditable(false);
        nameField.setEditable(false);
        textArea.setEditable(false);
    }

    /**
     * Initializes the container/JFrame.
     */
    private void initContainer()
    {
        // Get container for frame components
        Container c = getContentPane();
        // Set program to stop upon the window closing
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Set the preferred size of the window
        setPreferredSize(new Dimension(500, 300));
        // Set layout for main window frame
        setLayout(new BorderLayout());
        // Place panels onto frame
        c.add("North", northPanel);
        c.add("Center", centerPanel);
        c.add("East", eastPanel);
        // Make the frame visible
        setVisible(true);
        pack();
    }

    /**
     * Initializes the panels.
     */
    private void initPanels()
    {
        // Set layout for center panel
        centerPanel.setLayout(new GridLayout(3, 3, 5, 5));
        // Place the components into their respective panels
        northPanel.add(new JLabel("Active Player:"));
        northPanel.add(nameField);
        northPanel.add(new JLabel("Mark:"));
        northPanel.add(markField);
        centerPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        centerPanel.add(r0c0Button);
        centerPanel.add(r0c1Button);
        centerPanel.add(r0c2Button);
        centerPanel.add(r1c0Button);
        centerPanel.add(r1c1Button);
        centerPanel.add(r1c2Button);
        centerPanel.add(r2c0Button);
        centerPanel.add(r2c1Button);
        centerPanel.add(r2c2Button);
        eastPanel.add(scrollPane);
    }

    //=========================================================================
    // Listener methods (Used by controller to add functionality to buttons)
    //=========================================================================
    public void addr0c0Listener(ActionListener listener) 
    {
        r0c0Button.addActionListener(listener);
    }

    public void addr0c1Listener(ActionListener listener) 
    {
        r0c1Button.addActionListener(listener);
    }

    public void addr0c2Listener(ActionListener listener) 
    {
        r0c2Button.addActionListener(listener);
    }

    public void addr1c0Listener(ActionListener listener) 
    {
        r1c0Button.addActionListener(listener);
    }

    public void addr1c1Listener(ActionListener listener) 
    {
        r1c1Button.addActionListener(listener);
    }

    public void addr1c2Listener(ActionListener listener) 
    {
        r1c2Button.addActionListener(listener);
    }

    public void addr2c0Listener(ActionListener listener) 
    {
        r2c0Button.addActionListener(listener);
    }

    public void addr2c1Listener(ActionListener listener) 
    {
        r2c1Button.addActionListener(listener);
    }

    public void addr2c2Listener(ActionListener listener) 
    {
        r2c2Button.addActionListener(listener);
    }

    //=========================================================================
    // Getters and setters
    //=========================================================================
    public void setMarkField(String text) { this.markField.setText(text); }
    public void setNameField(String text) { this.nameField.setText(text); }
    public void setTextArea(String text) { this.textArea.setText(text); }
}
