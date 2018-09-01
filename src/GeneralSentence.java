import java.util.HashSet;
import java.util.Set;

public class MultiSentence extends Sentence {

    private Set<Sentence> clauses;

    public MultiSentence() {
        clauses = new HashSet<>();
    }

    public void addClause(Sentence clause) {
        clauses.add(clause);
    }

    /**
     * Check if the sentence contains nothing.
     *
     * @return true if the sentence is empty.
     */
    boolean isEmpty() {

    };

    /**
     * Check if the sentence has an empty clause, a clause where all
     * variables have been assigned in a way that makes the
     * corresponding literals false.
     *
     * @return true if the sentence has an empty clause.
     */
    boolean hasEmptyClause();

    /**
     * Return the first unit clause of a sentence if present.
     * Unit clause: consists of one literal.
     *
     * @return the first (if any) unit clause in the sentence, else null.
     */
    Literal getUnitClause();

    /**
     * Return the first pure clause of a sentence if present.
     * Pure clause: appears as only positive or negative.
     *
     * @return the first (if any) pure clause in the sentence, else null.
     */
    Literal getPureClause();

    /**
     * Return a simplified sentence after removing the given literal.
     * Removing the literal is equivalent to assigning it 'true'.
     *
     * @param u literal to remove from the sentence.
     * @return new sentence with literal removed.
     */
    Sentence assign(Literal u);

}
