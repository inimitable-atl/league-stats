package com.inimitable.report.admin;

import com.inimitable.model.SummonerGroup;

import java.util.UUID;

public interface UserService {
    SummonerGroup getSummonerGroup(String username, UUID summonerGroupId);
}
