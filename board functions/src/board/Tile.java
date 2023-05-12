
package board;


import java.util.Objects;
import java.util.Random;

//this class represent a tile in the game
public class Tile {
    public final char letter;//letter of tile
    public final int score;//score of tile

    //constructor
    private Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return letter == tile.letter && score == tile.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, score);
    }

    //this class represents the bag of tiles in the game
    public static class Bag{
        private static final int LETTERS = 26;//number of letters
        private final int[] letter_values = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};//value of each letter

        final int[] letter_max_quan = {9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1};// starting quantity of each letter
        private int[] letter_quan;//current quantity of each letter in the bag
        Tile[] letters;//letters array

        private static Bag bag_ref = null;//reference of bag

        //constructor
        private Bag() {
            letters = new Tile[LETTERS];
            char curr_letter = 'A';
            for(int i = 0;i<LETTERS;i++)
            {
                letters[i] = new Tile(curr_letter,letter_values[i]);
                curr_letter++;
            }
            this.letter_quan = new int[LETTERS];
            System.arraycopy(this.letter_max_quan,0,this.letter_quan,0,LETTERS);
        }

        //gets the bag reference
        public static Bag getBag()
        {
            if(bag_ref == null)
                bag_ref = new Bag();
            return bag_ref;
        }

        //gets random tile from bag
        public Tile getRand(){
            boolean is_empty = true;
            for(int quantity : letter_quan){
                if(quantity != 0) {
                    is_empty = false;
                    break;
                }
            }
            if(is_empty)
                return null;
            Random r = new Random();
            //int pos;
            Tile t = null;
            while(t == null)
                t = getTile((char)(r.nextInt(LETTERS) + 'A'));
            return t;
        }

        //gets specific tile from bag
        public Tile getTile(char letter)
        {
            int pos = letter - 'A';
            if(letter < 'A' || letter > 'Z' || letter_quan[pos] == 0)
                return null;
            letter_quan[pos]--;
            return this.letters[pos];
        }

        //puts tile in bag
        public void put(Tile t)
        {
            int pos = t.letter - 'A';
            if(this.letter_quan[pos] < this.letter_max_quan[pos])
                letter_quan[pos]++;
        }

        //gets size of bag
        public int size()
        {
            int quan = 0;
            for(int quantity : letter_quan){
                quan += quantity;
            }
            return quan;
        }

        //gets current quantities of letters in bag
        public int[] getQuantities()
        {
            int[] quantity_copy = new int[LETTERS];
            System.arraycopy(this.letter_quan,0,quantity_copy,0,LETTERS);
            return quantity_copy;
        }
    }
}
