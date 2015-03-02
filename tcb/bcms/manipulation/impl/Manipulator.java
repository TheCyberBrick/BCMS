package tcb.bcms.manipulation.impl;

import java.util.ArrayList;
import java.util.List;

public abstract class Manipulator<T> {
	private final boolean requiredIdentification;
	private final String name;
	private final Class<T> type;
	private T identifiedObject = null;
	private T currentObject = null;
	private List<Manipulator<?>> children = new ArrayList<Manipulator<?>>();
	private Manipulator<?> parent = null;
	
	/**
	 * Creates a new Manipulator with the specified name that only accepts the specified type to be identified and manipulated
	 * @param name String
	 * @param type Class
	 */
	public Manipulator(String name, Class<T> type, boolean requiredIdentification) {
		this.name = name;
		this.type = type;
		this.requiredIdentification = requiredIdentification;
	}
	
	/**
	 * Creates a new Manipulator with the specified name that only accepts the specified type to be identified and manipulated
	 * @param name String
	 * @param type Class
	 */
	public Manipulator(String name, Class<T> type) {
		this.name = name;
		this.type = type;
		this.requiredIdentification = true;
	}
	
	/**
	 * Creates a new Manipulator with the specified name that accepts any type to be identified and manipulated
	 * @param name
	 */
	public Manipulator(String name, boolean requiredIdentification) {
		this.name = name;
		this.type = null;
		this.requiredIdentification = requiredIdentification;
	}
	
	/**
	 * Creates a new Manipulator with the specified name that accepts any type to be identified and manipulated
	 * @param name
	 */
	public Manipulator(String name) {
		this.name = name;
		this.type = null;
		this.requiredIdentification = true;
	}
	
	/**
	 * Returns whether the identification of this Manipulator<T> is required for the parent Manipulator<?> to continue.
	 * @return boolean
	 */
	public final boolean isIdentificationRequired() {
		return this.requiredIdentification;
	}
	
	/**
	 * Returns the object that is currently being identified
	 * @return Object
	 */
	public final T getCurrentObject() {
		return this.currentObject;
	}
	
	/**
	 * Returns the identified object
	 * @return Object
	 */
	public final T getIdentifiedObject() {
		return this.identifiedObject;
	}
	
	/**
	 * Returns the parent Manipulator<?> of this Manipulator<T>
	 * @return Manipulator<?>
	 */
	public final Manipulator<?> getParent() {
		return this.parent;
	}
	
	/**
	 * Returns a child Manipulator<?> with the given name
	 * @param name String
	 */
	public final Manipulator<?> getManipulator(String name) {
		for(Manipulator<?> child : this.children){
			if(child.getName().equals(name)) return child;
		}
		return null;
	}
	
	/**
	 * Adds a child Manipulator<?>. If child fails to identify, this Manipulator<T> will fail the identification and manipulation.
	 * Returns the instance of this Manipulator.
	 * @param child Manipulator<?>
	 * @return Manipulator<T>
	 */
	public final Manipulator<T> addManipulator(Manipulator<?> child) {
		child.parent = this;
		this.children.add(child);
		return this;
	}
	
	/**
	 * Adds an array of child Manipulator<?>s. If children fail to identify, this Manipulator<T> will fail the identification and manipulation
	 * Returns the instance of this Manipulator.
	 * @param children Manipulator<?>[]
	 * @return Manipulator<T>
	 */
	public final Manipulator<T> addManipulators(Manipulator<?>... children) {
		for(Manipulator<?> manipulator : children) {
			manipulator.parent = this;
			this.children.add(manipulator);
		}
		return this;
	}
	
	/**
	 * Removes a child Manipulator<?> from the children of this Manipulator<?>
	 * @param manipulator Manipulator<?>
	 */
	public final void removeManipulator(Manipulator<?> manipulator) {
		manipulator.parent = null;
		this.children.remove(manipulator);
	}
	
	/**
	 * Removes an array of child Manipulator<?>s from the children of this Manipulator<?>
	 * @param manipulators Manipulator<?>[]
	 */
	public final void removeManipulators(Manipulator<?>... manipulators) {
		for(Manipulator<?> manipulator : manipulators) {
			manipulator.parent = null;
			this.children.remove(manipulator);
		}
	}
	
	/**
	 * Returns the list of child Manipulator<?>
	 * @return Identifier<?>
	 */
	public final List<Manipulator<?>> getChildManipulators() {
		return this.children;
	}
	
	/**
	 * Returns whether the given object can be identified by this Manipulator and if true, the given object will be manipulated
	 * @param obj Object
	 * @return Boolean
	 */
	@SuppressWarnings("unchecked")
	public final boolean initManipulation(Object obj) {
		if(this.type == null || (obj != null && this.type.isAssignableFrom(obj.getClass()))) {
			this.currentObject = (T) obj;
			if(this.isIdentificationRequired()) {
				if(this.identify((T) obj)) {
					if(this.identifyChildren((T) obj)) {
						this.identifiedObject = (T) obj;
						this.identificationSuccess();
						this.manipulate((T) obj);
						/*if(obj instanceof MethodNode) {
							((MethodNode)obj).visitMaxs(0, 0);
						}*/
						this.currentObject = null;
						return true;
					}
				} else {
					this.identificationFail("Failed identification (Args: Object)", obj);
				}
			} else {
				return true;
			}
		}
		this.currentObject = null;
		return false;
	}
	
	/**
	 * Tries to identify the Object by all children, if failed, Manipulator will fail the identification and manipulation
	 * @param obj Object
	 * @return Boolean
	 */
	private final boolean identifyChildren(T obj) {
		boolean identified = true;
		for(Manipulator<?> child : this.children) {
			identified = this.identifyChild(child, obj);
			if(!identified) {
				this.identificationFail("Failed identification for child manipulator (Args: Manipulator<?>, Object)", child, obj);
				break;
			}
		}
		return identified; 
	}
	
	/**
	 * Tries to identify the object with the child Identifier
	 * @param child Manipulator<?>
	 * @param obj Object
	 * @return Boolean
	 */
	protected boolean identifyChild(Manipulator<?> child, T obj) {
		this.identificationFail("No child manipulators supported (Args: Manipulator<?>, Object)", child, obj);
		return false;
	}
	
	/**
	 * Returns whether the given object can be identified by this Manipulator
	 * @param obj Object
	 * @return Boolean
	 */
	protected abstract boolean identify(T obj);
	
	/**
	 * Returns the name of this Manipulator
	 * @return String
	 */
	public final String getName() {
		return this.name;
	}
	
	/**
	 * Returns the accepted types to identify
	 * @return Class
	 */
	public final Class<T> getType() {
		return this.type;
	}
	
	/**
	 * Called when the identification fails
	 * @param reason String
	 */
	protected void identificationFail(String reason, Object... args) { }
	
	/**
	 * Called when the identification was successful
	 */
	protected void identificationSuccess() { }
	
	/**
	 * Called when the identification was successful. Modifies the given object
	 * @param obj Object
	 */
	protected void manipulate(T obj) { }
}