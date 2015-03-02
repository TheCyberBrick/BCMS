package tcb.bcms.bytecode.instruction.loader.impl;

import tcb.bcms.bytecode.descriptor.impl.TypeDescriptor;

public class StaticFieldLoader extends FieldLoader {
	/**
	 * Creates a static field loader.
	 * @param ownerClass String Name of the owner class
	 * @param fieldName String Name of the field
	 * @param desc TypeDescriptor Description of the field type
	 */
	public StaticFieldLoader(String ownerClass, String fieldName, TypeDescriptor desc) {
		super(null, ownerClass, fieldName, desc);
	}
}
