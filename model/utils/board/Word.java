package board;


import java.util.Arrays;

//this class represent a word in the game
public class Word {
    private Tile[] tiles; //tiles that make up the word
    private int row,col;//place of first tile
    private boolean vertical;//tells if the word is vertical or horizontal

    //creates word
    public Word(Tile[] tiles, int row, int col, boolean vertical) {
        this.tiles = tiles;
        this.row = row;
        this.col = col;
        this.vertical = vertical;
    }

    //gets all tiles of word
    public Tile[] getTiles() {
        return tiles;
    }

    //gets the row of first tile
    public int getRow() {
        return row;
    }

    //get column of first tile
    public int getCol() {
        return col;
    }

    //gets if word is vertical or horizontal
    public boolean isVertical() {
        return vertical;
    }

    //checks for equality of words
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return row == word.row && col == word.col && vertical == word.vertical && Arrays.equals(tiles, word.tiles);
    }

    //converts the word to a string
    @Override
    public String toString() {
        String s = "";
        for(Tile t : tiles)
        {
            s += t.letter;
        }
        return s;
    }
}
