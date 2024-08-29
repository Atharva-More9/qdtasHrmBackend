package com.qdtas.service;
import com.qdtas.entity.GeneralInformation;

import java.util.List;

public interface GeneralInformationService {

    public List<GeneralInformation> getAllGeneralInformation();

    public GeneralInformation addGeneralInformation(GeneralInformation generalInformation);
}
