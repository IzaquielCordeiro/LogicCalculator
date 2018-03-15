package com.example.izaquiel.logiccalculator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
{
    private TextView _screen;
    private static String display = "";
    private String operators = Operator.AND + Operator.OR + Operator.THEN + Operator.NOT;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _screen = (TextView)findViewById(R.id.textView);
    }

    private void updateScreen()
    {
        _screen.setText(display);
    }

    protected void onClickPropositions(View v)
    {
        Button b = (Button) v;
        if (display.length() > 0)
        {
            String last = Character.toString(display.charAt(display.length() - 1));
            if (operators.contains(last))
            {
                display += b.getText();
                updateScreen();
            }
        }
        else if (display.length() == 0)
        {
            display += b.getText();
            updateScreen();
        }
    }

    protected void onClickNot(View v)
    {
        Button b = (Button) v;
        if (display.length() > 0) {
            String last = Character.toString(display.charAt(display.length()-1));
            if (!operators.contains(last)) {
                Toast.makeText(this, "The previous term must be a operator.", Toast.LENGTH_LONG).show();
            }
            else {
                display += b.getText();
                updateScreen();
            }
        }
        else {
            display += b.getText();
            updateScreen();
        }
    }

    protected void onClickOperator(View v)
    {
        Button b = (Button) v;
        if (display.length() > 0) {
            String last = Character.toString(display.charAt(display.length()-1));
            if (operators.contains(last)) {
                Toast.makeText(this, "The next term must be a proposition.", Toast.LENGTH_SHORT).show();
            }
            else {
                display += b.getText();
                updateScreen();
            }
        }
        else
            Toast.makeText(this, "The previous term must be a proposition.", Toast.LENGTH_SHORT).show();

    }

    protected void onClickDel(View v)
    {
        if(display.length() > 0) {
            display = display.substring(0, display.length() - 1);
        }
        else if(display.length() == 1)
        {
            display = "";
        }
        updateScreen();
    }


    protected void onClickGo(View v)
    {
        if (display.length()> 0) {
            String last = Character.toString(display.charAt(display.length()-1));
            if (!operators.contains(last) && last!=Operator.NOT) {
                Intent myIntent = new Intent(v.getContext(), TableActivity.class);
                startActivityForResult(myIntent, 0);
            }
        }
    }

    protected static String getFormula()
    {
        return display;
    }
}
