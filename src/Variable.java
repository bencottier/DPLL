import java.util.Objects;

/**
 * Representation of an atomic logical formula (one symbol) or its negation.
 *
 * @author Ben Cottier
 */
public class Variable extends Sentence {

    private String symbol;
    private boolean negated;

    public Variable(String symbol, boolean negated) {
        this.symbol = symbol;
        this.negated = negated;
    }

    /**
     * Copy constructor.
     */
    public Variable(Variable v) {
        this.symbol = v.symbol;
        this.negated = v.negated;
    }

    public boolean isNegated() {
        return negated;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return negated == variable.negated &&
                Objects.equals(symbol, variable.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, negated);
    }

    @Override
    public String toString() {
        if (negated) {
            return NOT + symbol;
        } else {
            return symbol;
        }
    }
}
