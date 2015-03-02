package tcb.bcms.manipulation.impl.classes;

import org.objectweb.asm.tree.FieldNode;

import tcb.bcms.manipulation.impl.Manipulator;


public abstract class FieldManipulator extends Manipulator<FieldNode> {
	/**
	 * Creates a new Manipulator with the specified name that only accepts an object of the type FieldNode to be identified and manipulated
	 * Supported child Manipulator<?>: N/A
	 * @param name String
	 */
	public FieldManipulator(String name, boolean identificationRequired) {
		super(name, FieldNode.class, identificationRequired);
	}
	
	/**
	 * Returns the name of the identified field
	 * @return String
	 */
	public String getIdentifiedFieldName() {
		return this.getIdentifiedObject().name;
	}
}
