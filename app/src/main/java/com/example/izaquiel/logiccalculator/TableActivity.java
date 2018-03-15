package com.example.izaquiel.logiccalculator;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class TableActivity extends AppCompatActivity
{
    private String formula;
    private TruthTable tt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);

        this.formula = MainActivity.getFormula();
        init();

    }

    protected void init()
    {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.table, null);
        ScrollView sv = (ScrollView) v.findViewById(R.id.sv);

        GridLayout gl = new GridLayout(this);

        tt = new TruthTable(formula);
        boolean[][] table = tt.createTable();

        String[] propositionsName = tt.getPropositions();
        String[] subFormulas = tt.getSubFormulas();
        gl.setColumnCount(propositionsName.length + subFormulas.length);

        for (int i = 0; i < propositionsName.length; i++) {
            Button pn = new Button(this);
            pn.setBackgroundColor(Color.LTGRAY);
            pn.setText(propositionsName[i]);
            gl.addView(pn);
        }

        for (int i = 0; i < subFormulas.length; i++) {
            Button sf = new Button(this);
            sf.setBackgroundColor(Color.LTGRAY);
            sf.setText(subFormulas[i]);
            gl.addView(sf);
        }

        for (int i = 0; i < table.length; i++)
        {
            for (int j = 0; j < table[0].length; j++)
            {
                Button value = new Button(this);
                value.setBackgroundColor(Color.WHITE);
                value.setText(table[i][j]? "T" : "F");
                gl.addView(value);
            }
        }

        sv.addView(gl);
        setContentView(v);
    }
}
