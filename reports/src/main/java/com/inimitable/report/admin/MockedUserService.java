package com.inimitable.report.admin;

import com.inimitable.model.Region;
import com.inimitable.model.Summoner;
import com.inimitable.model.SummonerGroup;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MockedUserService implements UserService {
    @Override
    public SummonerGroup getSummonerGroup(String username, UUID summonerGroupId) {
        return SummonerGroup.builder()
                .summoner(Summoner.builder().region(Region.NA).name("HeavensVanguard").build())
//                .summoner(Summoner.builder().region(Region.NA).name("pyramin").build())
                .build();
    }
}
