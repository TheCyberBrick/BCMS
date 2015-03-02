package tcb.bcms.bytecode.instruction.caller.impl;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;

import tcb.bcms.bytecode.descriptor.impl.MethodDescriptor;
import tcb.bcms.bytecode.instruction.IInstructor;
import tcb.bcms.bytecode.instruction.loader.ILoader;

public class MethodCaller implements ILoader {
	private final ILoader owner;
	private final String methodName;
	private final String ownerClass;
	private final MethodDescriptor desc;
	private final boolean isInterface;
	private final IInstructor[] paramLoaders;
	
	/**
	 * Creates a method caller.
	 * @param owner ILoader Loader of the owner instance
	 * @param ownerClass String Name of the owner class
	 * @param methodName String Name of the method
	 * @param desc MethodDescriptor Description of the method
	 * @param isInterface Boolean True if the method is in an interface
	 * @param paramLoaders IInstructor[] Parameter loaders
	 */
	public MethodCaller(ILoader owner, String ownerClass, String methodName, MethodDescriptor desc, boolean isInterface, IInstructor... paramLoaders) {
		this.methodName = methodName;
		this.ownerClass = ownerClass.replace(".", "/");
		this.desc = desc;
		this.owner = owner;
		this.isInterface = isInterface;
		this.paramLoaders = paramLoaders;
	}
	
	/**
	 * Creates a method caller.
	 * @param owner ILoader Loader of the owner instance
	 * @param ownerClass String Name of the owner class
	 * @param methodName String Name of the method
	 * @param desc MethodDescriptor Description of the method
	 * @param paramLoaders IInstructor[] Parameter loaders
	 */
	public MethodCaller(ILoader owner, String ownerClass, String methodName, MethodDescriptor desc, IInstructor... paramLoaders) {
		this.methodName = methodName;
		this.ownerClass = ownerClass.replace(".", "/");
		this.desc = desc;
		this.owner = owner;
		this.isInterface = false;
		this.paramLoaders = paramLoaders;
	}
	
	@Override
	public List<AbstractInsnNode> getInstructions() {
		ArrayList<AbstractInsnNode> loadInstructions = new ArrayList<AbstractInsnNode>();
		if(this.owner != null) {
			loadInstructions.addAll(this.owner.getInstructions());
			if(this.paramLoaders != null) {
				for(IInstructor i : this.paramLoaders) {
					loadInstructions.addAll(i.getInstructions());
				}
			}
			loadInstructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, this.ownerClass, this.methodName, this.desc.getDescription(), this.isInterface));
		} else {
			if(this.paramLoaders != null) {
				for(IInstructor i : this.paramLoaders) {
					loadInstructions.addAll(i.getInstructions());
				}
			}
			loadInstructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, this.ownerClass, this.methodName, this.desc.getDescription(), this.isInterface));
		}
		return loadInstructions;
	}
}
