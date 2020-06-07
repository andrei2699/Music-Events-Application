package services.implementation;

import models.CrashServiceModel;
import repository.IRepository;
import services.ICrashService;

import java.time.LocalDate;
import java.time.LocalTime;

public class CrashServiceImpl implements ICrashService {

    private final IRepository<CrashServiceModel> crashServiceModelRepository;

    public CrashServiceImpl(IRepository<CrashServiceModel> crashServiceModelRepository) {
        this.crashServiceModelRepository = crashServiceModelRepository;
    }

    @Override
    public CrashServiceModel createCrashReport(String exceptionMessage) {
        LocalTime now = LocalTime.now();
        String date = LocalDate.now() + "_" + now.getHour() + "_" + now.getMinute() + "_" + now.getSecond();
        crashServiceModelRepository.setDestinationFileName("CrashReport_" + date + ".json");

        CrashServiceModel crashServiceModel = new CrashServiceModel(0, exceptionMessage, date);
//        crashServiceModelRepository.create(crashServiceModel);

        return crashServiceModel;
    }
}
