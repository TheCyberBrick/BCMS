package tcb.bcms.manipulation.impl.methods.insn;

import org.objectweb.asm.tree.MethodInsnNode;

public abstract class MethodInsnManipulator extends MultiInsnManipulator<MethodInsnNode> {

	/**
	 * Creates a new Manipulator with the specified name that only accepts an object of the type MethodInsnNode 
	 * to be identified and manipulated (the required previous instructions included)
	 * Supported child Manipulator<?>: N/A
	 * @param name String
	 */
	public MethodInsnManipulator(String name, boolean identificationRequired) {
		super(name, MethodInsnNode.class, identificationRequired);
	}
	
	/**
	 * Creates a new Manipulator that tries to identify a MethodInsnNode by going back until it finds the first LabelNode that is after the minimum offset
	 * and tries to identify the instructions in between. Can be used to identify if/if else and 
	 * method instructions by looking at the needed instructions for example.
	 * Supported child Manipulator<?>: N/A
	 * @param name String
	 * @param minOffset Defines a minimum offset or size of the instruction array to identify
	 */
	public MethodInsnManipulator(String name, int minOffset, boolean identificationRequired) {
		super(name, MethodInsnNode.class, minOffset, identificationRequired);
	}
}
