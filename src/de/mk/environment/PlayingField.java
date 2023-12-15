package de.mk.environment;

public class PlayingField {

    private static int WIDTH = 25;
    private static int HEIGHT = 20;

    private int[][] field;

    private int[] foodPosition = {-1,-1};

    public Snake snake;

    public PlayingField(){
        this.field = new int[WIDTH][HEIGHT];
        this.createWalls();
        this.placeRandomFood();
    }

    public void setSnake(Snake snake){
        this.snake = snake;
        Coordinates coordinates = this.findStartCoordinatesForSnake();
        this.snake.setAtNewPosition(coordinates);
        this.field[coordinates.x][coordinates.y]=Thing.SNAKE_HEAD;
    }

    public void moveSnake(){
        Coordinates oldHead = snake.getHead();
        Coordinates moveTo = snake.snakeBrain.planMove(this.snake, this);
        Coordinates moveOut = this.snake.body.get(this.snake.body.size() - 1);
        if (snakeJustDied(moveTo)){
            this.snake.alive = false;
            return;
        }
        this.snake.timeAlive++;
        if (!snakeJustHasEaten(moveTo)){
            this.snake.body.add(0, moveTo);
            this.setAt(oldHead, Thing.SNAKE_BODY);
            this.setAt(moveTo, Thing.SNAKE_HEAD);
            this.snake.body.remove(moveOut);
            this.setAt(moveOut, Thing.BACKGROUND);
        }else{
            this.setAt(oldHead, Thing.SNAKE_BODY);
            this.snake.body.add(0, moveTo);
            this.setAt(moveTo, Thing.SNAKE_BODY);
            this.placeRandomFood();
            this.snake.life = Snake.FULL_LIFE;
        }
        this.snake.life--;
        if (0>=this.snake.life) this.snake.alive = false;
    }

    public void moveSnakeUntilDead(){
        while (this.snake.alive){
            this.moveSnake();
        }
    }

    public int getWidth(){
        return WIDTH;
    }

    public int getHeight(){
        return HEIGHT;
    }

    public int getAt(int x, int y){
        return this.field[x][y];
    }

    public int getAt(Coordinates coordinates){
        return this.field[coordinates.x][coordinates.y];
    }

    public int[] getFoodPosition(){
        return this.foodPosition;
    }

    public boolean is(Coordinates coordinates, int value){
        return this.getAt(coordinates)==value;
    }

    private void createWalls(){
        for (int i=0; i<WIDTH; i++){
            field[i][0] = Thing.WALL;
            field[i][HEIGHT-1] = Thing.WALL;
        }
        for (int i=0; i<HEIGHT; i++){
            field[0][i] = Thing.WALL;
            field[WIDTH-1][i] = Thing.WALL;
        }
    }

    private void placeRandomFood(){
        boolean placed = false;
        while(!placed) {
            int x = (int) (Math.random() * (WIDTH - 2)) + 1;
            int y = (int) (Math.random() * (HEIGHT - 2)) + 1;
            if (this.field[x][y]==Thing.BACKGROUND){
                field[x][y] = Thing.FOOD;
                this.foodPosition[0] = x;
                this.foodPosition[1] = y;
                placed = true;
            }
        }
    }

    private Coordinates findStartCoordinatesForSnake(){
        boolean success = false;
        int x = 0;
        int y = 0;
        while (!success) {
            x = (int) (Math.random() * (WIDTH - 2)) + 1;
            y = (int) (Math.random() * (HEIGHT - 2)) + 1;
            if (this.getAt(x, y) == Thing.BACKGROUND) success = true;
        }
        return new Coordinates(x,y);
    }

    private boolean snakeJustDied(Coordinates moveTo){
        if (this.getAt(moveTo) == Thing.WALL){
            return true;
        }else{
            return this.getAt(moveTo) == Thing.SNAKE_BODY;
        }
    }

    private boolean snakeJustHasEaten(Coordinates moveTo){
        return this.is(moveTo, Thing.FOOD);
    }

    private void setAt(Coordinates coordinates, int value){
        this.field[coordinates.x][coordinates.y] = value;
    }

}
