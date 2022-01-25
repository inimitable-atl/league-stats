//package config;
//
//import org.yaml.snakeyaml.Yaml;
//import org.yaml.snakeyaml.constructor.Constructor;
//import foo.RiotUser;
//
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//public class YamlConfigRunner {
//
//    public List<RiotUser> load() {
//        var list = new ArrayList<RiotUser>();
//        Yaml yaml = new Yaml(new Constructor(Config.class));
//        try (InputStream in = getClass().getClassLoader().getResourceAsStream("config.yaml")) {
//            Config config = yaml.load(in);
//
//            for (User user : config.getUsers()) {
//                list.add(new RiotUser(user.getName(), user.getSummoners()));
//            }
//            return list;
//        } catch (Exception e) {
//            System.err.println(e);
//        }
//        return list;
//    }
//
//}
