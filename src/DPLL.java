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
    public static boolean dpllSatisfiable(CNFSentence s, int depth) {
        System.out.println(s);
        if (s.isEmpty()) {
            return true;
        }
        if (s.hasEmptyClause()) {
            return false;
        }
        Variable u = s.getUnitClause();
        if (u != null) {
            System.out.println(depth + ". assign " + u.toString());
            return dpllSatisfiable(s.assign(u), depth + 1);
        }
        u = s.getPureLiteral();
        if (u != null) {
            System.out.println(depth + ". assign " + u.toString());
            return dpllSatisfiable(s.assign(u), depth + 1);
        }
        u = s.pickVariable();
        System.out.println(depth + ". assign " + u.toString());
        if (dpllSatisfiable(s.assign(u), depth + 1)) {
            return true;
        } else {
            u = new Variable(u.getSymbol(), !u.isNegated());
            System.out.println(depth + ". assign " + u.toString());
            return dpllSatisfiable(s.assign(u), depth + 1);
        }
    }

    public static void main(String[] args) {
        String repr = args[0];
        CNFSentence s = new CNFSentence(repr);
        System.out.println("\nSentence = " + s.toString() + "\n");
        System.out.println("Steps:");
        if (dpllSatisfiable(s, 1)) {
            System.out.println("\nSentence is satisfiable.");
        } else {
            System.out.println("\nSentence is not satisfiable.");
        }
    }

}
