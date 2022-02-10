package com.inimitable.report.admin;

import com.inimitable.model.Summoner;
import reactor.core.publisher.Flux;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface UserService {
    Flux<Summoner> getSummonersInGroup(String username, UUID summonerGroupId) throws ExecutionException, InterruptedException;
}
