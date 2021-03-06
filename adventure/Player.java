/**
 *  A class to represent an adventurer playing the exciting
 *  game of "Soc World". A player has a location (of class Room)
 * and is facing a particular direction.
 *
 *
 */

package adventure;

public class Player
{
    private Room here;
    private Direction facing;

    /**
     * Construct a player with "null" location
     * facing east
     * @param christian
     * @param startRoom
     */
    public Player(String christian, Room startRoom)
    {
        here = null;
        facing = Direction.NORTH;
    }

    /**
     * return the direction thie player is facing
     */
    public Direction getDirection()
    {
        return facing;
    }

    /**
     * set the direction the player is facing
     */

    public void setDirection(Direction d)
    {
        facing = d;
    }

    /**
     * return the room the player is currently in
     */
    public Room getRoom()
    {
        return here;
    }

    /**
     * set the players current location
     */

    public void setRoom(Room r)
    {
        here = r;
    }

    /**
     * make the player turn left
     */
    public void turnLeft()
    {
        if (facing == Direction.NORTH)
        {
            facing = Direction.WEST;
        }
        else if (facing == Direction.WEST)
        {
            facing = Direction.SOUTH;
        }
        else if (facing == Direction.SOUTH)
        {
            facing = Direction.EAST;
        }
        else
        {
            facing = Direction.NORTH;
        }
    }

    public void turnRight()
    {
    }
}

