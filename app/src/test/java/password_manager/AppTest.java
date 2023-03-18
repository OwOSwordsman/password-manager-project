/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package password_manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.junit.jupiter.api.Test;

import password_manager.database.DatabaseDao;
import password_manager.database.Encrypter;

class AppTest {
    @Test
    void checkEncryptions() throws Exception {
        var encrypter = new Encrypter(Encrypter.generateSalt(), "poop");
        var originalText = "the quick brown fox jumped over the lazy dog";
        var encryptedText = encrypter.encryptString(originalText);
        var decryptedText = new String(encrypter.decryptString(encryptedText));
        assertNotEquals(encryptedText, originalText.getBytes());
        assertEquals(originalText, decryptedText);
    }

    @Test
    void checkDbInitialization() throws Exception {
        try (var dao = new DatabaseDao("init.db", "test_pass")) {
        } finally {
            new File("init.db").delete();
        }
    }

    @Test()
    void validPassword() throws Exception {
        try (var dao = new DatabaseDao("valid.db", "test_pass")) {
        }
        try (var dao = new DatabaseDao("valid.db", "test_pass")) {
        } finally {
            new File("valid.db").delete();
        }
    }

    @Test()
    void invalidPassword() throws Exception {
        try (var dao = new DatabaseDao("invalid.db", "test_pass")) {
        }
        try (var dao = new DatabaseDao("invalid.db", "wrong_pass")) {
        } catch (IllegalArgumentException e) {
            return;
        } finally {
            new File("invalid.db").delete();
        }
        fail();
    }
}
