package tcb.bcms.manipulation.impl.methods.insn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;

import tcb.bcms.manipulation.impl.Manipulator;
import tcb.bcms.manipulation.impl.classes.MethodManipulator;


public abstract class MultiInsnManipulator<T> extends Manipulator<AbstractInsnNode> {
	private final Class<T> nodeType;
	private AbstractInsnNode[] identifiedInstructions;
	private final int offset;
	
	/**
	 * Creates a new Manipulator that tries to identify the given instruction by going back until it finds the first LabelNode and tries to identify
	 * the instructions in between. Can be used to identify if/if else and method instructions by looking at the needed instructions for example.
	 * Supported child Manipulator<?>: N/A
	 * @param name String
	 * @param type Type of the node to be identified
	 */
	public MultiInsnManipulator(String name, Class<T> type, boolean identificationRequired) {
		super(name, AbstractInsnNode.class, identificationRequired);
		this.nodeType = type;
		this.offset = -1;
	}
	
	/**
	 * Creates a new Manipulator that tries to identify the given instruction by going back until it finds the first LabelNode that is after the minimum offset
	 * and tries to identify the instructions in between. Can be used to identify if/if else and 
	 * method instructions by looking at the needed instructions for example.
	 * Supported child Manipulator<?>: N/A
	 * @param name String
	 * @param type Type of the node to be identified
	 * @param minOffset Defines a minimum offset or size of the instruction array to identify
	 */
	public MultiInsnManipulator(String name, Class<T> type, int minOffset, boolean identificationRequired) {
		super(name, AbstractInsnNode.class, identificationRequired);
		this.nodeType = type;
		this.offset = minOffset;
	}

	public final Class<T> getInsnType() {
		return this.nodeType;
	}
	
	/**
	 * Returns the identified array of instructions
	 * @return
	 */
	public final AbstractInsnNode[] getIdentifiedInstructions() {
		return this.identifiedInstructions;
	}
	
	/**
	 * Returns whether the given object can be identified by this Manipulator
	 * @param obj Object
	 * @return Boolean
	 */
	protected boolean identify(AbstractInsnNode obj) {
		if(obj instanceof LabelNode == false && this.nodeType.isAssignableFrom(obj.getClass())) {
			MethodManipulator parent = (MethodManipulator) this.getParent();
			MethodNode methodNode = parent.getCurrentObject();
			int insnIndex = methodNode.instructions.indexOf((AbstractInsnNode) obj);
			List<AbstractInsnNode> prevNodes = new ArrayList<AbstractInsnNode>();
			AbstractInsnNode prevNode = null;
			int offset = 1;
			while(insnIndex - offset >= 0 && (prevNode = methodNode.instructions.get(insnIndex - offset)) != null) {
				if(prevNode instanceof LabelNode && offset > this.offset) {
					break;
				}
				offset++;
				prevNodes.add(prevNode);
			}
			if(offset <= this.offset) {
				return false;
			}
			Collections.reverse(prevNodes);
			prevNodes.add((AbstractInsnNode) obj);
			AbstractInsnNode[] prevNodesArray = new AbstractInsnNode[prevNodes.size()];
			prevNodesArray = prevNodes.toArray(prevNodesArray);
			if(this.identifyInstructions(prevNodesArray)) {
				this.identifiedInstructions = prevNodesArray;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns whether the given instructions can be identified by this Manipulator
	 * @param instructions AbstractInsnNode[]
	 * @return Boolean
	 */
	protected abstract boolean identifyInstructions(AbstractInsnNode[] instructions);
	
	/**
	 * Called when the identification was successful. Modifies the given AbstractInsnNode[] array
	 * @param jmpCondNodes AbstractInsnNode[]
	 */
	@Override
	protected final void manipulate(AbstractInsnNode endNode) { 
		MethodManipulator parent = (MethodManipulator) this.getParent();
		int jmpCondStart = parent.getCurrentObject().instructions.indexOf(this.identifiedInstructions[0]);
		int jmpCondEnd = jmpCondStart + this.identifiedInstructions.length - 1;
		this.manipulateMultiInsn(parent.getIdentifiedObject(), jmpCondStart, jmpCondEnd, this.identifiedInstructions);
	}
	
	/**
	 * Called when the identification was successful. Modifies the method or the jump conditional nodes.
	 * @param method Method to be modified
	 * @param jmpCondStart Index of the first instruction of the jump conditional node in the method instruction list
	 * @param jmpCondEnd Index of the last instruction (JumpInsnNode) of the jump conditional node in the method instruction list
	 * @param jmpCondNodes Array of the jump conditions
	 */
	protected void manipulateMultiInsn(MethodNode method, int jmpCondStart, int jmpCondEnd, AbstractInsnNode[] jmpCondNodes) { }
}
