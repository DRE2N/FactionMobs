package com.gmail.scyntrus.ifactions.fxl;


import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.gmail.scyntrus.ifactions.Faction;
import com.gmail.scyntrus.ifactions.Factions;
import com.gmail.scyntrus.ifactions.FactionsManager;
import com.gmail.scyntrus.ifactions.Rank;
import com.gmail.scyntrus.ifactions.f6.FactionListener68;
import io.github.dre2n.factionsxl.FactionsXL;
import io.github.dre2n.factionsxl.board.Region;
import io.github.dre2n.factionsxl.player.FPlayer;

public class FactionsFXL implements Factions {

    private io.github.dre2n.factionsxl.faction.FactionCache factionsInstance;

    private static FactionsFXL instance = null;

    private FactionsFXL(Plugin plugin) {
        factionsInstance = FactionsXL.getInstance().getFactionCache();
        plugin.getServer().getPluginManager().registerEvents(new FactionListener68(), plugin);
    }

    public static Factions get(Plugin plugin, StringBuilder log) {
        if (instance != null) {
            return instance;
        }
        String pluginName = plugin.getName();
        if (FactionsManager.classExists("io.github.dre2n.factionsxl.FactionsXL")) {
            log.append("FOUND io.github.dre2n.factionsxl.FactionsXL\n");
            System.out.println("[" + pluginName + "] FactionsXL detected.");
            instance = new FactionsFXL(plugin);
        }
        return instance;
    }

    @Override
    public Faction getFactionAt(Location loc) {
        Region region = FactionsXL.getInstance().getBoard().getByLocation(loc);
        if (region != null && !region.isNeutral()) {
            return new FactionFXL(region.getOwner());
        } else {
            return null;
        }
    }

    @Override
    public Faction getFactionByName(String name) {
        return new FactionFXL(factionsInstance.getByName(name));
    }

    @Override
    public Faction getPlayerFaction(Player player) {
        return new FactionFXL(factionsInstance.getByMember(player));
    }

    @Override
    public Faction getFactionFromNativeObject(Object nativeObject) {
        return new FactionFXL(nativeObject);
    }

    @Override
    public Rank getPlayerRank(Player player) {
        FPlayer fPlayer = FactionsXL.getInstance().getFPlayerCache().getByPlayer(player);
        if (fPlayer.getFaction() == null) {
            return Rank.MEMBER;
        } else if (fPlayer.getFaction().getAdmin().equals(player)) {
            return Rank.LEADER;
        } else if (fPlayer.isMod()) {
            return Rank.OFFICER;
        } else {
            return Rank.MEMBER;
        }
    }

    @Override
    public boolean supportsLandOwnership() {
        return true;
    }

    @Override
    public String getVersionString() {
        return "FXL";
    }

}
