package com.example.spelltimer;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Map;

//TODO make changing the selected object in spinners change imageViews too
//TODO make checkboxes modify a boolean variable in HasModifiers
public class MainActivity extends AppCompatActivity {
    protected ArrayList<Spell> list_of_spells;
    protected Map<String,Integer> dict_of_icons;
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
        list_of_spells = Spell.createSpellList();
        dict_of_icons = Spell.createIconDict();
        populateSpinners();
        setLeftStartButton(); setRightStartButton();
        leftSpellSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateIcons();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        rightSpellSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateIcons();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        set_initial_timer_displays();
        updateIcons();
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

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                leftTimerView.setText("OFF CD");
            }
        }; rightTimer = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                rightTimerView.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                rightTimerView.setText("OFF CD");
            }
        };
        leftIconView = findViewById(R.id.leftIconView); rightIconView = findViewById(R.id.rightIconView);
    }

    protected void setLeftStartButton(){
        leftStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_and_start_leftTimer(modifySpellCooldown(((Spell)leftSpellSelect.getSelectedItem()).getCooldown()));
            }
        });
    }

    protected void setRightStartButton(){
        rightStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_and_start_rightTimer(modifySpellCooldown(((Spell)rightSpellSelect.getSelectedItem()).getCooldown()));
            }
        });
    }

    protected void set_initial_timer_displays(){
        leftTimerView.setText("OFF CD");
        rightTimerView.setText("OFF CD");
    }

    protected void reset_and_start_leftTimer(long millisInFuture){
        leftTimer.cancel();
        leftTimer = new CountDownTimer( millisInFuture,1000) {

            public void onTick(long millisUntilFinished) {
                leftTimerView.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @SuppressLint("SetTextI18n")
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

            @SuppressLint("SetTextI18n")
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

    protected void updateLeftSpellIcon(){
        String key_for_dict = ((Spell)leftSpellSelect.getSelectedItem()).getSpellName();
        Integer drawableReference = dict_of_icons.get(key_for_dict);
        leftIconView.setImageResource(drawableReference);
    }

    protected void updateRightSpellIcon(){
        String key_for_dict = ((Spell)rightSpellSelect.getSelectedItem()).getSpellName();
        Integer drawableReference = dict_of_icons.get(key_for_dict);
        rightIconView.setImageResource(drawableReference);
    }

    protected void updateIcons(){
        updateLeftSpellIcon();
        updateRightSpellIcon();
    }



}

