package tcb.bcms.manipulation.impl;

import java.security.ProtectionDomain;
import java.util.List;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InnerClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

import tcb.bcms.manipulation.impl.classes.FieldManipulator;
import tcb.bcms.manipulation.impl.classes.MethodManipulator;
import tcb.bcms.manipulation.IClassTransformer;


public abstract class ClassManipulator extends Manipulator<ClassNode> implements IClassTransformer {
	private final boolean recompute;
	
	/**
	 * Creates a new Manipulator with the specified name that only accepts an object of the type MethodNode to be identified and manipulated
	 * Supported child Manipulator<?>: FieldManipulator, MethodManipulator, Manipulator<?>
	 * @param name String
	 */
	public ClassManipulator(String name) {
		this(name, false);
	}
	
	/**
	 * Creates a new Manipulator with the specified name that only accepts an object of the type MethodNode to be identified and manipulated
	 * Supported child Manipulator<?>: FieldManipulator, MethodManipulator, Manipulator<?>
	 * Use true for recompute if MAXSTACK and MAXLOCALS should be recomputed. Could cause errors for precompiled files.
	 * @param name String
	 * @param recompute True if MAXSTACK and MAXLOCALS should be recomputed.
	 */
	public ClassManipulator(String name, boolean recompute) {
		super(name, ClassNode.class);
		this.recompute = recompute;
	}
	
	/**
	 * Transforms the bytecode.
	 */
	@Override
	public final byte[] transform(ClassLoader classLoader, String className, Class<?> paramClass, ProtectionDomain protectionDomain, byte[] bytecode) {
		ClassReader classReader = new ClassReader(bytecode);
		ClassNode classNode = new ClassNode();
		classReader.accept(classNode, ClassReader.SKIP_FRAMES);
		
		if(this.initManipulation(classNode)) {
			ClassWriter classWriter = new ClassWriter(this.recompute ? ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS : 0);
			classNode.accept(classWriter);
			
			return classWriter.toByteArray();
		}
		
		return bytecode;
	}
	
	/**
	 * Returns the name of the identified class
	 * @return String
	 */
	public String getIdentifiedClassName() {
		return this.getIdentifiedObject().name;
	}
	
	/**
	 * Tries to identify the object with the child Manipulator and modifies it
	 * @param child Manipulator<?>
	 * @param obj Object
	 * @return Boolean
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected boolean identifyChild(Manipulator<?> child, ClassNode obj) {
		ClassNode classNode = (ClassNode) obj;
		
		//FieldManipulator and MethodManipulator are separated for optimization
		if(child instanceof FieldManipulator) {
			for(FieldNode fieldNode : (List<FieldNode>) classNode.fields) {
				if(child.initManipulation(fieldNode)) {
					return true;
				}
			}
		} else if(child instanceof MethodManipulator) {
			for(MethodNode methodNode : (List<MethodNode>) classNode.methods) {
				if(child.initManipulation(methodNode)) {
					return true;
				}
			}
		} else {
			//Inner classes
			for(InnerClassNode node : (List<InnerClassNode>) classNode.innerClasses) {
				if(child.initManipulation(node)) {
					return true;
				}
			}

			//Visible annotations
			if(classNode.visibleAnnotations != null) {
				for(AnnotationNode node : (List<AnnotationNode>) classNode.visibleAnnotations) {
					if(child.initManipulation(node)) {
						return true;
					}
				}
			}

			//Invisible annotations
			if(classNode.invisibleAnnotations != null) {
				for(AnnotationNode node : (List<AnnotationNode>) classNode.invisibleAnnotations) {
					if(child.initManipulation(node)) {
						return true;
					}
				}
			}

			//Visible type annotations
			if(classNode.visibleTypeAnnotations != null) {
				for(TypeAnnotationNode node : (List<TypeAnnotationNode>) classNode.visibleTypeAnnotations) {
					if(child.initManipulation(node)) {
						return true;
					}
				}
			}

			//Invisible type annotations
			if(classNode.invisibleTypeAnnotations != null) {
				for(TypeAnnotationNode node : (List<TypeAnnotationNode>) classNode.invisibleTypeAnnotations) {
					if(child.initManipulation(node)) {
						return true;
					}
				}
			}

			//Attributes
			if(classNode.attrs != null) {
				for(Attribute attr : (List<Attribute>) classNode.attrs) {
					if(child.initManipulation(attr)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
}
