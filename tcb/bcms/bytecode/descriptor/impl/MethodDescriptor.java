package tcb.bcms.bytecode.descriptor.impl;

import tcb.bcms.bytecode.descriptor.IDescriptor;

public class MethodDescriptor implements IDescriptor {
	private final String desc;
	private final String typeDesc;
	private final String paramDesc;
	
	/**
	 * Non-void, params
	 * @param methodType
	 * @param params
	 */
	public MethodDescriptor(TypeDescriptor methodType, TypeDescriptor[] params) {
		String typeDesc = "V";
		if(methodType != null) {
			typeDesc = methodType.getDescription();
		}
		this.typeDesc = typeDesc;
		String desc = "(";
		if(params != null) {
			for(TypeDescriptor d : params) {
				desc = desc + d.getDescription();
			}
		}
		desc = desc + ")";
		this.paramDesc = desc;
		desc = desc + typeDesc;
		this.desc = desc;
	}
	
	/**
	 * Non-void, no params
	 * @param methodType TypeDescriptor
	 */
	public MethodDescriptor(TypeDescriptor methodType) {
		this(methodType, (TypeDescriptor[])null);
	}

	/**
	 * Void, params
	 * @param params TypeDescriptor[]
	 */
	public MethodDescriptor(TypeDescriptor[] params) {
		this(null, params);
	}
	
	/**
	 * Void, no params
	 */
	public MethodDescriptor() {
		this(null, (TypeDescriptor[])null);
	}
	
	@Override
	public String getDescription() {
		return this.desc;
	}
	
	public String getTypeDescription() {
		return this.typeDesc;
	}
	
	public String getParamDescription() {
		return this.paramDesc;
	}
}
