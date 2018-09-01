import java.util.*;

public class CNFSentence extends Sentence {

    private Set<Sentence> clauses;
    private Set<Literal> literals;

    public CNFSentence() {
        clauses = new HashSet<>();
        literals = new HashSet<>();
    }

    public void addClause(Sentence clause) {clauses.add(clause);}

    public void removeClause(Sentence clause) {clauses.remove(clause);}

    protected Set<Sentence> getClauses() {return clauses;}

    protected Set<Literal> getLiterals() {return literals;}

    protected int numberOfLiterals() {return literals.size();}

    public Literal pickVariable() {
        Map<Literal, Integer> counts = new HashMap<>();
        Literal choice = null;
        int maxCount = 0;
        for (Sentence clause: clauses) {
            for (Literal u: clause.getLiterals()) {
                Integer count = counts.get(u);
                if (count == null) {
                    counts.put(u, 0);
                    continue;
                }
                counts.put(u, ++count);
                if (count > maxCount) {
                    maxCount = count;
                    choice = u;
                }
            }
        }
        return choice;
    }

    /**
     * Check if the sentence contains nothing.
     *
     * @return true if the sentence is empty.
     */
    boolean isEmpty() {
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
        for (Sentence clause: clauses) {
            if (clause.hasEmptyClause()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return the first unit clause of a sentence if present.
     * Unit clause: consists of one literal.
     *
     * @return the first (if any) unit clause in the sentence, else null.
     */
    Literal getUnitClause() {
        for (Sentence clause: clauses) {
            if (clause.getUnitClause() != null) {
                return (Literal) clause;
            }
        }
        return null;
    }

    /**
     * Return the first pure literal of a sentence if present.
     * Pure literal: appears as only positive or negative.
     *
     * @return the first (if any) pure literal in the sentence, else null.
     */
    Literal getPureLiteral() {
        boolean negated;
        boolean nextLiteral = false;
        for (Literal u: literals) {
            negated = u.isNegated();
            for (Sentence clause: clauses) {
                for (Literal v: clause.getLiterals()) {
                    if (u.getName().equals(v.getName())
                            && v.isNegated() != negated) {
                        nextLiteral = true;
                        break;
                    }
                }
                if (nextLiteral) {
                    break;
                }
            }
            if (nextLiteral) {
                break;
            }
            return u;
        }
        return null;
    }

    /**
     * Return a simplified sentence after removing the given literal.
     * Removing the literal is equivalent to assigning it 'true'.
     *
     * @param u literal to remove from the sentence.
     * @return new sentence with literal removed.
     */
    Sentence assign(Literal u) {
        // We implicitly assign true to u
        // Copy this sentence
        Sentence s = new CNFSentence(this);
        for (Sentence clause: clauses) {
            // Check for unit clause
            Literal unit = clause.getUnitClause();
            if (unit != null) {
                if (u.getName().equals(unit.getName())) {
                    // The unit clause is u, so assign it
                    if (u.isNegated() == unit.isNegated()) {
                        // ANDing with true is redundant
                        removeClause(clause);
                    } else {
                        // ANDing with false is false
                        return new EmptyClause();
                    }
                } else {
                    // Some other unit clause - move on
                    continue;
                }
            } else {
                // Not a unit clause, meaning it is a disjunction
                // Check if u is in the clause
                for (Literal v: clause.getLiterals()) {
                    if (u.getName().equals(v.getName())) {
                        // u is in this clause, so assign it
                        if (u.isNegated() == v.isNegated()) {
                            // ORing with true is true
                            // Then ANDing with true is redundant
                            removeClause(clause);
                        } else {
                            // ORing with false is redundant
                            clause.removeLiteral(u);
                        }
                    }
                }
            }
            return s;
        }
    }

}
