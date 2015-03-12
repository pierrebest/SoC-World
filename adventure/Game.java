/**
 *  This class is the main class of the "SoC World" application. 
 *  "Soc World" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 *
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 *
 *  This class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *
 */
package adventure;

public class Game
{
    private Parser parser;
    private Player thePlayer;
    private Room currentRoom;
    private Theatre secondRoom;
    private ChangingRoom thirdRoom;
    private Lobby fourthRoom;

    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        thePlayer = new Player();
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office;
// create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");

        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        currentRoom = outside;  // start game outside
    }

    /**
     * Main play routine.  Loops until end of play.
     */
    public void play()
    {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished)
        {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the SoC World!");
        System.out.println("SoC World is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        thePlayer.setRoom(currentRoom);
        System.out.println(thePlayer.getRoom().getLongDescription());
        System.out.println(" You are facing " + thePlayer.getDirection());
    }

    /**
     * Given a command, process (that is: execute) the command.
     *
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord)
        {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case MOVE:
                move();
                break;
                
            case TURN:
                turn(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp()
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /**
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void move()
    {


        // Try to leave current room.
        Room nextRoom = thePlayer.getRoom().getExit(thePlayer.getDirection().toString());

        if (nextRoom == null)
        {
            System.out.println("There is no door!");
        }
        else
        {
            thePlayer.setRoom(nextRoom);
            System.out.println(thePlayer.getRoom().getLongDescription());
            System.out.println("You are facing " + thePlayer.getDirection());
        }
    }

    /**
     * "Turn" was entered. Check the rest of the command to see
     * whether we turn left or right.
     */
    private void turn(Command command)
    {
        if (!command.hasSecondWord())
        {
            // if there is no second word, we don't know which way to turn...
            System.out.println("turn which way?");
            return;
        }

        String direction = command.getSecondWord();
        if (direction.equals("left"))
        {
            thePlayer.turnLeft();
        }
        else if (direction.equals("right"))
        {
            thePlayer.turnRight();
        }
        else
        {
            System.out.println("You can not turn that way");
        }
        System.out.println("You are currently facing " + thePlayer.getDirection());
    }


    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     *
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command)
    {
        if (command.hasSecondWord())
        {
            System.out.println("Quit what?");
            return false;
        }
        else
        {
            return true;  // signal that we want to quit
        }
    }
}
