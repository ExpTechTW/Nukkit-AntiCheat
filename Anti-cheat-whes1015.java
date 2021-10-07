package whes20031015;
//再反編譯阿你個啟智兒笑死沒實力靠反編譯腦癱
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.*;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.LoginChainData;
import cn.nukkit.utils.TextFormat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.SECONDS;

public class whes1015 extends PluginBase implements Listener {

    private final int vercode = 110;
    private final int configvercode = 7;
    private final String vername = "V 1.1.0-stable";
    private String str1 = "null";
    private String data = "null";
    private String data1 = "null";
    private String data2 = "null";
    private String kicklist = "null";

    @Override
    public void onEnable() {
        Config config = new Config(new File(getDataFolder() + "/config.yml"), Config.YAML);
        if(config.get("version")==null){
            whes1015configcheck();
        }else{
            int configvercode1 = Integer.parseInt(String.valueOf(config.get("version")));
            if (configvercode1!= configvercode) {
                whes1015configcheck();
            } else {
                URL url = null;
                try {
                    url = new URL("http://220.134.162.44:81/Anti-cheat-whes1015.php");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection http = null;
                try {
                    http = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    http.setRequestMethod("GET");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }
                try {
                    InputStream input = http.getInputStream();
                    byte[] data = new byte[1024];
                    int idx = input.read(data);
                    String str = new String(data, 0, idx);
                    int x = Integer.parseInt(str);
                    if (vercode < x) {
                        this.getLogger().warning(TextFormat.RED + "Please Updated Your Plugin! " + vername);
                        this.getLogger().warning(TextFormat.RED + "Please Updated Your Plugin! " + vername);
                        this.getLogger().warning(TextFormat.RED + "Please Updated Your Plugin! " + vername);
                        this.getLogger().warning(TextFormat.RED + "Please Updated Your Plugin! " + vername);
                        this.getLogger().warning(TextFormat.RED + "Please Updated Your Plugin! " + vername);
                    } else {
                        this.getLogger().info(TextFormat.BLUE + "Anti-cheat-whes1015 working! " + vername + " Language Code " + config.get("language"));
                        whes1015get();
                        whes1015versioncheck();
                    }
                    input.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                http.disconnect();

                this.getServer().getPluginManager().registerEvents(this, this);
            }
        }
        whes1015post2();
    }

    @EventHandler
    public void whes1015Flight(PlayerToggleFlightEvent event) {

        if(event.getPlayer().getGamemode()==0&& !event.getPlayer().isOp()){
            Config config = new Config(new File(getDataFolder() + "/config.yml"), Config.YAML);
            Config config1 = new Config(new File(getDataFolder() + "/string.yml"), Config.YAML);
            if (!str1.contains("^" + event.getPlayer().getLoginChainData().getDeviceId() + "^") || !str1.contains("^" + event.getPlayer().getLoginChainData().getXUID() + "^")) {
                data = "{\n    \"username\": \"" + this.getServer().getMotd() + "\",\n    \"avatar_url\": \"https://res.cloudinary.com/dpk8k0rob/image/upload/v1611295072/iusvs0tssr1utaqp3gav.webp\",\n    \"content\":\"" + event.getPlayer().getName() + " >> Unfair advantage: flight - Has been included in the cloud blacklist" + "\"\n  }";
                data1 = "{\n    \"username\": \"" + this.getServer().getMotd() + "\",\n    \"avatar_url\": \"https://res.cloudinary.com/dpk8k0rob/image/upload/v1611295072/iusvs0tssr1utaqp3gav.webp\",\n    \"content\":\"" + event.getPlayer().getName() + " >> 不公平優勢:飛行 - 已被列入雲端黑名單" + "\"\n  }";
                data2 = "^" + event.getPlayer().getLoginChainData().getXUID() + "^^" + event.getPlayer().getLoginChainData().getDeviceId() + "^";
                whes1015post();
                whes1015post1();
                whes1015post2();
            }
                event.getPlayer().kick(String.valueOf(config1.get("kickflightreason-" + config.get("language"))), false);
                this.getServer().broadcastMessage(TextFormat.RED+event.getPlayer().getName()+" "+String.valueOf(config1.get("kickflightreason-" + config.get("language"))));
            }
        }

    @EventHandler
    public void whes1015Move(PlayerMoveEvent event) {
       /* Config config = new Config(new File(getDataFolder() + "/config.yml"), Config.YAML);
        Config config1 = new Config(new File(getDataFolder() + "/string.yml"), Config.YAML);
            Config config3 = new Config(new File(getDataFolder() + "/data.yml"), Config.YAML);
            if (config3.get(event.getPlayer().getName() + "X") == null) {
                config3.set(event.getPlayer().getName() + "X", event.getPlayer().getX());
                config3.set(event.getPlayer().getName() + "Z", event.getPlayer().getZ());
                config3.save();
            } else {
                Double value = Math.sqrt(Math.abs(event.getPlayer().getX() - (Double) config3.get(event.getPlayer().getName() + "X"))) + Math.sqrt(Math.abs(event.getPlayer().getZ() - (Double) config3.get(event.getPlayer().getName() + "Z")));
                if (config3.get(event.getPlayer().getName() + "sum") == null) {
                    config3.set(event.getPlayer().getName() + "sum", value);
                    config3.set(event.getPlayer().getName() + "time", 1);
                    config3.save();
                } else {
                    Double value1 = (Double) config3.get(event.getPlayer().getName() + "sum") + value;
                    int value2 = (int) config3.get(event.getPlayer().getName() + "time") + 1;
                    Double value3 = value1 / value2;
                    config3.set(event.getPlayer().getName() + "time", value2);
                    config3.set(event.getPlayer().getName() + "sum", value1);
                    if(value3>0.95){
                        data = "{\n    \"username\": \"" + this.getServer().getMotd() + "\",\n    \"avatar_url\": \"https://res.cloudinary.com/dpk8k0rob/image/upload/v1611295072/iusvs0tssr1utaqp3gav.webp\",\n    \"content\":\"" + event.getPlayer().getName() + " >> Unfair advantage: speed - Has been included in the cloud blacklist" + "\"\n  }";
                        data1 = "{\n    \"username\": \"" + this.getServer().getMotd() + "\",\n    \"avatar_url\": \"https://res.cloudinary.com/dpk8k0rob/image/upload/v1611295072/iusvs0tssr1utaqp3gav.webp\",\n    \"content\":\"" + event.getPlayer().getName() + " >> 不公平優勢:速度 - 已被列入雲端黑名單" + "\"\n  }";
                        data2 = "^" + event.getPlayer().getLoginChainData().getXUID() + "^^" + event.getPlayer().getLoginChainData().getDeviceId() + "^";
                        whes1015post();
                        whes1015post1();
                        whes1015post2();
                        event.getPlayer().kick(String.valueOf(config1.get("kickspeedreason-" + config.get("language"))), false);
                    }
                }
                config3.remove(event.getPlayer().getName() + "X");
                config3.remove(event.getPlayer().getName() + "Z");
                config3.save();
            }*/

    }

    @EventHandler
    public void whes1015Login(PlayerLoginEvent event) {
        this.getLogger().info(event.getPlayer().getName()+" > XUID > "+event.getPlayer().getLoginChainData().getXUID());
        LoginChainData clientData = event.getPlayer().getLoginChainData();
            if (clientData.getDeviceOS() == 1) { // is Android
                    String[] modelSplit = clientData.getDeviceModel().split(" ");
                    if (modelSplit.length != 0) {
                        if (!modelSplit[0].equals(modelSplit[0].toUpperCase())) {
                            data = "{\n    \"username\": \"" + this.getServer().getMotd() + "\",\n    \"avatar_url\": \"https://res.cloudinary.com/dpk8k0rob/image/upload/v1611295072/iusvs0tssr1utaqp3gav.webp\",\n    \"content\":\"" + event.getPlayer().getName() + " >> Unfair advantage: using cheating tools - Has been included in the cloud blacklist" + "\"\n  }";
                            data1 = "{\n    \"username\": \"" + this.getServer().getMotd() + "\",\n    \"avatar_url\": \"https://res.cloudinary.com/dpk8k0rob/image/upload/v1611295072/iusvs0tssr1utaqp3gav.webp\",\n    \"content\":\"" + event.getPlayer().getName() + " >> 不公平優勢:使用作弊工具 - 已被列入雲端黑名單" + "\"\n  }";
                            data2 = "^" + event.getPlayer().getLoginChainData().getXUID() + "^^" + event.getPlayer().getLoginChainData().getDeviceId() + "^";
                            whes1015post();
                            whes1015post1();
                            whes1015post2();
                            whes1015post3();
                            kicklist = kicklist + "*" + event.getPlayer().getName() + "*";
                        }
                    }
                }
        }

    @EventHandler
    public void PlayerLocallyInitialized(PlayerLocallyInitializedEvent event) {
        Config config = new Config(new File(getDataFolder() + "/config.yml"), Config.YAML);
        Config config1 = new Config(new File(getDataFolder() + "/string.yml"), Config.YAML);
        Config config2 = new Config(new File(getDataFolder() + "/whitelist.yml"), Config.YAML);
        if (str1.contains("^" + event.getPlayer().getLoginChainData().getDeviceId() + "^") || str1.contains("^" + event.getPlayer().getLoginChainData().getXUID() + "^")) {
            if(Integer.parseInt(String.valueOf(config.get("Cloud-blacklist")))==1) {
                if (Integer.parseInt(String.valueOf(config.get("strict"))) == 1 || kicklist.contains("*" + event.getPlayer().getName() + "*")) {
                    if (!config2.getString("whitelist-xuid").contains(event.getPlayer().getLoginChainData().getXUID())) {
                        data = "{\n    \"username\": \"" + this.getServer().getMotd() + "\",\n    \"avatar_url\": \"https://res.cloudinary.com/dpk8k0rob/image/upload/v1611295072/iusvs0tssr1utaqp3gav.webp\",\n    \"content\":\"" + event.getPlayer().getName() + " >> In the cloud blacklist, so blocked from joining the game" + "\"\n  }";
                        data1 = "{\n    \"username\": \"" + this.getServer().getMotd() + "\",\n    \"avatar_url\": \"https://res.cloudinary.com/dpk8k0rob/image/upload/v1611295072/iusvs0tssr1utaqp3gav.webp\",\n    \"content\":\"" + event.getPlayer().getName() + " >> 在雲端黑名單中所以被阻止加入遊戲" + "\"\n  }";
                        whes1015post();
                        whes1015post1();
                        whes1015post3();
                        event.getPlayer().kick(String.valueOf(config1.get("kickCloudblacklistreason-" + config.get("language"))), false);
                        this.getServer().broadcastMessage(TextFormat.RED + event.getPlayer().getName() + " " + String.valueOf(config1.get("kickCloudblacklistreason-" + config.get("language"))));

                } else {
                    this.getLogger().alert(event.getPlayer().getName() + String.valueOf(config1.get("kickcancel-" + config.get("language"))));
                }
            }
            }else{
                this.getServer().broadcastMessage(TextFormat.RED +String.valueOf(config1.get("kickwarn-" + config.get("language"))));
            }
        } else if (kicklist.contains("*" + event.getPlayer().getName() + "*")) {
            event.getPlayer().kick(String.valueOf(config1.get("kickreason-" + config.get("language"))),false);
            this.getServer().broadcastMessage(TextFormat.RED+String.valueOf(config1.get("kickreason-" + config.get("language"))));
            kicklist.replace("*" + event.getPlayer().getName() + "*", "");
        }
    }

    public void whes1015get() {

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        final Runnable runnable = new Runnable() {

            public void run() {
                URL url = null;
                try {
                    url = new URL("http://220.134.162.44:81/NoToolboxlist.php");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection http = null;
                try {
                    http = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    http.setRequestMethod("GET");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }
                try {
                    InputStream input = http.getInputStream();
                    byte[] data = new byte[1024];
                    int idx = input.read(data);
                    str1 = new String(data, 0, idx);

                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                http.disconnect();

            }
        };
        scheduler.scheduleAtFixedRate(runnable, 0, 1, SECONDS);

    }

    public void whes1015post() {

        URL url = null;
        try {
            url = new URL("https://discord.com/api/webhooks/872481675626549320/F3tCsNsYxlSeb63d8VidM3dq8xuO6dYtHoU2Zmt4Q1LQv0SA1j2i2VzZdtXW3eyb_Z73");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection http = null;
        try {
            http = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            http.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        http.setDoOutput(true);
        http.setRequestProperty("Authorization", "Bearer mt0dgHmLJMVQhvjpNXDyA83vA_PxH23Y");
        http.setRequestProperty("Content-Type", "application/json");

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = null;
        try {
            stream = http.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            stream.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if(http.getResponseCode()>=400){
                this.getLogger().alert("cht - 無法傳送訊息至 Discord，不影響插件運作，建議使用VPN來解決問題 | en - Unable to send messages to Discord, does not affect the operation of the plug-in, it is recommended to use a VPN to solve the problem");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        http.disconnect();
    }

    public void whes1015post2() {
         if(data2!="null") {
             URL url2 = null;
             try {
                 url2 = new URL("http://220.134.162.44:9000/register");
             } catch (MalformedURLException e) {
                 e.printStackTrace();
             }
             HttpURLConnection http = null;
             try {
                 http = (HttpURLConnection) url2.openConnection();
             } catch (IOException e) {
                 e.printStackTrace();
             }
             try {
                 http.setRequestMethod("POST");
             } catch (ProtocolException e) {
                 e.printStackTrace();
             }
             http.setDoOutput(true);
             http.setRequestProperty("Authorization", "Bearer mt0dgHmLJMVQhvjpNXDyA83vA_PxH23Y");
             http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

             byte[] out = data2.getBytes(StandardCharsets.UTF_8);

             OutputStream stream = null;
             try {
                 stream = http.getOutputStream();
             } catch (IOException e) {
                 e.printStackTrace();
             }
             try {
                 stream.write(out);
             } catch (IOException e) {
                 e.printStackTrace();
             }
             try {
                 if(http.getResponseCode()>=400){
                     this.getLogger().warning("cht - 無法連接至數據庫 | en - Unable to connect to the database");
                 }
             } catch (IOException e) {
                 e.printStackTrace();
             }
             http.disconnect();
         }
    }

    public void whes1015post1() {

        URL url1 = null;
        try {
            url1 = new URL("https://discord.com/api/webhooks/872480911470493716/_ovrgJCktRVwJm2hYyhhc1mGFNgC6Kk25hntHfFOonMbQlmdEmmniCwnq6vGjYpp82PZ");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection http = null;
        try {
            http = (HttpURLConnection) url1.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            http.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        http.setDoOutput(true);
        http.setRequestProperty("Authorization", "Bearer mt0dgHmLJMVQhvjpNXDyA83vA_PxH23Y");
        http.setRequestProperty("Content-Type", "application/json");

        byte[] out = data1.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = null;
        try {
            stream = http.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            stream.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if(http.getResponseCode()>=400){
                this.getLogger().alert("cht - 無法傳送訊息至 Discord，不影響插件運作，建議使用VPN來解決問題 | en - Unable to send messages to Discord, does not affect the operation of the plug-in, it is recommended to use a VPN to solve the problem");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        http.disconnect();
    }

    public void whes1015post3() {
        Config config = new Config(new File(getDataFolder() + "/config.yml"), Config.YAML);
        if (Integer.parseInt(String.valueOf(config.get("webhook"))) != 0) {
            URL url3 = null;
            try {
                url3 = new URL(String.valueOf(config.get("webhook")));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection http = null;
            try {
                http = (HttpURLConnection) url3.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                http.setRequestMethod("POST");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            http.setDoOutput(true);
            http.setRequestProperty("Authorization", "Bearer mt0dgHmLJMVQhvjpNXDyA83vA_PxH23Y");
            http.setRequestProperty("Content-Type", "application/json");

            byte[] out = data.getBytes(StandardCharsets.UTF_8);

            OutputStream stream = null;
            try {
                stream = http.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                stream.write(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (http.getResponseCode() >= 400) {
                    this.getLogger().alert("cht - 無法傳送訊息至 Discord，不影響插件運作，建議使用VPN來解決問題 | en - Unable to send messages to Discord, does not affect the operation of the plug-in, it is recommended to use a VPN to solve the problem");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            http.disconnect();
        }
    }

    public void whes1015versioncheck() {
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        final Runnable runnable = new Runnable() {

            public void run() {
                URL url = null;
                try {
                    url = new URL("http://220.134.162.44:81/Anti-cheat-whes1015.php");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection http = null;
                try {
                    http = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    http.setRequestMethod("GET");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }
                try {
                    InputStream input = http.getInputStream();
                    byte[] data = new byte[1024];
                    int idx = input.read(data);
                    String str = new String(data, 0, idx);
                    int x = Integer.parseInt(str);
                    if (vercode < x) {
                        update();
                    }
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                saveDefaultConfig();
                http.disconnect();
            }
        };
        scheduler.scheduleAtFixedRate(runnable, 0, 15, SECONDS);
    }

    public void reload() {
        this.getServer().reload();
    }

    public void update() {
        this.getLogger().warning(TextFormat.RED + "Please Updated Your Plugin! " + vername);
        reload();
    }

    public void whes1015configcheck() {
        Config config = new Config(new File(getDataFolder() + "/config.yml"), Config.YAML);
        Config config1 = new Config(new File(getDataFolder() + "/string.yml"), Config.YAML);
        Config config2 = new Config(new File(getDataFolder() + "/whitelist.yml"), Config.YAML);

        if(config.get("version")==null){
            config.set("version","1");
            config.set("language","en");
            config1.set("kickreason-en","Unfair advantage: using cheating tools");
            config1.set("kickreason-cht","不公平的優勢：使用作弊工具");
            config1.set("kickCloudblacklistreason-en","You have been included in the cloud blacklist");
            config1.set("kickCloudblacklistreason-cht","您已被列入雲端黑名單");

            config.save();
            config1.save();
            this.getLogger().info(TextFormat.LIGHT_PURPLE+"Anti-cheat-whes1015 The configuration file is built!");
        }else{
            int configvercode1 = Integer.parseInt(String.valueOf(config.get("version")));
            if(configvercode1<2){
                config1.set("kickflightreason-en","Unfair advantage: flight");
                config1.set("kickflightreason-cht","不公平優勢:飛行");
                config.set("version","2");
                config.save();
                config1.save();
            }
            if(configvercode1<3){
                config.set("version","3");
                config1.set("kickspeedreason-en","Unfair advantage: speed");
                config1.set("kickspeedreason-cht","不公平優勢:速度");
                config2.set("whitelist-xuid","");
                config.save();
                config1.save();
                config2.save();
            }
            if(configvercode1<5){
                config.set("version","5");
                config.set("Cloud-blacklist","1");
                config1.set("kickwarn-cht","警告：雲黑名單檢測已關閉 服務器存在風險");
                config1.set("kickwarn-en","Warning: Cloud blacklist detection has been turned off and the server is at risk");
                config.save();
                config1.save();
            }

            if(configvercode1<6){
                config.set("version","6");
                config1.set("kickcancel-cht"," 在whitelist-xuid中，所以不被踢出");
                config1.set("kickcancel-en"," In the whitelist-xuid, so it is not kicked out");
                config.save();
                config1.save();
            }

            if(configvercode1<7){
                config.set("version","7");
                config.set("webhook","0");
                config.set("strict","1");
                config.save();
            }

            this.getLogger().info(TextFormat.LIGHT_PURPLE+"Anti-cheat-whes1015 Configuration file has been updated! Config version:" + configvercode);
        }
        reload();
    }
}

