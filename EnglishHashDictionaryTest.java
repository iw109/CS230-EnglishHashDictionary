import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

public class EnglishHashDictionaryTest {

    private static Path tempDictFile;

    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        // Create a temporary dictionary file with a few words
        tempDictFile = Files.createTempFile("tempDictionary", ".txt");
        Files.write(tempDictFile, String.join("\n", "apple", "banana", "carrot").getBytes());
    }

    @AfterAll
    public static void tearDownAfterClass() throws Exception {
        // Clean up the temporary file
        Files.deleteIfExists(tempDictFile);
    }

    @Test
    public void testSearchWord_Found() {
        EnglishHashDictionary dict = new EnglishHashDictionary(tempDictFile.toString());
        assertTrue(dict.searchWord("apple"), "apple should be found in the dictionary");
        assertTrue(dict.searchWord("banana"), "banana should be found in the dictionary");
        assertTrue(dict.searchWord("carrot"), "carrot should be found in the dictionary");
    }

    @Test
    public void testSearchWord_NotFound() {
        EnglishHashDictionary dict = new EnglishHashDictionary(tempDictFile.toString());
        assertFalse(dict.searchWord("donut"), "donut should not be found in the dictionary");
    }

    @Test
    public void testAddWord() {
        EnglishHashDictionary dict = new EnglishHashDictionary(tempDictFile.toString());
        assertFalse(dict.searchWord("donut"));
        dict.addWord("donut");
        assertTrue(dict.searchWord("donut"));
    }

    @Test
    public void testRemoveWord() {
        EnglishHashDictionary dict = new EnglishHashDictionary(tempDictFile.toString());
        dict.addWord("donut");
        assertTrue(dict.searchWord("donut"));
        dict.removeWord("donut");
        assertFalse(dict.searchWord("donut"));
    }

    @Test
    public void testCaseInsensitivity() {
        EnglishHashDictionary dict = new EnglishHashDictionary(tempDictFile.toString());
        dict.addWord("Orange");
        assertTrue(dict.searchWord("orange"));
        assertTrue(dict.searchWord("ORANGE"));
        assertTrue(dict.searchWord("Orange"));
    }

    @Test
    public void testWhitespaceTrimming() {
        EnglishHashDictionary dict = new EnglishHashDictionary(tempDictFile.toString());
        dict.addWord("  kiwi  ");
        assertTrue(dict.searchWord("kiwi"));
        assertTrue(dict.searchWord("  kiwi"));
        assertTrue(dict.searchWord("kiwi  "));
    }
}