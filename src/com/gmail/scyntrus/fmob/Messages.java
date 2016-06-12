package com.gmail.scyntrus.fmob;

import com.google.common.base.Charsets;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Messages {

    public enum Message {

        NAME_ARCHER("name.archer"),
        NAME_SWORDSMAN("name.swordsman"),
        NAME_MAGE("name.mage"),
        NAME_TITAN("name.titan"),
        NAME_SPIRITBEAR("name.spiritbear"),
        INTERACT_NOHITFRIENDLY("interact.nohitfriendly"),
        INTERACT_HITFRIENDLY("interact.hitfriendly"),
        INTERACT_NOHITALLY("interact.nohitally"),
        INTERACT_INFO("interact.info"),
        INTERACT_SELECT("interact.select"),
        INTERACT_DESELECT("interact.deselect"),
        INTERACT_NOSELECT("interact.noselect"),
        INTERACT_HEAL("interact.heal"),
        INTERACT_PLAYERDEATH("interact.playerdeath"),
        FM_ERROR("fm.error"),
        FM_HELP("fm.help"),
        FM_INFO_SPAWN("fm.info.spawn"),
        FM_INFO_NOSPAWN("fm.info.nospawn"),
        FM_INFO_COMMAND("fm.info.command"),
        FM_INFO_NOCOMMAND("fm.info.nocommand"),
        FM_INFO_MOB("fm.info.mob"),
        FM_INFO_DISABLED("fm.info.disabled"),
        FM_INFO_COST("fm.info.cost"),
        FM_INFO_POWER("fm.info.power"),
        FM_DESELECT("fm.deselect"),
        FM_NOSELECTION("fm.noselection"),
        FM_NOPERMISSION("fm.nopermission"),
        FM_SELECTALL("fm.selectall"),
        FM_SELECTIONSTART("fm.selectionstart"),
        FM_SELECTIONITEM("fm.selectionitem"),
        FM_SELECTIONSTOP("fm.selectionstop"),
        FM_NOFACTION("fm.nofaction"),
        FM_NORANK("fm.norank"),
        FM_NOTERRITORY("fm.noterritory"),
        FM_NOCAPACITY("fm.nocapacity"),
        FM_NOMOB("fm.nomob"),
        FM_NOPERMISSIONMOB("fm.nopermissionmob"),
        FM_SPAWNDISABLED("fm.spawndisabled"),
        FM_POWERUSAGE("fm.powerusage"),
        FM_NOPOWERUSAGE("fm.nopowerusage"),
        FM_MONEYUSAGE("fm.moneyusage"),
        FM_MONEYERROR("fm.moneyerror"),
        FM_NOMONEY("fm.nomoney"),
        FM_SPAWNSUCCESS("fm.spawnsuccess"),
        FM_SPAWNFAIL("fm.spawnfail"),
        FM_MONSTERSDISABLED("fm.monstersdisabled"),
        FM_REFUNDMONEY("fm.refundmoney"),
        FM_COLORFORMAT("fm.colorformat"),
        FM_COLORSUCCESS("fm.colorsuccess"),
        FM_NOCOMMAND("fm.nocommand"),
        FM_COMMAND_NOBLOCK("fm.command.noblock"),
        FM_COMMAND_HOME("fm.command.home"),
        FM_COMMAND_FOLLOW("fm.command.follow"),
        FM_COMMAND_STOP("fm.command.stop"),
        FM_COMMAND_MOVE("fm.command.move"),
        FM_COMMAND_PATROL("fm.command.patrol"),
        FM_COMMAND_WANDER("fm.command.wander"),
        FM_COMMAND_SETHOME("fm.command.sethome"),
        FM_COMMAND_TPHOME("fm.command.tphome"),
        FM_COMMAND_TPHERE("fm.command.tphere");

        private final String key;
        Message(String key) {
            this.key = key;
        }
        public String getKey() {
            return key;
        }
    }

    private static YamlConfiguration yml;
    private static boolean initialized = false;
    private static Map<String, String> cache = new HashMap<String, String>();

    public static void init(Plugin plugin) {
        if (initialized)
            return;
        yml = new YamlConfiguration();
        File file = new File(plugin.getDataFolder(), "messages.yml");
        if (file.isFile()) {
            try {
                yml.load(file);
            } catch (Exception e) {
                ErrorManager.handleError(e);
            }
        }
        InputStream res = Messages.class.getResourceAsStream("/messages.yml");
        if(res != null) {
            yml.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(res, Charsets.UTF_8)));
        }
        yml.options().copyDefaults(true);
        try {
            yml.save(file);
        } catch (IOException e) {
            ErrorManager.handleError(e);
        }
        initialized = true;
    }

    public static String get(Message message, Object... args) {
        if (!initialized) {
            ErrorManager.handleError("Messages library accessed before initialization.", new Exception());
            return "";
        }
        String key = message.getKey();
        String raw;
        if (cache.containsKey(key)) {
            raw = cache.get(key);
        }  else {
            raw = yml.getString(key);
            cache.put(key, raw);
        }
        if (raw == null) {
            ErrorManager.handleError("Message with no data.", new Exception());
            return "";
        }
        return String.format(raw, args);
    }
}
