package tcb.bcms.bytecode.instruction.caller.impl;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;

import tcb.bcms.bytecode.descriptor.impl.MethodDescriptor;
import tcb.bcms.bytecode.instruction.IInstructor;
import tcb.bcms.bytecode.instruction.caller.ICaller;

public class ConstructorCaller implements ICaller {
	private final String ownerClass;
	private final MethodDescriptor desc;
	private final IInstructor[] paramLoaders;
	
	/**
	 * Creates a constructor caller.
	 * @param ownerClass String Name of the owner class
	 * @param desc MethodDescriptor Description of the constructor parameters (return type is ignored)
	 */
	public ConstructorCaller(String ownerClass, MethodDescriptor desc, IInstructor... paramLoaders) {
		this.ownerClass = ownerClass.replace(".", "/");
		this.desc = desc;
		this.paramLoaders = paramLoaders;
	}
	
	@Override
	public List<AbstractInsnNode> getInstructions() {
		ArrayList<AbstractInsnNode> insnList = new ArrayList<AbstractInsnNode>();
		if(this.paramLoaders != null) {
			for(IInstructor i : this.paramLoaders) {
				insnList.addAll(i.getInstructions());
			}
		}
		insnList.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, this.ownerClass, "<init>", this.desc.getParamDescription() + "V", false));
		return insnList;
	}
}
