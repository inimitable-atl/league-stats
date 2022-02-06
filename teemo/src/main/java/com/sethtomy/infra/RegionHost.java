package com.sethtomy.infra;

public enum RegionHost implements TargetHost {

    AMERICAS("https://americas.api.riotgames.com"),
    ASIA("https://asia.api.riotgames.com");

    private final String url;


    RegionHost(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}
