package tcb.bcms.manipulation;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import tcb.bcms.manipulation.impl.ClassManipulator;


public class TransformerFactory {
	private final List<IClassTransformer> classTransformers = new ArrayList<IClassTransformer>();

	private class ClassFileTransformerCMA implements ClassFileTransformer {
		private final boolean recompute;
		private final List<IClassTransformer> classTransformers;

		private ClassFileTransformerCMA(final List<IClassTransformer> classTransformers, boolean recompute) {
			this.classTransformers = classTransformers;
			this.recompute = recompute;
		}

		@Override
		public byte[] transform(ClassLoader classLoader,
				String className, Class<?> paramClass,
				ProtectionDomain protectionDomain,
				byte[] bytecode) throws IllegalClassFormatException {
			boolean hasClassManipulator = false;
			try {
				for(IClassTransformer classTransformer : this.classTransformers) {
					if(classTransformer instanceof ClassManipulator == false) {
						bytecode = classTransformer.transform(classLoader, className, paramClass, protectionDomain, bytecode);
					} else {
						hasClassManipulator = true;
					}
				}
				if(hasClassManipulator) {
					ClassReader classReader = new ClassReader(bytecode);
					ClassNode classNode = new ClassNode();
					classReader.accept(classNode, ClassReader.SKIP_FRAMES);
					boolean manipulated = false;
					for(IClassTransformer classTransformer : this.classTransformers) {
						if(classTransformer instanceof ClassManipulator) {
							if(((ClassManipulator)classTransformer).initManipulation(classNode)) {
								manipulated = true;
							}
						}
					}
					if(!manipulated) {
						return bytecode;
					}
					ClassWriter classWriter = new ClassWriter(this.recompute ? (ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS) : 0);
					classNode.accept(classWriter);
					return classWriter.toByteArray();
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
			return bytecode;
		}
	}

	/**
	 * Returns a list of all IClassTransformers.
	 * @return List<ClassModifier>
	 */
	public List<IClassTransformer> getManipulators() {
		return this.classTransformers;
	}

	/**
	 * Adds a ClassModifier to the TransformerFactory.
	 * @param mod ClassModifier
	 * @return TransformerFactory
	 */
	public TransformerFactory addClassTransformer(IClassTransformer transformer) {
		this.classTransformers.add(transformer);
		return this;
	}

	/**
	 * Adds an array of ClassModifier to the TransformerFactory.
	 * @param mod ClassModifier
	 * @return TransformerFactory
	 */
	public TransformerFactory addClassTransformers(IClassTransformer... transformers) {
		Collections.addAll(this.classTransformers, transformers);
		return this;
	}

	/**
	 * Creates a ClassFileTransformer that transforms the classes using the added IClassTransformers.
	 * Use true for recompute if MAXSTACK and MAXLOCALS should be recomputed. Could cause errors for precompiled files.
	 * @param recompute True if MAXSTACK and MAXLOCALS should be recomputed.
	 * @return ClassFileTransformer
	 */
	public ClassFileTransformer createTransformer(boolean recompute) {
		return new ClassFileTransformerCMA(this.classTransformers, recompute);
	}
	
	/**
	 * Creates a ClassFileTransformer that transforms the classes using the added IClassTransformers 
	 * and adds it to the Instrumentation.
	 * Use true for recompute if MAXSTACK and MAXLOCALS should be recomputed. Could cause errors for precompiled files.
	 * @param inst Instrumentation
	 * @param recompute True if MAXSTACK and MAXLOCALS should be recomputed.
	 */
	public void addToInstrumentation(Instrumentation inst, boolean recompute) {
		inst.addTransformer(this.createTransformer(recompute));
	}
}