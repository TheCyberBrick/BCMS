package tcb.bcms.bytecode.instruction.pusher.impl;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;

import tcb.bcms.bytecode.instruction.pusher.IPusher;

public class StackPusher implements IPusher {
	private final Object obj;

	/**
	 * Pushes a string onto the stack.
	 * @param str String
	 */
	public StackPusher(String str) {
		this.obj = str;
	}
	
	/**
	 * Pushes an integer onto the stack
	 * @param i Integer
	 */
	public StackPusher(int i) {
		this.obj = (Integer) i;
	}
	
	/**
	 * Pushes a long onto the stack.
	 * @param l Long
	 */
	public StackPusher(long l) {
		this.obj = (Long) l;
	}
	
	/**
	 * Pushes a byte onto the stack.
	 * @param b Byte
	 */
	public StackPusher(byte b) {
		this.obj = (Byte) b;
	}
	
	/**
	 * Pushes a float onto the stack.
	 * @param f Float
	 */
	public StackPusher(float f) {
		this.obj = (Float) f;
	}
	
	/**
	 * Pushes a double onto the stack.
	 * @param d Double
	 */
	public StackPusher(double d) {
		this.obj = (Double) d;
	}
	
	/**
	 * Pushes a char onto the stack.
	 * @param c char
	 */
	public StackPusher(char c) {
		this.obj = (Character) c;
	}
	
	/**
	 * Pushes a boolean onto the stack
	 * @param b Boolean
	 */
	public StackPusher(boolean b) {
		this.obj = (Boolean) b;
	}
	
	/**
	 * Pushes a short onto the stack.
	 * @param s Short
	 */
	public StackPusher(short s) {
		this.obj = (Short) s;
	}

	@Override
	public List<AbstractInsnNode> getInstructions() {
		ArrayList<AbstractInsnNode> insnList = new ArrayList<AbstractInsnNode>();
		if(this.obj instanceof Integer) {
			int i = ((Integer)this.obj).intValue();
			switch(i) {
			case 0:
				insnList.add(new InsnNode(Opcodes.ICONST_0));
				break;
			case 1:
				insnList.add(new InsnNode(Opcodes.ICONST_1));
				break;
			case 2:
				insnList.add(new InsnNode(Opcodes.ICONST_2));
				break;
			case 3:
				insnList.add(new InsnNode(Opcodes.ICONST_3));
				break;
			case 4:
				insnList.add(new InsnNode(Opcodes.ICONST_4));
				break;
			case 5:
				insnList.add(new InsnNode(Opcodes.ICONST_5));
				break;
			default:
				if(i <= Byte.MAX_VALUE) {
					insnList.add(new IntInsnNode(Opcodes.BIPUSH, i));
				} else if(i <= Short.MAX_VALUE) {
					insnList.add(new IntInsnNode(Opcodes.SIPUSH, i));
				} else {
					insnList.add(new LdcInsnNode(i));
				}
				break;
			}
		} else if(this.obj instanceof Byte) {
			byte b = ((Byte)this.obj).byteValue();
			insnList.add(new IntInsnNode(Opcodes.BIPUSH, b));
		} else if(this.obj instanceof Short) {
			short s = ((Short)this.obj).shortValue();
			insnList.add(new IntInsnNode(Opcodes.SIPUSH, s));
		} else if(this.obj instanceof Boolean) {
			byte b = (byte) (((Boolean)this.obj).booleanValue() ? 1 : 0);
			insnList.add(new IntInsnNode(Opcodes.BIPUSH, b));
		} else if(this.obj instanceof Float || this.obj instanceof Double || 
				this.obj instanceof Long || this.obj instanceof Character ||
				this.obj instanceof String) {
			insnList.add(new LdcInsnNode(this.obj));
		}
		return insnList;
	}
}
