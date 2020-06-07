package services.implementation;

import models.CrashServiceModel;
import repository.IRepository;
import services.ICrashService;

import java.time.LocalDate;
import java.time.LocalTime;

import static services.implementation.FileSystemManager.CRASH_REPORTS_FOLDER_NAME;

public class CrashServiceImpl implements ICrashService {

    private final IRepository<CrashServiceModel> crashServiceModelRepository;

    public CrashServiceImpl(IRepository<CrashServiceModel> crashServiceModelRepository) {
        this.crashServiceModelRepository = crashServiceModelRepository;
    }

    @Override
    public CrashServiceModel createCrashReport(String exceptionMessage) {
        LocalTime now = LocalTime.now();
        LocalDate dateNow = LocalDate.now();
        String date = dateNow.getYear() + "_" + dateNow.getMonth() + "_" + dateNow.getDayOfMonth() + "_" + now.getHour() + "_" + now.getMinute() + "_" + now.getSecond();
        String fileName = CRASH_REPORTS_FOLDER_NAME + "/" + "CrashReport_" + date + ".json";
        crashServiceModelRepository.setDestinationFileName(fileName);
        crashServiceModelRepository.initFile();

        CrashServiceModel crashServiceModel = new CrashServiceModel(0, exceptionMessage, date);
        crashServiceModelRepository.create(crashServiceModel);

        return crashServiceModel;
    }
}
