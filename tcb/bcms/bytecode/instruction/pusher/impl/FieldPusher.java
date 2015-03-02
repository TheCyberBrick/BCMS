package tcb.bcms.bytecode.instruction.pusher.impl;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;

import tcb.bcms.bytecode.descriptor.impl.TypeDescriptor;
import tcb.bcms.bytecode.instruction.loader.ILoader;
import tcb.bcms.bytecode.instruction.pusher.IPusher;

public class FieldPusher implements IPusher {
	private final ILoader owner;
	private final String fieldName;
	private final String ownerClass;
	private final TypeDescriptor desc;
	
	/**
	 * Creates a field pusher. Stores a reference from the stack in a field.
	 * @param owner ILoader Loader of the owner instance
	 * @param ownerClass String Name of the owner class
	 * @param fieldName String Name of the field
	 * @param desc TypeDescriptor Description of the field type
	 */
	public FieldPusher(ILoader owner, String ownerClass, String fieldName, TypeDescriptor desc) {
		this.fieldName = fieldName;
		this.ownerClass = ownerClass.replace(".", "/");
		this.desc = desc;
		this.owner = owner;
	}
	
	@Override
	public List<AbstractInsnNode> getInstructions() {
		ArrayList<AbstractInsnNode> insnList = new ArrayList<AbstractInsnNode>();
		if(this.owner != null) {
			insnList.addAll(this.owner.getInstructions());
			insnList.add(new FieldInsnNode(Opcodes.PUTFIELD, this.ownerClass, this.fieldName, this.desc.getDescription()));
		} else {
			insnList.add(new FieldInsnNode(Opcodes.PUTSTATIC, this.ownerClass, this.fieldName, this.desc.getDescription()));
		}
		return insnList;
	}
}
