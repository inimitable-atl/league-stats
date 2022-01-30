package com.inimitable.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class Participant {
    private final String puuid;

    private final int assists;
    private final int baronKills;
    private final int bountyLevel;
    private final int champExperience;
    private final int champLevel;
    private final int championId;
    private final String championName;
    private final int championTransform;
    private final int consumablesPurchased;
    private final int damageDealtToBuildings;
    private final int damageDealtToObjectives;
    private final int damageDealtToTurrets;
    private final int damageSelfMitigated;
    private final int deaths;
    private final int detectorWardsPlaced;
    private final int doubleKills;
    private final int dragonKills;
    private final boolean firstBloodAssist;
    private final boolean firstBloodKill;
    private final boolean firstTowerAssist;
    private final boolean firstTowerKill;
    private final boolean gameEndedInEarlySurrender;
    private final boolean gameEndedInSurrender;
    private final int goldEarned;
    private final int goldSpent;
    private final String individualPosition;
    private final int inhibitorKills;
    private final int inhibitorTakedowns;
    private final int item0;
    private final int item1;
    private final int item2;
    private final int item3;
    private final int item4;
    private final int item5;
    private final int item6;
    private final int itemsPurchased;
    private final int killingSprees;
    private final int kills;
    private final String lane;
    private final int largestCriticalStrike;
    private final int largestKillingSpree;
    private final int largestMultiKill;
    private final int longestTimeSpentLiving;
    private final int magicDamageDealt;
    private final int magicDamageDealtToChampions;
    private final int magicDamageTaken;
    private final int neutralMinionsKilled;
    private final int nexusKills;
    private final int nexusTakedowns;
    private final int nexusLost;
    private final int objectivesStolen;
    private final int participantId;
    private final int pentaKills;
    private final int physicalDamageDealt;
    private final int physicalDamageDealtToChampions;
    private final int physicalDamagedTaken;
    private final int profileIcon;
    private final int quadraKills;
    private final String riotIdName;
    private final String RiotIdTagline;
    private final String role;
    private final int sightWardsBoughtInGame;
    private final int spell1Casts;
    private final int spell2Casts;
    private final int spell3Casts;
    private final int spell4Casts;
    private final int summoner1Casts;
    private final int summoner1Id;
    private final int summoner2Casts;
    private final int summoner2Id;
    private final String summonerId;
    private final int summonerLevel;
    private final String summonerName;
    private final boolean teamEarlySurrendered;
    private final int teamId;
    private final String teamPosition;
    private final int timeCCingOthers;
    private final int timePlayed;
    private final int totalDamageDealt;
    private final int totalDamageDealtToChampions;
    private final int totalDamageShieldedOnTeammates;
    private final int totalDamageTaken;
    private final int totalHeal;
    private final int totalHealsOnTeammates;
    private final int totalMinionsKilled;
    private final int totalTimeCCDealt;
    private final int totalTimeSpentDead;
    private final int totalUnitsHealed;
    private final int tripleKills;
    private final int trueDamageDealt;
    private final int trueDamageDealtToChampions;
    private final int trueDamageTaken;
    private final int turretKills;
    private final int turretTakedowns;
    private final int turretsLost;
    private final int unrealKills;
    private final int visionScore;
    private final int visionWardsBoughtInGame;
    private final int wardsKilled;
    private final boolean win;
}
