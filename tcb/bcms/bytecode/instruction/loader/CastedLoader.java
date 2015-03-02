package tcb.bcms.bytecode.instruction.loader;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;

import tcb.bcms.bytecode.descriptor.impl.TypeDescriptor;

public class CastedLoader implements ILoader {
	private final ILoader referenceLoader;
	private final TypeDescriptor castType;
	
	/**
	 * Casts the reference loaded by referenceLoader to a class.
	 * @param referenceLoader ILoader Loader of the reference
	 * @param castType TypeDescriptor Class type to cast to
	 */
	public CastedLoader(ILoader referenceLoader, TypeDescriptor castType) {
		this.referenceLoader = referenceLoader;
		this.castType = castType;
	}
	
	@Override
	public List<AbstractInsnNode> getInstructions() {
		ArrayList<AbstractInsnNode> insnList = new ArrayList<AbstractInsnNode>();
		insnList.addAll(this.referenceLoader.getInstructions());
		insnList.add(new TypeInsnNode(Opcodes.CHECKCAST, this.castType.getDescription()));
		return insnList;
	}
}
