package de.mk.brain;

import de.mk.environment.*;

/**
 * This is an algorithm GitHub Copilot came up with.
 * It needed a little help (primarily to stop it from cheating ;-) but did a great job otherwise.
 */
public class CopilotBrain implements ISnakeBrain, Cloneable {

    /**
     * Initializes the first generation brain.
     * @return a new CopilotBrain2 instance
     */
    @Override
    public CopilotBrain initializeFirstGenerationBrain() {
        return new CopilotBrain();
    }

    @Override
    public CopilotBrain clone() {
        try {
            return (CopilotBrain) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Can't happen
        }
    }

    /**
     * Plans the next move for the snake based on the current state of the playing field.
     *
     * @param snake the snake
     * @param playingField the playing field
     * @return the coordinates of the next move
     */
    @Override
    public Coordinates planMove(Snake snake, PlayingField playingField) {
        Coordinates head = snake.getHead();
        Coordinates food = new Coordinates(playingField.getFoodPosition()[0], playingField.getFoodPosition()[1]);

        Direction bestDirection = new Direction(0);
        double shortestDistance = Double.MAX_VALUE;

        for (int i = 0; i < 4; i++) {
            Direction direction = new Direction(i);
            Coordinates next = direction.apply(head);
            if (isSafe(next, playingField)) {
                double distance = distanceTo(next, food);
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    bestDirection = direction;
                }
            }
        }

        return bestDirection.apply(head);
    }

    private double distanceTo(Coordinates from, Coordinates to) {
        return Math.sqrt(Math.pow(from.x - to.x, 2) + Math.pow(from.y - to.y, 2));
    }

    /**
     * Checks if the given coordinates are safe for the snake to move to.
     *
     * @param coordinates the coordinates to check
     * @param playingField the playing field
     * @return true if the coordinates are safe, false otherwise
     */
    private boolean isSafe(Coordinates coordinates, PlayingField playingField) {
        int x = coordinates.x;
        int y = coordinates.y;
        return x >= 0 && x < playingField.getWidth() &&
                y >= 0 && y < playingField.getHeight() &&
                playingField.getAt(x, y) != Thing.WALL &&
                playingField.getAt(x, y) != Thing.SNAKE_BODY;
    }
}