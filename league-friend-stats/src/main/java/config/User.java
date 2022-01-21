package config;

public class User {

    String name;

    String[] summoners;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getSummoners() {
        return summoners;
    }

    public void setSummoners(String[] summoners) {
        this.summoners = summoners;
    }
}
