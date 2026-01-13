import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * Speller.java 
 * CS230 Lab on hash tables
 * To check a text file against a dictionary of english words, 
 * which is constructed from an other text file.
 *
 * @author Imara Wangia 
 * @version 04.24.2025
 **/

public class Speller{
    private EnglishHashDictionary dictionary;
    private LinkedList<String> misspelled;
    //constructor 
    public Speller(String filename){
        dictionary = new EnglishHashDictionary(filename);
        misspelled = new LinkedList<String>();
    }
    /**
     * method checkSpelling @param String filename
     * @return boolean that returns true if a file contains mispelled words, and adds them to 
     * a linked list, otherwise it returns false. 
     */
    public boolean checkSpelling(String filename){
        boolean result = false;
        try {
            Scanner reader = new Scanner(new File(filename));
            String wrd = "", line = null;
            while (reader.hasNext()) {
                wrd = wrd + " " + reader.next();
            }
            reader.close();
            String[] words = wrd.replaceAll("\\p{Punct}","").split("\\s+");
            for(String w : words){
                if(!dictionary.searchWord(w)){
                    if(!misspelled.contains(w.trim().toLowerCase()) && (w.trim().toLowerCase().length()>=1)){
                        misspelled.add(w.trim().toLowerCase());
                        result = true;
                    }
                }
            }
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
        return result;
    }
    /**
     * method String toString returns a string with how many words are misspelled and a list of
     * misspelled words. 
     */
    public String toString(){
        String str = "";
        str += misspelled.size() + " mispelled " + misspelled;
        return str;
    }
    /**
     * method saveToTGF takes @param filename and writes the list of mispelled words to a file. 
     */
    public void saveToTGF(String fileName) {
        try (PrintWriter writer = new PrintWriter(new File(fileName))) {
            for (int i = 0; i < misspelled.size(); i++) {
                writer.println(misspelled.get(i));
            }
        } catch (IOException e) {
            System.out.println("Error saving TGF file: " + e.getMessage());
        }
    }
    /**
     * method main tester code
     */
    public static void main (String[] args) {
        EnglishHashDictionary dictionary = new EnglishHashDictionary("EnglishWords.txt");

        Speller spell = new Speller("EnglishWords.txt");
        System.out.println(spell.checkSpelling("RomeoAndJuliet.txt"));
        spell.saveToTGF("MisspelledWords.txt");
        String output = spell.toString();

        System.out.println(output);
        System.out.println(output.contains("william"));
        System.out.println(output.contains("69 mispelled"));
    
    }   
}
