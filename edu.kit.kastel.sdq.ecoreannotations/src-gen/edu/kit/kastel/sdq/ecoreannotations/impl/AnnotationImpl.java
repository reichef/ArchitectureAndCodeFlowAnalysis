/**
 */
package edu.kit.kastel.sdq.ecoreannotations.impl;

import edu.kit.kastel.sdq.ecoreannotations.Annotation;
import edu.kit.kastel.sdq.ecoreannotations.EcoreannotationsPackage;

import org.eclipse.emf.ecore.EClass;

import tools.mdsd.modelingfoundations.identifier.impl.IdentifierImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Annotation</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class AnnotationImpl extends IdentifierImpl implements Annotation {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AnnotationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EcoreannotationsPackage.Literals.ANNOTATION;
	}

} //AnnotationImpl
