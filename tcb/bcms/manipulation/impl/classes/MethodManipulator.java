package tcb.bcms.manipulation.impl.classes;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodNode;

import tcb.bcms.manipulation.impl.Manipulator;
import tcb.bcms.manipulation.impl.methods.insn.MultiInsnManipulator;


public abstract class MethodManipulator extends Manipulator<MethodNode> {
	/**
	 * Creates a new Manipulator with the specified name that only accepts an object of the type MethodNode to be identified and manipulated.
	 * Supported child Manipulator<?>: MultiInsnManipulator<?>, LocalVariableManipulator
	 * @param name String
	 */
	public MethodManipulator(String name, boolean identificationRequired) {
		super(name, MethodNode.class, identificationRequired);
	}

	/**
	 * Returns the name of the identified method
	 * @return String
	 */
	public String getIdentifiedMethodName() {
		return this.getIdentifiedObject().name;
	}

	/**
	 * Tries to identify the object with the child Manipulator and modifies it
	 * @param child Manipulator<?>
	 * @param obj Object
	 * @return Boolean
	 */
	@Override
	protected boolean identifyChild(Manipulator<?> child, MethodNode obj) {
		MethodNode methodNode = (MethodNode) obj;
		for(int i = 0; i < methodNode.instructions.size(); i++) {
			AbstractInsnNode insnNode = methodNode.instructions.get(i);
			if(child instanceof MultiInsnManipulator && ((MultiInsnManipulator<?>) child).getInsnType().isAssignableFrom(insnNode.getClass())) {
				if(child.initManipulation(insnNode)) {
					return true;
				}
			}
		}
		return false;
	}
}
