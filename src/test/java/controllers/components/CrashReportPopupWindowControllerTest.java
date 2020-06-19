package controllers.components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;

public class CrashReportPopupWindowControllerTest extends ApplicationTest {

    private CrashReportPopupWindowController crashReportPopupWindowController;

    private static final String CRASH_MESSAGE = "Crash message";

    @Before
    public void setUp() {
        crashReportPopupWindowController = new CrashReportPopupWindowController();
        crashReportPopupWindowController.crashMessageLabel = new Label();
        crashReportPopupWindowController.closeButton = new Button();
    }

    @Test
    public void testInitialize() {
        crashReportPopupWindowController.setCrashMessage(CRASH_MESSAGE);
        crashReportPopupWindowController.initialize(null, null);
        assertEquals(CRASH_MESSAGE, crashReportPopupWindowController.crashMessageLabel.getText());
    }
}