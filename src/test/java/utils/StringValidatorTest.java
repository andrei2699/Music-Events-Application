package utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StringValidatorTest {

    @Test
    public void testIsStringNotEmpty(){
        assertFalse(StringValidator.isStringNotEmpty(""));
        assertFalse(StringValidator.isStringNotEmpty("   "));
        assertFalse(StringValidator.isStringNotEmpty(null));
        assertTrue(StringValidator.isStringNotEmpty("word"));
        assertTrue(StringValidator.isStringNotEmpty("  word  "));
    }

    @Test
    public void testIsStringEmpty(){
        assertTrue(StringValidator.isStringEmpty(" "));
        assertTrue(StringValidator.isStringEmpty(""));
        assertTrue(StringValidator.isStringEmpty(null));
        assertFalse(StringValidator.isStringEmpty("  word "));
        assertFalse(StringValidator.isStringEmpty("word"));
    }
}
