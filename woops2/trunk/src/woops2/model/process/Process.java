package woops2.model.process;

import woops2.model.activity.Activity;

/**
 * @author deder
 * 
 * A Process is a special Activity that describes a structure for particular
 * types of development projects. To perform such a development project a
 * Processes would be 'instantiated' and adapted for the specific situation.
 * Process is an abstract class and this meta-model defines different special
 * types of Processes for different process management applications and
 * different situations of process reuse.
 * 
 */
public class Process extends Activity {

	/**
	 * Default constructor
	 *
	 */
	public Process() {
		super();
	}
}
