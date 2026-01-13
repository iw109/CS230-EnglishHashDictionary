import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

public class SpellerTest {

    private static Path tempDictFile;
    private static Path tempDocFile;

    @BeforeAll
    public static void setUpFiles() throws IOException {
        // Create a temporary dictionary file with known words
        tempDictFile = Files.createTempFile("tempDictionary", ".txt");
        Files.write(tempDictFile, String.join("\n", "apple", "banana", "carrot").getBytes());

        // Create a temporary document file with some misspelled and valid words
        tempDocFile = Files.createTempFile("tempDocument", ".txt");
        Files.write(tempDocFile, String.join(" ", "apple", "banana", "donut", "carrot", "dog", "banana").getBytes());
    }

    @AfterAll
    public static void cleanUpFiles() throws IOException {
        Files.deleteIfExists(tempDictFile);
        Files.deleteIfExists(tempDocFile);
    }

    @Test
    public void testCheckSpelling_FindsMisspelledWords() {
        Speller speller = new Speller(tempDictFile.toString());
        speller.checkSpelling(tempDocFile.toString());

        String output = speller.toString();
        assertTrue(output.contains("donut"), "Should report 'donut' as a misspelled word.");
        assertTrue(output.contains("dog"), "Should report 'dog' as a misspelled word.");
    }

    @Test
    public void testCheckSpelling_IgnoresDuplicates() {
        Speller speller = new Speller(tempDictFile.toString());
        speller.checkSpelling(tempDocFile.toString());

        String output = speller.toString();

        // Even though "banana" is repeated, it should not appear in misspelled list
        assertFalse(output.contains("banana"), "Should not report valid word 'banana' as misspelled.");
        // "donut" appears once in the misspelled list
        int donutCount = countOccurrences(output, "donut");
        assertEquals(1, donutCount, "'donut' should only appear once in the misspelled word list.");
    }

    @Test
    public void testToString_ReportsCorrectSize() {
        Speller speller = new Speller(tempDictFile.toString());
        speller.checkSpelling(tempDocFile.toString());

        String output = speller.toString();
        assertTrue(output.contains("2 mispelled"), "Should report exactly 2 misspelled words.");
    }

    private int countOccurrences(String text, String word) {
        return text.split("\\b" + word + "\\b", -1).length - 1;
    }
}
