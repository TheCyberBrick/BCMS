package tcb.bcms.bytecode.instruction.pusher.impl;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.tree.AbstractInsnNode;

import tcb.bcms.bytecode.instruction.pusher.IPusher;
import tcb.bcms.bytecode.util.Optimizer;

public class IndexPusher implements IPusher {
	private final int index;
	
	/**
	 * Creates an index pusher. Pushes an index onto the stack.
	 * @param index Integer Index
	 */
	public IndexPusher(int index) {
		this.index = index;
	}

	@Override
	public List<AbstractInsnNode> getInstructions() {
		ArrayList<AbstractInsnNode> insnList = new ArrayList<AbstractInsnNode>();
		insnList.add(Optimizer.optimizedIndex(index));
		return insnList;
	}
}
