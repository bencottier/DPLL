import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of the Davis-Putnam-Logeman-Loveland (DPLL) algorithm.
 *
 * @author Ben Cottier
 */
public class DPLL {

    private static int numTrue = 3;

    /**
     * Determine whether the given sentence is satisfiable using DPLL.
     *
     * @param s sentence to analyse.
     * @return true if the sentence is satisfiable.
     */
    public static boolean dpllSatisfiable(CNFSentence s,
                                          List<Variable> assigned) {
        System.out.println(s);
        if (s.isEmpty()) {
            return true;
        }
        if (s.hasEmptyClause()) {
            return false;
        }
        Variable u = s.getUnitClause();
        if (u != null) {
            System.out.println("Assign " + u.toString());
            assigned.add(u);
            return dpllSatisfiable(s.assign(u), assigned);
        }
        u = s.getPureLiteral();
        if (u != null) {
            System.out.println("Assign " + u.toString());
            assigned.add(u);
            return dpllSatisfiable(s.assign(u), assigned);
        }
        Variable v = s.pickVariable();
        System.out.println("Assign " + v.toString());
        assigned.add(v);
        if (dpllSatisfiable(s.assign(v), assigned) && numTrueMatch(assigned)) {
            return true;
        } else {
            v = new Variable(v.getSymbol(), !v.isNegated());
            System.out.println("Assign " + v.toString());
            assigned.add(v);
            return dpllSatisfiable(s.assign(v), assigned);
        }
    }

    private static boolean numTrueMatch(List<Variable> assigned) {
        int count = 0;
        for (Variable v : assigned) {
            if (!v.isNegated()) {
                count++;
            }
        }
        return count == numTrue;
    }

    public static void main(String[] args) {
        String repr = args[0];
        CNFSentence s = new CNFSentence(repr);
        System.out.println("\nSentence: " + s.toString() + "\n");
        System.out.println("Steps:");
        List<Variable> assigned = new ArrayList<>();
        if (dpllSatisfiable(s, assigned)) {
            System.out.println("\nSentence is satisfiable");
        } else {
            System.out.println("\nSentence is not satisfiable");
        }
    }

}
