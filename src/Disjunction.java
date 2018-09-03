import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;


/**
 * Representation of a logical disjunction.
 *
 * @author Ben Cottier
 */
public class Disjunction extends Sentence {

    /** The set of symbols contained in the disjunction. */
    protected Set<Variable> variables;
    protected Set<String> symbols;

    public Disjunction() {
        variables = new HashSet<>();
        symbols = new HashSet<>();
    }

    /**
     * Copy constructor.
     */
    public Disjunction(Disjunction d) {
        variables = new HashSet<>();
        symbols = new HashSet<>();
        for (Variable v: d.variables) {
            variables.add(new Variable(v));
            symbols.add(v.getSymbol());
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
        variables = new HashSet<>();
        symbols = new HashSet<>();
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
            variables.add(var);
            symbols.add(var.getSymbol());
        }
    }

    /**
     * If this is a unit clause, return its one variable.
     *
     * @return the variable if this is a unit clause, else null.
     */
    protected Variable getUnitClause() {
        if (variables.size() == 1) {
            Iterator<Variable> it = variables.iterator();
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
        for (Variable v : variables) {
            if (!that.variables.contains(v)) {
                return false;
            }
        }
        return variables.size() == that.variables.size();
    }

    @Override
    public int hashCode() {
        return Objects.hash(variables, symbols);
    }

    @Override
    public String toString() {
        String orSymbol = OR.replace("\\", "");
        String orAppend = " " + orSymbol + " ";
        StringBuilder sb = new StringBuilder();
        if (variables.size() > 1) {
            sb.append("(");
        }
        for (Variable v: variables) {
            sb.append(v.toString());
            sb.append(orAppend);
        }
        // Delete last OR
        sb.delete(sb.length() - 3, sb.length());
        if (variables.size() > 1) {
            sb.append(")");
        }
        return sb.toString();
    }
}
