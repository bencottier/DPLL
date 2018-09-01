/**
 * Literal: an atomic formula (one symbol) or its negation.
 */
public class Literal extends Sentence {

    boolean negated;
    String name;

    public boolean isNegated() {
        return negated;
    }

    public void negate() {
        negated = !negated;
    }

    public String getName() {
        return name;
    }

    /**
     * Check if the sentence contains nothing.
     *
     * @return true if the sentence is empty.
     */
    public boolean isEmpty() {
        return false;
    }

    /**
     * Check if the sentence has an empty clause, a clause where all
     * variables have been assigned in a way that makes the
     * corresponding literals false.
     *
     * @return true if the sentence has an empty clause.
     */
    boolean hasEmptyClause() {
        return false;
    }

    /**
     * Return the first unit clause of a sentence if present.
     * Unit clause: consists of one literal.
     *
     * @return the first (if any) unit clause in the sentence, else null.
     */
    Literal getUnitClause() {
        return this;
    }

    /**
     * Return the first pure clause of a sentence if present.
     * Pure clause: appears as only positive or negative.
     *
     * @return the first (if any) pure clause in the sentence, else null.
     */
    Literal getPureLiteral();

    /**
     * Return a simplified sentence after removing the given literal.
     * Removing the literal is equivalent to assigning it 'true'.
     *
     * @param u literal to remove from the sentence.
     * @return new sentence with literal removed.
     */
    Sentence assign(Literal u);

}
