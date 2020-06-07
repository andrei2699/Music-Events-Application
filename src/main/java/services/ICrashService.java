package services;

import models.CrashServiceModel;

public interface ICrashService {
    CrashServiceModel createCrashReport(Throwable exceptionMessage);
}
