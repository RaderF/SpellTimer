package com.example.spelltimer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CooldownModifierUnitTest {

    @Test
    public void do_both_cdr_boxes_work() {
        MainActivity.hasBoots.setChecked(true);
        MainActivity.hasInsight.setChecked(true);
        assertEquals(85, MainActivity.modifySpellCooldown(100));
    }

    @Test
    public void does_only_boots_work() {
        MainActivity.hasBoots.setChecked(true);
        MainActivity.hasInsight.setChecked(false);
        assertEquals(90, MainActivity.modifySpellCooldown(100));
    }

    @Test
    public void does_only_insight_work() {
        MainActivity.hasBoots.setChecked(false);
        MainActivity.hasInsight.setChecked(true);
        assertEquals(95, MainActivity.modifySpellCooldown(100));
    }
}
