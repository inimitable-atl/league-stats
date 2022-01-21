package com.sethtomy.infra;

public enum PlatformHost implements TargetHost {

    NA1("https://na1.api.riotgames.com");

    private final String url;


    PlatformHost(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
