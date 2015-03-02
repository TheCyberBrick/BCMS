package tcb.bcms.bytecode.util;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;

public class Optimizer {
	
	/**
	 * Pushes an index onto the stack. Most efficient Opcode is used.
	 * @param index Integer
	 * @return AbstratInsnNode
	 */
	public static AbstractInsnNode optimizedIndex(int index) {
		if(index <= 5) {
			switch(index) {
			case 0:
				return new InsnNode(Opcodes.ICONST_0);
			case 1:
				return new InsnNode(Opcodes.ICONST_1);
			case 2:
				return new InsnNode(Opcodes.ICONST_2);
			case 3:
				return new InsnNode(Opcodes.ICONST_3);
			case 4:
				return new InsnNode(Opcodes.ICONST_4);
			case 5:
				return new InsnNode(Opcodes.ICONST_5);
			}
		} else if(index <= Byte.MAX_VALUE) {
			return new IntInsnNode(Opcodes.BIPUSH, index);
		} else if(index <= Short.MAX_VALUE) {
			return new IntInsnNode(Opcodes.SIPUSH, index);
		} else {
			return new LdcInsnNode(index);
		}
		return new LdcInsnNode(index);
	}

	/**
	 * Loads a local variable and pushes it onto the stack.
	 * @param index Integer
	 * @param type VariableType
	 * @return AbstractInsnNode
	 */
	public static AbstractInsnNode optimizedLoad(int index, VariableType type) {
		switch(type) {
		case BOOLEAN:
		case INT:
			return new IntInsnNode(Opcodes.ILOAD, index);
		case LONG:
			return new IntInsnNode(Opcodes.LLOAD, index);
		case DOUBLE:
			return new IntInsnNode(Opcodes.DLOAD, index);
		case FLOAT:
			return new IntInsnNode(Opcodes.FLOAD, index);
		default:
			return new IntInsnNode(Opcodes.ALOAD, index);
		}
	}

	/**
	 * Stores the top value of the stack in a local variable.
	 * @param index Integer
	 * @param type VariableType
	 * @return AbstractInsnNode
	 */
	public static AbstractInsnNode optimizedStore(int index, VariableType type) {
		switch(type) {
		case BOOLEAN:
		case INT:
			return new IntInsnNode(Opcodes.ISTORE, index);
		case LONG:
			return new IntInsnNode(Opcodes.LSTORE, index);
		case DOUBLE:
			return new IntInsnNode(Opcodes.DSTORE, index);
		case FLOAT:
			return new IntInsnNode(Opcodes.FSTORE, index);
		default:
			return new IntInsnNode(Opcodes.ASTORE, index);
		}
	}
}
