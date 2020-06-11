abstract class BinaryExpression implements Expression {
    private final Expression lft;
    private final Expression rht;
    private final String opr;

    public BinaryExpression(Expression lft, Expression rht, String opr){
        this.lft = lft;
        this.rht = rht;
        this.opr = opr;
    }

    protected abstract double _applyOperator(double lft, double rht);

    public String toString()
    {
        return "(" + lft + " " + opr + " " + rht + ")";
    }

    public double evaluate(Bindings bindings)
    {
        return _applyOperator(lft.evaluate(bindings), rht.evaluate(bindings));
    }
}

