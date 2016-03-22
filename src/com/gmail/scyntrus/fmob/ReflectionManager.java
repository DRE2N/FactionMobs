package com.gmail.scyntrus.fmob;

import java.lang.reflect.Field;
import java.util.Map;

import net.minecraft.server.v1_9_R1.Entity;
import net.minecraft.server.v1_9_R1.EntityInsentient;
import net.minecraft.server.v1_9_R1.EntityTypes;
import net.minecraft.server.v1_9_R1.NavigationAbstract;
import net.minecraft.server.v1_9_R1.PathfinderGoalSelector;

public class ReflectionManager {
    public static Field navigation_Distance = null;
    public static Field pathfinderGoalSelector_GoalSet = null;
    public static Field entityInsentient_GoalSelector = null;

    public static boolean good_Navigation_Distance = false;
    public static boolean good_PathfinderGoalSelector_GoalSet = false;
    public static boolean good_EntityInsentient_GoalSelector = false;

    public static Map<String, Class<? extends Entity>> mapC;
    public static Map<Class<? extends Entity>, String> mapD;
    public static Map<Class<? extends Entity>, Integer> mapF;
    public static Map<String, Integer> mapG;

    public static boolean init() {
        {
            Field fieldC = null;
            Field fieldD = null;
            Field fieldF = null;
            Field fieldG = null;
            try {
                fieldC = EntityTypes.class.getDeclaredField("c"); //TODO: Update name on version change
                fieldD = EntityTypes.class.getDeclaredField("d"); //TODO: Update name on version change
                fieldF = EntityTypes.class.getDeclaredField("f"); //TODO: Update name on version change
                fieldG = EntityTypes.class.getDeclaredField("g"); //TODO: Update name on version change
            } catch (Exception e1) {
                try {
                    fieldC = EntityTypes.class.getDeclaredField("field_75625_b");
                    fieldD = EntityTypes.class.getDeclaredField("field_75626_c");
                    fieldF = EntityTypes.class.getDeclaredField("field_75624_e");
                    fieldG = EntityTypes.class.getDeclaredField("field_75622_f");
                } catch (Exception e2) {
                    ErrorManager.handleError("Failed to reflect entity registration methods.");
                    ErrorManager.handleError(e1);
                    ErrorManager.handleError(e2);
                    return false;
                }
            }
            try {
                fieldC.setAccessible(true);
                fieldD.setAccessible(true);
                fieldF.setAccessible(true);
                fieldG.setAccessible(true);
                @SuppressWarnings("unchecked")
                Map<String, Class<? extends Entity>> tempMap1 = (Map<String, Class<? extends Entity>>) fieldC.get(null);
                mapC = tempMap1;
                @SuppressWarnings("unchecked")
                Map<Class<? extends Entity>, String> tempMap2 = (Map<Class<? extends Entity>, String>) fieldD.get(null);
                mapD = tempMap2;
                @SuppressWarnings("unchecked")
                Map<Class<? extends Entity>, Integer> tempMap3 = (Map<Class<? extends Entity>, Integer>) fieldF.get(null);
                mapF = tempMap3;
                @SuppressWarnings("unchecked")
                Map<String, Integer> tempMap4 = (Map<String, Integer>) fieldG.get(null);
                mapG = tempMap4;
            } catch (Exception e) {
                ErrorManager.handleError(e);
                return false;
            }
        }
        try {
            navigation_Distance = NavigationAbstract.class.getDeclaredField("g"); //TODO: Update name on version change
            navigation_Distance.setAccessible(true);
            good_Navigation_Distance = true;
        } catch (Exception e1) {
            try {
                navigation_Distance = NavigationAbstract.class.getDeclaredField("field_75512_e");
                navigation_Distance.setAccessible(true);
                good_Navigation_Distance = true;
            } catch (Exception e2) {
                ErrorManager.handleError("[Minor Error] Field not found: Navigation.e; Custom pathfinding distances cannot be set");
                ErrorManager.handleError(e1);
                ErrorManager.handleError(e2);
            }
        }
        try {
            pathfinderGoalSelector_GoalSet = PathfinderGoalSelector.class.getDeclaredField("b"); //TODO: Update name on version change
            pathfinderGoalSelector_GoalSet.setAccessible(true);
            good_PathfinderGoalSelector_GoalSet = true;
        } catch (Exception e1) {
            try {
                pathfinderGoalSelector_GoalSet = PathfinderGoalSelector.class.getDeclaredField("field_75782_a");
                pathfinderGoalSelector_GoalSet.setAccessible(true);
                good_PathfinderGoalSelector_GoalSet = true;
            } catch (Exception e2) {
                ErrorManager.handleError("[Minor Error] Field not found: PathfinderGoalSelector.b; Unable to override mob goals");
                ErrorManager.handleError(e1);
                ErrorManager.handleError(e2);
            }
        }
        try {
            entityInsentient_GoalSelector = EntityInsentient.class.getDeclaredField("goalSelector");
            entityInsentient_GoalSelector.setAccessible(true);
            good_EntityInsentient_GoalSelector = true;
        } catch ( Exception e1 ) {
            try {
                entityInsentient_GoalSelector = EntityInsentient.class.getDeclaredField("field_70714_bg");
                entityInsentient_GoalSelector.setAccessible(true);
                good_EntityInsentient_GoalSelector = true;
            } catch (Exception e2) {
                ErrorManager.handleError("[Minor Error] Field not found: EntityInsentient.goalSelector; Unable to override zombie goals");
                ErrorManager.handleError(e1);
                ErrorManager.handleError(e2);
            }
        }
        return true;
    }
}
