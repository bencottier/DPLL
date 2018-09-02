import java.util.*;

/**
 * Representation of a logical sentence in conjunctive normal form (CNF).
 */
public class CNFSentence extends Sentence {

    private Set<Disjunction> clauses;
    private Set<Variable> variables;

    /**
     * Constructor.
     *
     */
    public CNFSentence() {
        clauses = new HashSet<>();
        variables = new HashSet<>();
    }

    /**
     * Copy constructor.
     *
     * @param s the sentence to copy.
     */
    private CNFSentence(CNFSentence s) {
        clauses = new HashSet<>();
        variables = new HashSet<>();
        for (Disjunction d: s.clauses) {
            clauses.add(new Disjunction(d));
        }
        for (Variable v : s.variables) {
            variables.add(new Variable(v));
        }
    }

    /**
     * Construct a sentence from a string representation.
     *
     * Expects a certain format, e.g.
     * (~P v Q) & (R v S)
     *
     * @param repr the string representation of the sentence.
     * @throws IllegalArgumentException if the format is incorrect.
     */
    public CNFSentence(String repr)
            throws IllegalArgumentException {
        clauses = new HashSet<>();
        variables = new HashSet<>();
        if (repr.length() == 0) {
            return;
        }
        String[] splitAND = repr.split(AND);
        for (String c : splitAND) {
            c = c.trim();
            c = c.replaceAll("\\(", "");
            c = c.replaceAll("\\)", "");
            Disjunction d = new Disjunction(c);
            clauses.add(d);
            for (Variable v: d.variables) {
                // Keep unsigned
                variables.add(new Variable(v.getName(), false));
            }
        }
    }

    /**
     * Check if the sentence contains nothing.
     *
     * @return true if the sentence is empty.
     */
    boolean isEmpty() {
        return clauses.size() == 0;
    }

    /**
     * Check if the sentence has an empty clause, a clause where all
     * variables have been assigned in a way that makes the
     * corresponding variables false.
     *
     * @return true if the sentence has an empty clause.
     */
    boolean hasEmptyClause() {
        for (Disjunction d: clauses) {
            if (d.variables.size() == 0) {
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
    Variable getUnitClause() {
        for (Disjunction d: clauses) {
            Variable unit = d.getUnitClause();
            if (unit != null) {
                return unit;
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
    Variable getPureLiteral() {
        boolean negated;
        boolean nextLiteral = false;
        for (Variable u: variables) {
            negated = u.isNegated();
            for (Disjunction d: clauses) {
                for (Variable v: d.variables) {
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
     * Remove any variables that no longer appear in the sentence.
     *
     * @param s the sentence to update
     */
    private static void updateVariables(CNFSentence s) {
        HashSet<Variable> toRemove = new HashSet<>();
        boolean found;
        for (Variable v : s.variables) {
            found = false;
            for (Disjunction d : s.clauses) {
                if (d.variables.contains(v)) {
                    found = true;
                }
            }
            if (!found) {
                toRemove.add(v);
            }
        }
        s.variables.removeAll(toRemove);
    }

    /**
     * Return a simplified sentence after removing the given literal.
     * Removing the literal is equivalent to assigning it 'true'.
     *
     * @param u literal to remove from the sentence.
     * @return new sentence with literal removed.
     */
    CNFSentence assign(Variable u) {
        // We implicitly assign true to u
        // Copy this sentence
        CNFSentence s = new CNFSentence(this);
        for (Disjunction d: clauses) {
            // Check for unit clause
            Variable unit = d.getUnitClause();
            if (unit != null) {
                if (u.getName().equals(unit.getName())) {
                    // The unit clause is u, so assign it
                    if (u.isNegated() == unit.isNegated()) {
                        // ANDing with true is redundant
                        s.clauses.remove(d);
                    } else {
                        // ANDing with false is false
                        // Sentence reduced to an empty clause; nothing to do
                        s.clauses = new HashSet<>();
                        s.clauses.add(new Disjunction());
                        s.variables = new HashSet<>();
                        break;
                    }
                }
            } else {
                // Not a unit clause, meaning it is a disjunction
                // Check if u is in the clause
                for (Variable v: d.variables) {
                    if (u.getName().equals(v.getName())) {
                        // u is in this clause, so assign it
                        if (u.isNegated() == v.isNegated()) {
                            // Same sign --> true
                            // ORing with true is true
                            // Then ANDing with true is redundant
                            s.clauses.remove(d);
                        } else {
                            // Different sign --> false
                            // ORing with false is redundant
                            d.variables.remove(u);
                        }
                    }
                }
            }
        }
        updateVariables(s);
        return s;
    }

    /**
     * Pick a variable to for assignment based on occurrence.
     *
     * @return variable with the highest occurrence in the sentence.
     */
    public Variable pickVariable() {
        Map<Variable, Integer> counts = new HashMap<>();
        Variable choice = null;
        int maxCount = 0;
        for (Disjunction d: clauses) {
            for (Variable u: d.variables) {
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

    @Override
    public String toString() {
        String andSymbol = AND.replace("\\", "");
        String andAppend = " " + andSymbol + " ";
        StringBuilder sb = new StringBuilder();
        for (Disjunction d: clauses) {
            sb.append(d.toString());
            sb.append(andAppend);
        }
        // Delete last AND
        if (sb.length() > 4) {
            sb.delete(sb.length() - 3, sb.length());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String[] tests = {"", "A", "~A & B", "(A v B) & C",
                "(A v B) & (C v D) & (E v ~F v G)"};
        for (String test : tests) {
            Sentence s = new CNFSentence(test);
            System.out.println(s.toString());
        }
    }

}
