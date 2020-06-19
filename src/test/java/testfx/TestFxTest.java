package testfx;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class TestFxTest extends ApplicationTest {

    private Label label;

    @Before
    public void setUp() {
        label = new Label();
        label.setText("ASD");
    }

    @Test
    public void test() {
        assertEquals("ASD", label.getText());
    }
}
