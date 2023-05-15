package board;


import java.util.ArrayList;
import server.DictionaryManager;


//this class represent the board of the game
public class Board {

    private DictionaryManager dm;//dictionary manager of all words

    //this class represent a square of the board
    private static class BoardSquare{
        private Tile tile;//current tile in the square
        private int word_multiplier;//word multiplier of square
        private int letter_multiplier;//letter multiplier of square

        //constructor
        public BoardSquare() {
            this.tile = null;
            this.word_multiplier = 1;
            this.letter_multiplier = 1;
        }

        //gets tile of the square
        public Tile getTile() {
            return tile;
        }

        //sets tile in the square
        public void setTile(Tile tile) {
            this.tile = tile;
        }

        //gets word multiplier in the square
        public int getWordMultiplier() {
            return word_multiplier;
        }

        //sets word multiplier of the square
        public void setWordMultiplier(int word_multiplier) {
            this.word_multiplier = word_multiplier;
        }

        //gets letter multiplier of the square
        public int getLetterMultiplier() {
            return letter_multiplier;
        }

        //sets letter multiplier of the square
        public void setLetterMultiplier(int letter_multiplier) {
            this.letter_multiplier = letter_multiplier;
        }
    }
    //checks if board has no tiles
    private boolean is_empty = true;
    //board reference
    private static Board board_ref = null;
    //size of row/col of board
    private static final int BOARD_SIZE = 15;
    //array of board squares
    private BoardSquare[][] squares;

    //generates double word multiplier in squares
    private void generateDoubleWordMul(){
        for(int i = 1;i< 5;i++){
            squares[i][i].setWordMultiplier(2);
            squares[i][squares.length - i - 1].setWordMultiplier(2);
            squares[squares.length - i - 1][i].setWordMultiplier(2);
            squares[squares.length - i - 1][squares.length - i - 1].setWordMultiplier(2);
        }
    }

    //generates triple word multiplier in squares
    private void generateTripleWordMul(){
        for(int i = 0;i<squares.length; i+= 7){
            for(int j = 0;j<squares[i].length; j += 7) {
                if(i == BOARD_SIZE/2 && j == BOARD_SIZE/2)
                    continue;
                squares[i][j].setWordMultiplier(3);
            }
        }
    }

    //generates double letter multiplier in squares
    private void generateDoubleLetterMul(){
        int[] row_arr = {0,0,2,2,3,6};
        int[] col_arr = {3,11,6,8,7,8};
        for(int i = 0 ; i< row_arr.length ; i++)
        {
            squares[row_arr[i]][col_arr[i]].setLetterMultiplier(2);
            squares[col_arr[i]][BOARD_SIZE-row_arr[i]-1].setLetterMultiplier(2);
            squares[BOARD_SIZE-row_arr[i]-1][BOARD_SIZE-col_arr[i]-1].setLetterMultiplier(2);
            squares[BOARD_SIZE-col_arr[i]-1][row_arr[i]].setLetterMultiplier(2);
        }
    }

    //generates triple multiplier in squares
    private void generateTripleLetterMul(){
        for(int i = 1;i< squares.length-1; i += 4){
            for(int j = 1;j<squares[i].length -1 ; j += 4){
                squares[i][j].setLetterMultiplier(3);
            }
        }
    }


    //generates all multipliers on squares
    private void generateBoard()
    {
        squares[BOARD_SIZE/2][BOARD_SIZE/2].setWordMultiplier(2);//setting middle bonus
        generateTripleLetterMul();
        generateDoubleWordMul();
        generateTripleWordMul();
        generateDoubleLetterMul();
    }
    //constructor
    public Board()
    {
        dm = DictionaryManager.get();
        this.squares = new BoardSquare[BOARD_SIZE][BOARD_SIZE];
        for(int i = 0 ; i<squares.length;i++){
            for(int j = 0 ; j < squares[i].length; j++){
                squares[i][j] = new BoardSquare();
            }
        }
        this.generateBoard();
    }

    //gets board reference
    public static Board getBoard()
    {
        if(board_ref == null)
            board_ref = new Board();
        return board_ref;
    }

    //gets array of tiles of the board
    public Tile[][] getTiles(){
        Tile[][] board_tiles_copy = new Tile[BOARD_SIZE][BOARD_SIZE];
        for(int i = 0 ;i<BOARD_SIZE ;i++){
            for(int j = 0;j<BOARD_SIZE;j++){
                board_tiles_copy[i][j] = squares[i][j].getTile();
            }
        }
        return board_tiles_copy;
    }

    //checks if a vertical word is leaning on another word on the board
    private boolean isWordLeanVert(Word w)
    {
        boolean is_lean = false;
        int row = w.getRow();
        while(row < w.getRow() + w.getTiles().length){
            if(w.getCol() > 0 && squares[row][w.getCol()-1].getTile() != null){
                is_lean = true;
                break;
            }
            if(w.getCol() < BOARD_SIZE - 1 && squares[row][w.getCol()+1].getTile() != null){
                is_lean = true;
                break;
            }
            row++;
        }
        if(w.getRow() > 0 && squares[w.getRow() - 1][w.getCol()].getTile() != null)
            is_lean = true;
        if(w.getRow() + w.getTiles().length < BOARD_SIZE &&
                squares[w.getRow()+w.getTiles().length + 1][w.getCol()].getTile() != null)
            is_lean = true;
        return is_lean;
    }

    //checks if a vertical word is legal
    private boolean vertBoardLegal(Word w)
    {
        if (w.getRow() + w.getTiles().length >= BOARD_SIZE)
            return false;
        if(is_empty)
        {
            if(w.getCol() != BOARD_SIZE/2)
                return false;
            if(w.getRow() > BOARD_SIZE/2 || w.getRow() + w.getTiles().length - 1 < BOARD_SIZE/2)
                return false;
        }
        //no replacing tiles check
        int row = w.getRow();
        for(Tile t : w.getTiles()) {
            if ((squares[row][w.getCol()].getTile() != null && t != null) ||
                    (squares[row][w.getCol()].getTile() == null && t == null))
                return false;
            row++;
        }
        if(!is_empty)
            return isWordLeanVert(w);
        return true;
    }

    //checks if a horizontal word is leaning on another word on the board
    private boolean isWordLeanHoriz(Word w)
    {
        boolean is_lean = false;
        int col = w.getCol();
        while(col < w.getCol() + w.getTiles().length){
            if(w.getRow() > 0 && squares[w.getRow()-1][col].getTile() != null){
                is_lean = true;
                break;
            }
            if(w.getRow() < BOARD_SIZE - 1 && squares[w.getRow()+1][col].getTile() != null){
                is_lean = true;
                break;
            }
            col++;
        }
        if(w.getCol() > 0 && squares[w.getRow()][w.getCol()-1].getTile() != null)
            is_lean = true;
        if(w.getCol() + w.getTiles().length < BOARD_SIZE &&
                squares[w.getRow()][w.getCol() + w.getTiles().length + 1].getTile() != null)
            is_lean = true;
        return is_lean;
    }

    //checks if a horizontal word is legal
    private boolean horizBoardLegal(Word w)
    {
        if(w.getCol() + w.getTiles().length >= BOARD_SIZE)
            return false;
        if(is_empty)
        {
            if(w.getRow() != BOARD_SIZE/2)
                return false;
            if(w.getCol() > BOARD_SIZE/2 || w.getCol() + w.getTiles().length - 1 < BOARD_SIZE/2)
                return false;
        }
        //no replacing tiles check
        int col = w.getCol();
        for(Tile t : w.getTiles()) {
            if ((squares[w.getRow()][col].getTile() != null && t != null) ||
            (squares[w.getRow()][col].getTile() == null && t == null))
                return false;
            col++;
        }
        if(!is_empty)
            return isWordLeanHoriz(w);
        return true;
    }

    //checks if a word can be put in the position it wants on the board
    public boolean boardLegal(Word w)
    {
        if(w.getRow() < 0 || w.getRow() >= BOARD_SIZE || w.getCol() < 0 || w.getCol() >= BOARD_SIZE)
            return false;
        if(w.isVertical())
            return vertBoardLegal(w);
        return horizBoardLegal(w);
    }

    //checks if word is in dictionary
    public boolean dictionaryLegal(Word w) {
        return dm.query("text1.txt","text2.txt",w.toString());
    }

    //gets new word created by putting the current vertical word on the board
    private Word getNewWordVert(Word w,int row)
    {
        int temp_col = w.getCol();
        if(w.getCol() > 0) {
            while (temp_col - 1 >= 0 && squares[row][temp_col-1].getTile() != null)//get to first letter of new word
                temp_col--;
        }
        int start_col = temp_col;
        int new_length = 0;
        int null_counter = 0;
        while(null_counter < 2 && temp_col < BOARD_SIZE)
        {
            if(squares[row][temp_col].getTile() == null)
                null_counter++;
            new_length++;
            temp_col++;
        }
        if(null_counter == 2)
            new_length--;
        if(new_length<2)
            return null;
        Tile[] new_word_tiles = new Tile[new_length];
        temp_col = start_col;
        for(int i = 0 ; i<new_length ; i++)
        {
            if(squares[row][temp_col].getTile() != null)
                new_word_tiles[i] = squares[row][temp_col].getTile();
            else
                new_word_tiles[i] = w.getTiles()[row - w.getRow()];
            temp_col++;
        }
        return new Word(new_word_tiles,row,start_col,!w.isVertical());
    }

    //gets all new words created by the new added vertical word to the board
    private ArrayList<Word> getWordsVert(Word w){
        ArrayList<Word> other_words = new ArrayList<>();
        Word w1;
        for(int row = w.getRow() ; row < w.getRow() + w.getTiles().length ; row++)
        {
            if(squares[row][w.getCol()].getTile() == null) {
                w1 = getNewWordVert(w, row);
                if (w1 != null)
                    other_words.add(getNewWordVert(w, row));
            }
        }
        return other_words;
    }

    //gets new word created by putting the current horizontal word on the board
    private Word getNewWordHoriz(Word w,int col)
    {
        int temp_row = w.getRow();
        if(w.getRow() > 0) {
            while (temp_row - 1 >= 0 && squares[temp_row - 1][col].getTile() != null)//get to first letter of new word
                temp_row--;
        }
        int start_row = temp_row;
        int new_length = 0;
        int null_counter = 0;
        while(null_counter < 2 && temp_row < BOARD_SIZE)
        {
            if(squares[temp_row][col].getTile() == null)
                null_counter++;
            new_length++;
            temp_row++;
        }
        if(null_counter == 2)
            new_length--;
        if(new_length < 2)
            return null;
        Tile[] new_word_tiles = new Tile[new_length]; // doesnt initialize
        temp_row = start_row;
        for(int i = 0;i<new_length;i++)
        {
            if(squares[temp_row][col].getTile() != null)
                new_word_tiles[i] = squares[temp_row][col].getTile();
            else
                new_word_tiles[i] = w.getTiles()[col - w.getCol()];
            temp_row++;
        }
        return new Word(new_word_tiles,start_row,col,!w.isVertical());
    }

    //gets all new words created by the new added horizontal word to the board
    private ArrayList<Word> getWordsHoriz(Word w) {
        ArrayList<Word> other_words = new ArrayList<>();
        Word w1;
        for(int col = w.getCol() ; col < w.getCol() + w.getTiles().length ; col++)
        {
            if(squares[w.getRow()][col].getTile() == null) {
                w1 = getNewWordHoriz(w,col);
                if(w1 != null)
                    other_words.add(w1);
            }
        }
        return other_words;
    }

    //gets all new words created by the new added word to the board
    public ArrayList<Word> getWords(Word w){
        ArrayList<Word> added_words;
        if(!is_empty) {
            if (w.isVertical())
                added_words = getWordsVert(w);
            else
                added_words = getWordsHoriz(w);
        }
        else
            added_words = new ArrayList<>();
        Tile[] curr_word_tiles = new Tile[w.getTiles().length];
        for(int i = 0 ;i<w.getTiles().length ; i++) {
            if(w.getTiles()[i] != null)
                curr_word_tiles[i] = w.getTiles()[i];
            else if(w.isVertical())
                curr_word_tiles[i] = squares[w.getRow() + i][w.getCol()].getTile();
            else
                curr_word_tiles[i] = squares[w.getRow()][w.getCol() + i].getTile();
        }
        Word curr_word = new Word(curr_word_tiles,w.getRow(),w.getCol(),w.isVertical());
        added_words.add(curr_word);
        return added_words;
    }

    //gets score of vertical word
    private int getVertScore(Word w)
    {
        int sum = 0;
        int word_mul = 1;
        int row = w.getRow();
        Tile des_tile;
        for(Tile t : w.getTiles())
        {
            if(squares[row][w.getCol()].getTile() != null)
                des_tile = squares[row][w.getCol()].getTile();
            else
                des_tile = t;
            sum += des_tile.score * squares[row][w.getCol()].getLetterMultiplier();
            if(squares[row][w.getCol()].getWordMultiplier() != 1)
                word_mul *= squares[row][w.getCol()].getWordMultiplier();
            row++;
        }
        return sum*word_mul;
    }

    //gets score of horizontal word
    private int getHorizScore(Word w)
    {
        int sum = 0;
        int word_mul = 1;
        int col = w.getCol();
        Tile des_tile;
        for(Tile t : w.getTiles())
        {
            if(squares[w.getRow()][col].getTile() != null)
                des_tile = squares[w.getRow()][col].getTile();
            else
                des_tile = t;
            sum += des_tile.score * squares[w.getRow()][col].getLetterMultiplier();
            word_mul *= squares[w.getRow()][col].getWordMultiplier();
            col++;
        }
        return sum * word_mul;
    }

    //gets score of the word on the board
    public int getScore(Word w){
        int sum;
        if(w.isVertical())
            sum = getVertScore(w);
        else
            sum = getHorizScore(w);
        return sum;
    }

    //places word on the board
    private void placeWord(Word w)
    {
        if(w.isVertical()) {
            int row = w.getRow();
            for(Tile t : w.getTiles())
            {
                if(squares[row][w.getCol()].getTile() == null)
                    squares[row][w.getCol()].setTile(t);
                row++;
            }
        }
        else
        {
            int col = w.getCol();
            for(Tile t : w.getTiles())
            {
                if(squares[w.getRow()][col].getTile() == null)
                    squares[w.getRow()][col].setTile(t);
                col++;
            }
        }
    }

    //checks if word is legal and then puts in on the board if it is
    public int tryPlaceWord(Word w){
       if(!boardLegal(w))
           return 0;
       int sum = 0;
       ArrayList<Word> new_words = getWords(w);
       for(int i = 0 ; i<new_words.size() ; i++) {
           if (!dictionaryLegal(new_words.get(i)))
               return 0;
           sum += getScore(new_words.get(i));
       }
       placeWord(w);
       if(is_empty) {
           is_empty = false;
           squares[BOARD_SIZE/2][BOARD_SIZE/2].setWordMultiplier(1);//remove middle square multipliers
       }
           return sum;
    }

}
