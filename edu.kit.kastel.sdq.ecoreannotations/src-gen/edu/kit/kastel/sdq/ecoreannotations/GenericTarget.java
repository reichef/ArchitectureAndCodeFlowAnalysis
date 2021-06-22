/**
 */
package edu.kit.kastel.sdq.ecoreannotations;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Generic Target</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.kastel.sdq.ecoreannotations.GenericTarget#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see edu.kit.kastel.sdq.ecoreannotations.EcoreannotationsPackage#getGenericTarget()
 * @model abstract="true"
 * @generated
 */
public interface GenericTarget extends EObject {
	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(EObject)
	 * @see edu.kit.kastel.sdq.ecoreannotations.EcoreannotationsPackage#getGenericTarget_Target()
	 * @model required="true"
	 * @generated
	 */
	EObject getTarget();

	/**
	 * Sets the value of the '{@link edu.kit.kastel.sdq.ecoreannotations.GenericTarget#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(EObject value);

} // GenericTarget
