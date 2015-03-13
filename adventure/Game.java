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
  private Player player;
  private Room outside, theatre, pub, lab, office;
    private Room currentRoom;
  private int moves;
 
    

    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        Room startRoom = createRooms();
        player=new Player ("Christian",startRoom);
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private Room createRooms()
    {
        Room outside, tenniscourt,lobby, changingrooms, gym;
// create the rooms
        outside = new Room("outside the main entrance of the university gym");
        tenniscourt = new Room("into the tennis court");
        lobby = new Room("into the lobby of the gym");
        changingrooms= new Room("into the changing room");
        gym = new Room("into the gym area");

// initialise room exits
        outside.setExit("north",lobby);
        
        
        lobby.setExit("east", tenniscourt);
        lobby.setExit("south", outside);
        lobby.setExit("west", changingrooms);
        lobby.setExit("north", gym);

        tenniscourt.setExit("south", lobby);
        

        gym.setExit("south", lobby);

        changingrooms.setExit("south", lobby);
        changingrooms.setExit("north", gym);
        
// put items in the room
        lobby.addItem(new Item("security card", "a pass to the security office", 0.5));
        changingrooms.addItem(new Item("pound coin", "used to rent a locker", 0.75));
        tenniscourt.addItem(new Item("tennis balls", "a tennis ball coommonly use to play the sport", 30));
        gym.addItem(new Item("torch", "a torch to show you the way", 0.1));
        
// start game outside
        
        currentRoom = outside;

        return currentRoom;
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
        System.out.println("Welcome to the Treadmania ");
        System.out.println("Treadmania is a new, incredibly fantasic adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        player.setRoom(currentRoom);
        System.out.println(player.getRoom().getLongDescription());
        System.out.println(" You are currently facing " + player.getDirection());
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
        System.out.println("You are not motivated!");
        System.out.println("fitness is not an easy thing, however it looks like you need help!.");
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
        Room nextRoom = player.getRoom().getExit(player.getDirection().toString());

        if (nextRoom == null)
        {
            System.out.println("There is no door!");
        }
        else
        {
            player.setRoom(nextRoom);
            System.out.println(player.getRoom().getLongDescription());
            System.out.println("You are facing " + player.getDirection());
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
            player.turnLeft();
        }
        else if (direction.equals("right"))
        {
            player.turnRight();
        }
        else
        {
            System.out.println("You can not turn that way");
        }
        System.out.println("You are currently facing " + player.getDirection());
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
