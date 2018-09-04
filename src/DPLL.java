/**
 * Implementation of the Davis-Putnam-Logeman-Loveland (DPLL) algorithm.
 *
 * @author Ben Cottier
 */
public class DPLL {

    /**
     * Determine whether the given sentence is satisfiable using DPLL.
     *
     * @param s sentence to analyse.
     * @return true if the sentence is satisfiable.
     */
    public static boolean dpllSatisfiable(CNFSentence s) {
        System.out.println(s);
        if (s.isEmpty()) {
            return true;
        }
        if (s.hasEmptyClause()) {
            return false;
        }
        Variable u = s.getUnitClause();
        if (u != null) {
            return dpllSatisfiable(s.assign(u));
        }
        u = s.getPureLiteral();
        if (u != null) {
            return dpllSatisfiable(s.assign(u));
        }
        Variable v = s.pickVariable();
        if (dpllSatisfiable(s.assign(v))) {
            return true;
        } else {
            v = new Variable(v.getSymbol(), !v.isNegated());
            return dpllSatisfiable(s.assign(v));
        }
    }

    public static void main(String[] args) {
        String repr = args[0];
        CNFSentence s = new CNFSentence(repr);
        System.out.println("\nSentence: " + s.toString() + "\n");
        System.out.println("Steps:");
        if (dpllSatisfiable(s)) {
            System.out.println("\nSentence is satisfiable");
        } else {
            System.out.println("\nSentence is not satisfiable");
        }
    }

}
