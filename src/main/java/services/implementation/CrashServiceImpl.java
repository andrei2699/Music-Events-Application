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
    public void createCrashReport(String exceptionMessage) {
        String date = LocalDate.now() + " - " + LocalTime.now();
        crashServiceModelRepository.setDestinationFileName("CrashReport : " + date + ".json");
        crashServiceModelRepository.create(new CrashServiceModel(0, exceptionMessage, date));
    }
}
