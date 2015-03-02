package tcb.bcms.bytecode.descriptor.impl;

import org.objectweb.asm.Type;

import tcb.bcms.bytecode.descriptor.IDescriptor;

public class TypeDescriptor implements IDescriptor {
	private final String desc;
	
	/**
	 * Creates a type descriptor. Don't use this constructor for primitive data types.
	 * @param className String Class name
	 * @param array Boolean True if array
	 * @throws ClassNotFoundException
	 */
	public TypeDescriptor(String className, boolean array) {
		this.desc = (array ? "[" : "") + "L" + className.replace(".", "/") + ";";
	}
	
	/**
	 * Creates a type descriptor. Don't use this constructor for primitive data types.
	 * @param className String Class name
	 * @throws ClassNotFoundException
	 */
	public TypeDescriptor(String className) {
		this(className, false);
	}
	
	/**
	 * Creates a type descriptor.
	 * @param clazz Class<?> Type class
	 * @param array Boolean True if array
	 */
	public TypeDescriptor(Class<?> clazz, boolean array) {
		this.desc = (array ? "[" : "") + Type.getDescriptor(clazz);
	}
	
	/**
	 * Creates a type descriptor.
	 * @param clazz Class<?> Type class
	 */
	public TypeDescriptor(Class<?> clazz) {
		this.desc = Type.getDescriptor(clazz);
	}
	
	@Override
	public String getDescription() {
		return this.desc;
	}
}