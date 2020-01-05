package de.mk.environment;

import de.mk.brain.ISnakeBrain;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    public static int FULL_LIFE = 200;

    public String name;
    public ISnakeBrain snakeBrain;
    public List<Coordinates> body = new ArrayList<>();
    public double fitness = 0;

    public int life = FULL_LIFE;
    public boolean alive = true;
    public int timeAlive = 0;

    public Snake(String name, ISnakeBrain snakeBrain){
        this.name = name;
        this.snakeBrain = snakeBrain;
    }

    public Snake clone(String name, boolean cloneBrain){
        ISnakeBrain brain = this.snakeBrain;
        if (cloneBrain) brain = this.snakeBrain.clone();
        Snake clone = new Snake(name, brain);
        return clone;
    }

    public void computeFitness(int runs){
        double fitness = 0.0;
        for (int j=0; j<runs; j++) {
            PlayingField playingField = new PlayingField();
            Snake clone = this.clone("Clone", false);
            playingField.setSnake(clone);
            playingField.moveSnakeUntilDead();
            fitness += clone.getScore();
        }
        this.fitness = fitness / runs;
    }

    public void setAtNewPosition(Coordinates coordinates){
        this.body = new ArrayList<>();
        this.body.add(coordinates);
    }

    public Coordinates getHead(){
        return this.body.get(0);
    }

    public int getScore(){
        return this.body.size()-1;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Coordinates coord : this.body){
            sb.append(coord.toString());
        }
        return sb.toString();
    }

}
