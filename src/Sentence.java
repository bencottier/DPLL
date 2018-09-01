import java.util.List;
import java.util.Set;

public abstract class Sentence {

    abstract protected Set<Sentence> getClauses();

    abstract protected Set<Literal> getLiterals();

    abstract public Literal pickVariable();

    /**
     * Check if the sentence contains nothing.
     *
     * @return true if the sentence is empty.
     */
    abstract boolean isEmpty();

    /**
     * Check if the sentence has an empty clause, a clause where all
     * variables have been assigned in a way that makes the
     * corresponding literals false.
     *
     * @return true if the sentence has an empty clause.
     */
    abstract boolean hasEmptyClause();

    /**
     * Return the first unit clause of a sentence if present.
     * Unit clause: consists of one literal.
     *
     * @return the first (if any) unit clause in the sentence, else null.
     */
    abstract Literal getUnitClause();

    /**
     * Return the first pure clause of a sentence if present.
     * Pure clause: appears as only positive or negative.
     *
     * @return the first (if any) pure clause in the sentence, else null.
     */
    abstract Literal getPureLiteral();

    /**
     * Return a simplified sentence after removing the given literal.
     * Removing the literal is equivalent to assigning it 'true'.
     *
     * @param u literal to remove from the sentence.
     * @return new sentence with literal removed.
     */
    abstract Sentence assign(Literal u);

}
