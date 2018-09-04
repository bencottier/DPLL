import java.util.*;

/**
 * Representation of a logical sentence in conjunctive normal form (CNF).
 *
 * @author Ben Cottier
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
            variables.addAll(d.variables);
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
     * symbols have been assigned in a way that makes the
     * corresponding symbols false.
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
            Variable u = d.getUnitClause();
            if (u != null) return u;
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
        for (Variable u: variables) {
            // Check if its negation is also in the sentence
            if (!variables.contains(
                    new Variable(u.getSymbol(), !u.isNegated()))) {
                return u;
            }
        }
        return null;
    }

    /**
     * Remove any symbols that no longer appear in the sentence.
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
                if (u.getSymbol().equals(unit.getSymbol())) {
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
                Variable negU = new Variable(u.getSymbol(), !u.isNegated());
                if (d.variables.contains(u)) {
                    // u is in the clause with same sign --> true
                    // ORing with true is true
                    // Then ANDing with true is redundant
                    s.clauses.remove(d);
                } else if (d.variables.contains(negU)) {
                    // Different sign --> false
                    // ORing with false is redundant
                    s.clauses.remove(d); // to be replaced
                    Disjunction dCopy = new Disjunction(d);
                    dCopy.variables.remove(negU);
                    s.clauses.add(dCopy);
                    // Remove symbol if it is no longer in the disjunction
                    if (!d.variables.contains(u)) {
                        dCopy.symbols.remove(u.getSymbol());
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
        Map<String, Integer> counts = new HashMap<>();
        Variable choice = null;
        int maxCount = 0;
        for (Disjunction d: clauses) {
            for (Variable u: d.variables) {
                Integer count = counts.get(u.getSymbol());
                if (count == null) {
                    counts.put(u.getSymbol(), 1);
                    continue;
                }
                counts.put(u.getSymbol(), ++count);
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
        if (sb.toString().length() == 0) {
            sb.append("*");
        }
        // Delete last AND
        if (sb.length() >= 3) {
            sb.delete(sb.length() - 3, sb.length());
            if (sb.toString().length() == 0) {
                sb.append("()");
            }
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
