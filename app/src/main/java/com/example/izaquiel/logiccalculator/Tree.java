package com.example.izaquiel.logiccalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by izaquiel on 13/03/18.
 */

public class Tree
{

    private Node root;
    private List<String> propositions;

    public Tree()
    {
        this.root = null;
        this.propositions = new ArrayList<>();
    }

    public String[] getPropositions() {
        Collections.sort(propositions);
        return propositions.toArray(new String[propositions.size()]);
    }public void setPropositions(List<String> propositions) {
    this.propositions = propositions;
}public int getTotalPropostions() {
    return propositions.size();
}

    public Node getRoot() {
        return root;
    }public void setRoot(Node root) {
    this.root = root;
}


    public void buildTree(String formula)
    {
        this.root = this.buildTree(root, formula);
    }

    private Node buildTree(Node node, String formula)
    {
        if(formula.contains(Operator.THEN))
        {
            int index = formula.indexOf(Operator.THEN);
            node = new Node(Operator.THEN);

            String previous = formula.substring(0, index);
            String posterior = formula.substring((index+Operator.THEN.length()), formula.length());

            node.setLeftOf(buildTree(node, previous));
            node.setRightOf(buildTree(node, posterior));

            return node;
        }

        else if(formula.contains(Operator.OR))
        {
            int index = formula.indexOf(Operator.OR);
            String previous = formula.substring(0, index);
            String posterior = formula.substring((index+Operator.OR.length()), formula.length());

            node = new Node(Operator.OR);
            node.setLeftOf(buildTree(node, previous));
            node.setRightOf(buildTree(node, posterior));

            return node;
        }

        else if(formula.contains(Operator.AND))
        {
            int index = formula.indexOf(Operator.AND);
            node = new Node(Operator.AND);
            String previous = formula.substring(0, index);
            String posterior = formula.substring((index+Operator.AND.length()), formula.length());

            node.setLeftOf(buildTree(node, previous));
            node.setRightOf(buildTree(node, posterior));

            return node;
        }

        else if(formula.contains(Operator.NOT))
        {
            int index = formula.indexOf(Operator.NOT);
            String posterior = formula.substring((index+Operator.NOT.length()), formula.length());
            node = new Node(Operator.NOT);
            node.setRightOf(buildTree(node, posterior));

            return node;
        }

        this.propositions.add(formula);
        return new Node(formula);
    }

    public boolean evaluate(Map<String, Boolean> values) {
        return this.evaluate(values, this.root);
    }

    public boolean evaluate(Map<String, Boolean> values, Node node)
    {
        if (node.getLeftOf() == null && node.getRightOf() == null)
            return Boolean.valueOf(values.get(node.getToken()));

        else if(node.getToken() == Operator.NOT)
        {
            boolean value = evaluate(values, node.getRightOf());
            return this.computeOperation(value);
        }
        boolean leftValue = evaluate(values, node.getLeftOf());
        boolean rightValue = evaluate(values, node.getRightOf());

        return this.computeOperation(leftValue, rightValue, node.getToken());
    }

    public boolean computeOperation(boolean value)
    {
        //System.out.println(!value);
        return !value;
    }

    private boolean computeOperation(boolean leftValue, boolean rightValue, String token)
    {
        if (token.equals(Operator.THEN))
            return (leftValue && !rightValue ? false : true);

        else if (token.equals(Operator.OR))
            return (leftValue || rightValue);


        return (leftValue && rightValue);

    }


    public String[] getSubformulas()
    {
        List<String> result = new ArrayList<String>();
        this.getSubformulas(this.root, result);
        String[] resultAsArray = new String[result.size()];

        return result.toArray(resultAsArray);
    }

    private List<String> getSubformulas(Node node, List<String> formulas)
    {
        if (node != null)
        {
            if (node.getSubformula().length() != 1 && !formulas.contains(node.getSubformula()))
                formulas.add(node.getSubformula());


            getSubformulas(node.getLeftOf(), formulas);
            getSubformulas(node.getRightOf(), formulas);
        }
        return formulas;
    }
}
