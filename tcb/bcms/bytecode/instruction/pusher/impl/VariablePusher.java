package tcb.bcms.bytecode.instruction.pusher.impl;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.tree.AbstractInsnNode;

import tcb.bcms.bytecode.instruction.pusher.IPusher;
import tcb.bcms.bytecode.util.Optimizer;
import tcb.bcms.bytecode.util.VariableType;

public class VariablePusher implements IPusher {
	private final int varIndex;
	private final VariableType varType;
	
	/**
	 * Creates a variable pusher. Stores a reference from the stack in a local variable.
	 * @param varIndex Integer Variable index
	 * @param varType VariableTypes Variable type
	 */
	public VariablePusher(int varIndex, VariableType varType) {
		this.varIndex = varIndex;
		this.varType = varType;
	}
	
	@Override
	public List<AbstractInsnNode> getInstructions() {
		ArrayList<AbstractInsnNode> insnList = new ArrayList<AbstractInsnNode>();
		insnList.add(Optimizer.optimizedStore(this.varIndex, this.varType));
		return insnList;
	}
	
}
