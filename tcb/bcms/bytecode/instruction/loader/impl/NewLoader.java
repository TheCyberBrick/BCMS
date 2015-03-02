package tcb.bcms.bytecode.instruction.loader.impl;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;

import tcb.bcms.bytecode.instruction.caller.impl.ConstructorCaller;
import tcb.bcms.bytecode.instruction.loader.ILoader;

public class NewLoader implements ILoader {
	private final String className;
	private final ConstructorCaller ctorCaller;
	
	public NewLoader(String className, ConstructorCaller ctorCaller) {
		this.className = className.replace(".", "/");
		this.ctorCaller = ctorCaller;
	}
	
	@Override
	public List<AbstractInsnNode> getInstructions() {
		ArrayList<AbstractInsnNode> insnList = new ArrayList<AbstractInsnNode>();
		insnList.add(new TypeInsnNode(Opcodes.NEW, this.className));
		insnList.add(new InsnNode(Opcodes.DUP));
		if(this.ctorCaller != null) {
			insnList.addAll(this.ctorCaller.getInstructions());
		} else {
			insnList.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, this.className, "<init>", "()V", false));
		}
		return insnList;
	}
}
