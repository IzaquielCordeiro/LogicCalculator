package com.example.izaquiel.logiccalculator;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by izaquiel on 13/03/18.
 */

public class TruthTable
{

    private Tree tree;
    private boolean[][] values;
    private String[] propositions;
    private String[] subFormulas;


    public TruthTable(String formula)
    {
        this.tree = new Tree();
        this.tree.buildTree(formula);
        this.propositions = tree.getPropositions();
        this.subFormulas = tree.getSubformulas();
    }

    public String[] getPropositions() {
        return propositions;
    }public void setPropositions(String[] propositions) {
    this.propositions = propositions;
}

    public boolean[][] getValues() {
        return values;
    }


    public String[] getSubFormulas() {
        return subFormulas;
    }public void setSubFormulas(String[] subFormulas) {
    this.subFormulas = subFormulas;
}

    public boolean[][] createTable()
    {
        setValues();
        return createTable(this.tree.getRoot(), 0, propositions.length);

    }


    private boolean[][] createTable(Node node, int row, int column)
    {
        if(column < this.values[0].length && node != null)
        {
            if (node.getLeftOf() != null || node.getRightOf() != null)
            {
                if (row < values.length) {

                    Map<String, Boolean> propsValue = new TreeMap<>();

                    for (int i = 0; i < propositions.length; i++) {
                        propsValue.put(propositions[i], values[row][i]);
                    }
                    values[row][column] = tree.evaluate(propsValue, node);
                    createTable(node, ++row, column);
                    createTable(node.getRightOf(), 0, ++column);
                }

                createTable(node.getLeftOf(), 0, ++column);
                createTable(node.getRightOf(), 0, ++column);
            }

        }
        return values;
    }

    private void setValues()
    {
        int rows = (int) Math.pow(2, propositions.length);
        int columns = subFormulas.length + propositions.length;

        this.values = new boolean[rows][columns];


        for (int r=0; r<rows; r++)
        {
            String bin = Integer.toBinaryString(r);

            while (bin.length() < propositions.length)
                bin = "0" + bin;

            boolean[] boolArray = new boolean[propositions.length];
            int i=0;

            while (i<propositions.length)
            {
                char bit = bin.charAt(i);
                boolArray[i] = (Character.toString(bit).equals("0")) ? false : true;
                i++;
            }
            for (int c = 0; c < propositions.length; c++)
                this.values[r][c] = boolArray[c];
        }
    }

}
