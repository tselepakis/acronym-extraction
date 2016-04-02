import java.util.ArrayList;
import java.util.List;

/**
 * Definition of an Acronym object
 * 
 * @author Tselepakis Konstantinos <ktselepakis@gmail.com>
 *
 */

public class Acronym {
	int occurrences = 0;
	List<Expansion> possibleExpansions = new ArrayList<>();
	String acronym;
	String acronymExpantion = "";
	double acronymScore = 0.0 ;
}
