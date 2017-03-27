package com.gmail.scyntrus.ifactions.fxl;

import java.lang.reflect.Method;

import com.gmail.scyntrus.fmob.ErrorManager;
import com.gmail.scyntrus.ifactions.Faction;
import io.github.dre2n.factionsxl.relation.Relation;

class FactionFXL extends Faction {

    public io.github.dre2n.factionsxl.faction.Faction faction;

    public FactionFXL (io.github.dre2n.factionsxl.faction.Faction faction) {
        this.faction = faction;
    }

    public FactionFXL (Object faction) {
        this.faction = (io.github.dre2n.factionsxl.faction.Faction) faction;
    }

    @Override
    public int getRelationTo(Faction other) {
        if (faction == null || isNone()) return 0;
        try {
            Relation rel = faction.getRelation(((FactionFXL)other).faction);
            if (rel == Relation.ENEMY) {
                return -1;
            } else if (rel == Relation.PEACE) {
                return 0;
            } else if (rel == Relation.ALLIANCE || rel == Relation.OWN || rel == Relation.COALITION || rel == Relation.LORD || rel == Relation.VASSAL || rel == Relation.PERSONAL_UNION) {
                return 1;
            }
        } catch (Exception e) {
            ErrorManager.handleError(e);
        }
        return 0;
    }

    @Override
    public boolean isNone() {
        return faction == null || !faction.isActive();
    }

    @Override
    public String getName() {
        if (faction == null) return "";
        return faction.getName();
    }

    @Override
    public double getPower() {
        if (faction == null) return 0;
        return faction.getPower();
    }

    @Override
    public boolean monstersNotAllowed() {
        return false;
    }
}
