/**
 * Representations for all the valid command words for the game
 * along with a string in a particular language.
 *
 *
 */
package adventure;

public enum CommandWord
{
    // A value for each command word along with its
    // corresponding user interface string.
    MOVE("move"), TURN("turn"), QUIT("quit"), HELP("help"), UNKNOWN("?");

    // The command string.
    private String commandString;

    /**
     * Initialise with the corresponding command word.
     *
     * @param commandString The command string.
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }

    /**
     * @return The command word as a string.
     */
    public String toString()
    {
        return commandString;
    }
}
