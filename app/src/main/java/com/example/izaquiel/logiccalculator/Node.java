package com.example.izaquiel.logiccalculator;

/**
 * Created by izaquiel on 13/03/18.
 */

public class Node
{
    private String token;
    private boolean value;
    private Node rightOf, leftOf;


    public Node(String token)
    {
        this.setToken(token);
    }

    public String getToken() {
        return token;
    }public void setToken(String token) {
    this.token = token;
}

    public boolean getValue() {
        return value;
    }public void setValue(boolean value) {
    this.value = value;
}

    public Node getLeftOf() {
        return leftOf;
    }public void setLeftOf(Node leftOf) {
    this.leftOf = leftOf;
};

    public Node getRightOf() {
        return rightOf;
    }public void setRightOf(Node rightOf) {
    this.rightOf = rightOf;
}


    public String getSubformula()
    {
        if (this.leftOf != null || this.rightOf != null)
        {
            if(this.token.equals(Operator.NOT))
                return(this.token + this.rightOf.getSubformula());

            return (this.leftOf.getSubformula() + this.token + this.getRightOf().getSubformula());
        }

        return this.token;
    }

}
