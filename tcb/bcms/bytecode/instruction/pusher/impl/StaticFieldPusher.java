package tcb.bcms.bytecode.instruction.pusher.impl;

import tcb.bcms.bytecode.descriptor.impl.TypeDescriptor;

public class StaticFieldPusher extends FieldPusher {
	/**
	 * Creates a static field pusher. Stores a reference from the stack in a static field.
	 * @param ownerClass String Name of the owner class
	 * @param fieldName String Name of the field
	 * @param desc TypeDescriptor Description of the field type
	 */
	public StaticFieldPusher(String ownerClass, String fieldName, TypeDescriptor desc) {
		super(null, ownerClass, fieldName, desc);
	}
}
