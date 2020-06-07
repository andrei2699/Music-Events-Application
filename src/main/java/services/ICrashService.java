package services;

import models.CrashServiceModel;

public interface ICrashService {
    CrashServiceModel createCrashReport(String exceptionMessage);
}
