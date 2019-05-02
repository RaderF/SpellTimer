package com.example.spelltimer;

import android.widget.CheckBox;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

//TODO remove null pointer exceptions from these tests
public class CooldownModifierUnitTest extends MainActivity{
    CheckBox hasBoots;
    CheckBox hasInsight;

    @Test
    public void do_both_cdr_boxes_work() {
        hasBoots.setChecked(true);
        hasInsight.setChecked(true);
        assertEquals(85, modifySpellCooldown(100));
    }

    @Test
    public void does_only_boots_work() {
        hasBoots.setChecked(true);
        hasInsight.setChecked(false);
        assertEquals(90, modifySpellCooldown(100));
    }

    @Test
    public void does_only_insight_work() {
        hasBoots.setChecked(false);
        hasInsight.setChecked(true);
        assertEquals(95, modifySpellCooldown(100));
    }
}
