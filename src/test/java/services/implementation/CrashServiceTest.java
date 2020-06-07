package services.implementation;

import models.CrashServiceModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import repository.IRepository;
import services.ICrashService;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CrashServiceTest {
    @Mock
    private IRepository<CrashServiceModel> repository;

    private ICrashService crashService;

    @Before
    public void setUp() {
        crashService = new CrashServiceImpl(repository);
    }

    @After
    public void tearDown() {
        crashService = null;
    }

    @Test
    public void testCreateNewCrashReport() {
        try {
            throw new NullPointerException("Crash Message");
        } catch (Exception e) {
            CrashServiceModel crashReport = crashService.createCrashReport(e.getMessage());

            LocalTime now = LocalTime.now();
            LocalDate dateNow = LocalDate.now();
            String date = dateNow.getYear() + "_" + dateNow.getMonth() + "_" + dateNow.getDayOfMonth() + "_" + now.getHour() + "_" + now.getMinute() + "_" + now.getSecond();
            assertEquals("crash date don't match", date, crashReport.getDate());
            assertEquals("crash messages don't match", "Crash Message", crashReport.getExceptionMessage());
        }
    }
}
