import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;


/**
 * Representation of a logical disjunction.
 */
public class Disjunction extends Sentence {

    /** The set of variables contained in the disjunction. */
    protected Set<Variable> clauses;
    protected Set<String> variables;

    public Disjunction() {
        clauses = new HashSet<>();
        variables = new HashSet<>();
    }

    /**
     * Copy constructor.
     */
    public Disjunction(Disjunction d) {
        clauses = new HashSet<>();
        variables = new HashSet<>();
        for (Variable v: d.clauses) {
            clauses.add(new Variable(v));
            variables.add(v.getName());
        }
    }

    /**
     * Construct a sentence from a string representation.
     *
     * Expects a certain format, e.g.
     * ~P v Q
     *
     * @param repr the string representation of the sentence.
     * @throws IllegalArgumentException if the format is incorrect.
     */
    public Disjunction(String repr)
            throws IllegalArgumentException {
        clauses = new HashSet<>();
        variables = new HashSet<>();
        if (repr.length() == 0) {
            return;
        }
        String[] splitOR = repr.split(OR);
        for (String v : splitOR) {
            v = v.trim();
            boolean negated = false;
            if (v.contains(NOT)) {
                v = v.replace(NOT, "");
                negated = true;
            }
            Variable var = new Variable(v, negated);
            clauses.add(var);
            variables.add(var.getName());
        }
    }

    /**
     * If this is a unit clause, return its one variable.
     *
     * @return the variable if this is a unit clause, else null.
     */
    protected Variable getUnitClause() {
        if (clauses.size() == 1) {
            Iterator<Variable> it = clauses.iterator();
            return it.next();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disjunction that = (Disjunction) o;
        // Check each variable
        for (Variable clause : clauses) {
            if (!that.clauses.contains(clause)) {
                return false;
            }
        }
        return clauses.size() == that.clauses.size();
    }

    @Override
    public int hashCode() {
        return Objects.hash(clauses, variables);
    }

    @Override
    public String toString() {
        String orSymbol = OR.replace("\\", "");
        String orAppend = " " + orSymbol + " ";
        StringBuilder sb = new StringBuilder();
        if (clauses.size() > 1) {
            sb.append("(");
        }
        for (Variable v: clauses) {
            sb.append(v.toString());
            sb.append(orAppend);
        }
        // Delete last OR
        sb.delete(sb.length() - 3, sb.length());
        if (clauses.size() > 1) {
            sb.append(")");
        }
        return sb.toString();
    }
}
