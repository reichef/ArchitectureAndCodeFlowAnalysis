/**
 */
package edu.kit.kastel.sdq.ecoreannotations;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.kastel.sdq.ecoreannotations.EcoreannotationsPackage
 * @generated
 */
public interface EcoreannotationsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EcoreannotationsFactory eINSTANCE = edu.kit.kastel.sdq.ecoreannotations.impl.EcoreannotationsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Annotation Repository</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Annotation Repository</em>'.
	 * @generated
	 */
	AnnotationRepository createAnnotationRepository();

	/**
	 * Returns a new object of class '<em>Generic Target String Content Annotation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Generic Target String Content Annotation</em>'.
	 * @generated
	 */
	GenericTargetStringContentAnnotation createGenericTargetStringContentAnnotation();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	EcoreannotationsPackage getEcoreannotationsPackage();

} //EcoreannotationsFactory
