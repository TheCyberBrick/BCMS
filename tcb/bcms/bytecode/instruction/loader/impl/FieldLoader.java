package tcb.bcms.bytecode.instruction.loader.impl;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;

import tcb.bcms.bytecode.descriptor.impl.TypeDescriptor;
import tcb.bcms.bytecode.instruction.loader.ILoader;

public class FieldLoader implements ILoader {
	private final ILoader owner;
	private final String fieldName;
	private final String ownerClass;
	private final TypeDescriptor desc;
	
	/**
	 * Creates a field loader.
	 * @param owner ILoader Loader of the owner instance
	 * @param ownerClass String Name of the owner class
	 * @param fieldName String Name of the field
	 * @param desc TypeDescriptor Description of the field type
	 */
	public FieldLoader(ILoader owner, String ownerClass, String fieldName, TypeDescriptor desc) {
		this.fieldName = fieldName;
		this.ownerClass = ownerClass.replace(".", "/");
		this.desc = desc;
		this.owner = owner;
	}
	
	@Override
	public List<AbstractInsnNode> getInstructions() {
		ArrayList<AbstractInsnNode> loadInstructions = new ArrayList<AbstractInsnNode>();
		if(this.owner != null) {
			loadInstructions.addAll(this.owner.getInstructions());
			loadInstructions.add(new FieldInsnNode(Opcodes.GETFIELD, this.ownerClass, this.fieldName, this.desc.getDescription()));
		} else {
			loadInstructions.add(new FieldInsnNode(Opcodes.GETSTATIC, this.ownerClass, this.fieldName, this.desc.getDescription()));
		}
		return loadInstructions;
	}
}
