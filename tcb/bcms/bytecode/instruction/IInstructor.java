package tcb.bcms.bytecode.instruction;

import java.util.List;

import org.objectweb.asm.tree.AbstractInsnNode;

public interface IInstructor {
	/**
	 * Returns the instructions.
	 * @return List<AbstractInsnNode>
	 */
	List<AbstractInsnNode> getInstructions();
}
