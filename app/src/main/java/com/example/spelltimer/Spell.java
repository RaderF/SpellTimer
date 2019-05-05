package com.example.spelltimer;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Spell {
    private String SpellName;
    private long Cooldown;

    private Spell(String name, long cd){
        SpellName = name;
        Cooldown = cd;
    }

    static ArrayList<Spell> createSpellList() {
        ArrayList<Spell> list_of_spells = new ArrayList<>();
        list_of_spells.add(new Spell("Barrier", 180000));
        list_of_spells.add(new Spell("Cleanse", 210000));
        list_of_spells.add(new Spell("Exhaust", 210000));
        list_of_spells.add(new Spell("Flash", 300000));
        list_of_spells.add(new Spell("Ghost", 180000));
        list_of_spells.add(new Spell("Heal", 240000));
        list_of_spells.add(new Spell("Ignite", 180000));
        list_of_spells.add(new Spell("Teleport", 360000));
        return list_of_spells;
    }

    static Map<String, Integer> createIconDict(){
        Map<String, Integer> dict_of_icons = new HashMap<>();
        dict_of_icons.put("Barrier", R.drawable.barrier_icon);
        dict_of_icons.put("Cleanse", R.drawable.cleanse_icon);
        dict_of_icons.put("Exhaust", R.drawable.exhaust_icon);
        dict_of_icons.put("Flash", R.drawable.flash_icon);
        dict_of_icons.put("Ghost", R.drawable.ghost_icon);
        dict_of_icons.put("Heal", R.drawable.heal_icon);
        dict_of_icons.put("Ignite", R.drawable.ignite_icon);
        dict_of_icons.put("Teleport", R.drawable.teleport_icon);
        return dict_of_icons;
    }

    long getCooldown() {
        return Cooldown;
    }

    String getSpellName() {
        return SpellName;
    }


    @NonNull
    @Override
    public String toString(){
        return this.getSpellName() + ": " + this.getCooldown()/1000;
    }

    @Override
    public boolean equals(Object other){
        return toString().equals(other.toString());
    }
}
