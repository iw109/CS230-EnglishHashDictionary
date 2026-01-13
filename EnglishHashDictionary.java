import java.util.*;
import java.util.Hashtable;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
/**
 * Reads a dictionary from the input file, into a hash table. 
 * Each line in the file contains only one word.
 * 
 * @author Imara Wangia
 * @version 04.24.2025
 */

public class EnglishHashDictionary implements Dictionary {
    //instance vars
    private int totalWords;
    private Hashtable<String,String> table;
    private  LinkedList<Hashtable> collection;
    //constructor
    public EnglishHashDictionary(String filename){
        totalWords = 0;
        table = new Hashtable<String,String>(); 
        try {
            Scanner reader = new Scanner(new File(filename));
            while (reader.hasNext()) {
                String word = reader.next();                
                table.put(word,word);
                totalWords++;
            }
            reader.close();
        }

        catch (IOException ex) {
            System.out.println(ex);
        }
    }
    /**
     *  method addWord @para word adds a word to a dictionary
     */
    public void addWord(String word){
        table.put(word.trim().toLowerCase(),word.trim().toLowerCase());
    }
    /**
     *  method removeWord @para word removes a word from a dictionary
     */
    public void removeWord(String word){
        table.remove(word,word);
    }
    /**
     *  method searchWord @para word checks to see if a word is in the dictionary
     */
    public boolean searchWord(String word){
        boolean result = false;        
        if(table.containsKey(word.trim().toLowerCase())){
            result = true;
        }
        return result;
    }
    /**
     *  method main tester
     */
    public static void main(String[] args) {
        EnglishHashDictionary ehd = new EnglishHashDictionary("EnglishWords.txt");
        System.out.println("******Testing searchWord() *****");
        System.out.println("donut: " + ehd.searchWord("donut"));
        System.out.println("Adding donut to dictionary");
        ehd.addWord("donut");
        System.out.println("donut: " + ehd.searchWord("donut"));
        System.out.println("data: " + ehd.searchWord("data"));
        System.out.println("booger: " + ehd.searchWord("booger"));
        System.out.println("  ");
    }

}
