package tcb.bcms.bytecode.instruction.caller.impl;

import tcb.bcms.bytecode.descriptor.impl.MethodDescriptor;
import tcb.bcms.bytecode.instruction.IInstructor;

public class StaticMethodCaller extends MethodCaller {
	/**
	 * Creates a static method caller.
	 * @param ownerClass String Name of the owner class
	 * @param methodName String Name of the method
	 * @param desc MethodDescriptor Description of the method
	 * @param isInterface Boolean True if the method is in an interface
	 * @param paramLoaders IInstructor[] Parameter loaders
	 */
	public StaticMethodCaller(String ownerClass, String methodName, MethodDescriptor desc, boolean isInterface, IInstructor... paramLoaders) {
		super(null, ownerClass, methodName, desc, isInterface, paramLoaders);
	}
	
	/**
	 * Creates a static method caller.
	 * @param ownerClass String Name of the owner class
	 * @param methodName String Name of the method
	 * @param desc MethodDescriptor Description of the method
	 * @param paramLoaders IInstructor[] Parameter loaders
	 */
	public StaticMethodCaller(String ownerClass, String methodName, MethodDescriptor desc, IInstructor... paramLoaders) {
		super(null, ownerClass, methodName, desc, false, paramLoaders);
	}
}
