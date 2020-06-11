class AddExpression extends BinaryExpression implements Expression
{
   public AddExpression(final Expression lft, final Expression rht)
   {
      super(lft, rht, "+");
   }

   @Override
   protected double _applyOperator(double lft, double rht) {
      return lft + rht;
   }
}
