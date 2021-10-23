package core.service;

import core.api.response.SettingResponse;
import core.model.GlobalSetting;
import core.model.repository.GlobalSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SettingService {

    @Autowired
    private GlobalSettingRepository globalSettingRepository;

    public SettingResponse getGlobalSettings() {
        Iterable<GlobalSetting> globalSettingIterable = globalSettingRepository.findAll();
        ArrayList<GlobalSetting> globalSettingArrayList = new ArrayList<>();
        for (GlobalSetting gs :
                globalSettingIterable) {
            globalSettingArrayList.add(gs);
        }
        SettingResponse settingResponse = new SettingResponse();
        settingResponse.setMultiuserMode(globalSettingArrayList.get(0).getValue().equals("yes") ? true : false);
        settingResponse.setPostPremoderation(globalSettingArrayList.get(1).getValue().equals("yes") ? true : false);
        settingResponse.setStatisticsIsPublic(globalSettingArrayList.get(2).getValue().equals("yes") ? true : false);

        return settingResponse;
    }
}
