package com.example.spelltimer;

import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Dictionary;

public class MainActivity extends AppCompatActivity {
    protected ArrayList<Spell> list_of_spells;
    protected Dictionary<String,Integer> dict_of_icons;
    protected static CheckBox hasBoots;
    protected static CheckBox hasInsight;
    protected Spinner leftSpellSelect; protected Spinner rightSpellSelect;
    protected TextView leftTimerView; protected TextView rightTimerView;
    protected Button leftStartButton; protected Button rightStartButton;
    protected CountDownTimer leftTimer; protected CountDownTimer rightTimer;
    protected ImageView leftIconView; protected ImageView rightIconView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findIDs();
        leftStartButton(); rightStartButton();
        list_of_spells = Spell.createSpellList();
        dict_of_icons = Spell.createIconDict();
        populateSpinners();
        displayLeftSpellIcon(); displayRightSpellIcon();
    }

    public void findIDs(){
        setContentView(R.layout.activity_main);
        hasBoots = findViewById(R.id.hasBoots);
        hasInsight = findViewById(R.id.hasInsight);
        leftSpellSelect = findViewById(R.id.leftSpellSelect); rightSpellSelect = findViewById(R.id.rightSpellSelect);
        leftTimerView = findViewById(R.id.leftTimer); rightTimerView = findViewById(R.id.rightTimer);
        leftStartButton = findViewById(R.id.leftStartButton); rightStartButton = findViewById(R.id.rightStartButton);
        leftTimer = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                leftTimerView.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                leftTimerView.setText("OFF CD");
            }
        }; rightTimer = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                rightTimerView.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                rightTimerView.setText("OFF CD");
            }
        };
        leftIconView = findViewById(R.id.leftIconView); rightIconView = findViewById(R.id.rightIconView);
    }

    protected void leftStartButton(){
        leftStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_and_start_leftTimer(modifySpellCooldown(list_of_spells.get(list_of_spells.indexOf(leftSpellSelect.getSelectedItem())).getCooldown()));
            }
        });
    }

    protected void rightStartButton(){
        rightStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_and_start_rightTimer(modifySpellCooldown(
                        list_of_spells.get(list_of_spells.indexOf(rightSpellSelect.getSelectedItem())).getCooldown()));}
        });
    }

    protected void reset_and_start_leftTimer(long millisInFuture){
        leftTimer.cancel();
        leftTimer = new CountDownTimer( millisInFuture,1000) {

            public void onTick(long millisUntilFinished) {
                leftTimerView.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                leftTimerView.setText("OFF CD");
            }
        }.start();
    }

    protected void reset_and_start_rightTimer(long millisInFuture){
        rightTimer.cancel();
        rightTimer = new CountDownTimer( millisInFuture,1000) {

            public void onTick(long millisUntilFinished) {
                rightTimerView.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                rightTimerView.setText("OFF CD");
            }
        }.start();
    }

    protected void populateSpinners(){
        ArrayAdapter<Spell> spellArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, list_of_spells);
        leftSpellSelect.setAdapter(spellArrayAdapter); rightSpellSelect.setAdapter(spellArrayAdapter);
        leftSpellSelect.setSelection(0); rightSpellSelect.setSelection(0);

    }

    protected static long modifySpellCooldown(long spell_cd){
        // 15% for both
        if (hasBoots.isChecked() && hasInsight.isChecked()) spell_cd *= .85;
        // 10% off for just boots
        if (hasBoots.isChecked() && !hasInsight.isChecked()) spell_cd *= .9;
        // 5% off for just insight
        if (!hasBoots.isChecked() && hasInsight.isChecked()) spell_cd *= .95;
        // 0% off if nothing is checked
        return spell_cd;
    }

    //TODO CRASHES ON NULL OBJECT REFERENCE SOMEWHERE
    protected void displayLeftSpellIcon(){
        String key_for_dict = list_of_spells.get(list_of_spells.indexOf(leftSpellSelect.getSelectedItem())).getSpellName();
        Integer drawableReference = dict_of_icons.get(key_for_dict);
        leftIconView.setImageResource(drawableReference);
    }

    //TODO CRASHES ON NULL OBJECT REFERENCE SOMEWHERE
    protected void displayRightSpellIcon(){
        String key_for_dict = list_of_spells.get(list_of_spells.indexOf(rightSpellSelect.getSelectedItem())).getSpellName();
        Integer drawableReference = dict_of_icons.get(key_for_dict);
        rightIconView.setImageResource(drawableReference);
    }
}

