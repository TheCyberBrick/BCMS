package tcb.bcms.manipulation.impl.methods.insn;

import org.objectweb.asm.tree.JumpInsnNode;

public abstract class JumpInsnManipulator extends MultiInsnManipulator<JumpInsnNode> {

	/**
	 * Creates a new Manipulator with the specified name that only accepts an object of the type JumpInsnNode 
	 * to be identified and manipulated (the required previous instructions included)
	 * Supported child Manipulator<?>: N/A
	 * @param name String
	 */
	public JumpInsnManipulator(String name, boolean identificationRequired) {
		super(name, JumpInsnNode.class, identificationRequired);
	}
	
	/**
	 * Creates a new Manipulator that tries to identify a JumpInsnNode by going back until it finds the first LabelNode that is after the minimum offset
	 * and tries to identify the instructions in between. Can be used to identify if/if else and 
	 * method instructions by looking at the needed instructions for example.
	 * Supported child Manipulator<?>: N/A
	 * @param name String
	 * @param minOffset Defines a minimum offset or size of the instruction array to identify
	 */
	public JumpInsnManipulator(String name, int minOffset, boolean identificationRequired) {
		super(name, JumpInsnNode.class, minOffset, identificationRequired);
	}
}
