package tcb.bcms.bytecode.instruction.loader.impl;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.tree.AbstractInsnNode;

import tcb.bcms.bytecode.instruction.loader.ILoader;
import tcb.bcms.bytecode.util.Optimizer;
import tcb.bcms.bytecode.util.VariableType;

public class VariableLoader implements ILoader {
	private final int varIndex;
	private final VariableType varType;
	
	/**
	 * Creates a variable loader.
	 * @param varIndex Integer Variable index
	 * @param varType VariableTypes Variable type
	 */
	public VariableLoader(int varIndex, VariableType varType) {
		this.varIndex = varIndex;
		this.varType = varType;
	}
	
	@Override
	public List<AbstractInsnNode> getInstructions() {
		ArrayList<AbstractInsnNode> insnList = new ArrayList<AbstractInsnNode>();
		insnList.add(Optimizer.optimizedLoad(this.varIndex, this.varType));
		return insnList;
	}
}
