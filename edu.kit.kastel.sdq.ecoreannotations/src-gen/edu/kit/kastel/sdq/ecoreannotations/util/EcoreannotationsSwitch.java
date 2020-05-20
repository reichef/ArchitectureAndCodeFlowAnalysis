/**
 */
package edu.kit.kastel.sdq.ecoreannotations.util;

import edu.kit.ipd.sdq.composition.securityanalyses.basic.IdentifiedElement;

import edu.kit.kastel.sdq.ecoreannotations.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see edu.kit.kastel.sdq.ecoreannotations.EcoreannotationsPackage
 * @generated
 */
public class EcoreannotationsSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static EcoreannotationsPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EcoreannotationsSwitch() {
		if (modelPackage == null) {
			modelPackage = EcoreannotationsPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case EcoreannotationsPackage.ANNOTATION_REPOSITORY: {
			AnnotationRepository annotationRepository = (AnnotationRepository) theEObject;
			T result = caseAnnotationRepository(annotationRepository);
			if (result == null)
				result = caseIdentifiedElement(annotationRepository);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case EcoreannotationsPackage.ANNOTATION: {
			Annotation annotation = (Annotation) theEObject;
			T result = caseAnnotation(annotation);
			if (result == null)
				result = caseIdentifiedElement(annotation);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case EcoreannotationsPackage.GENERIC_TARGET: {
			GenericTarget genericTarget = (GenericTarget) theEObject;
			T result = caseGenericTarget(genericTarget);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case EcoreannotationsPackage.GENERIC_TARGET_STRING_CONTENT_ANNOTATION: {
			GenericTargetStringContentAnnotation genericTargetStringContentAnnotation = (GenericTargetStringContentAnnotation) theEObject;
			T result = caseGenericTargetStringContentAnnotation(genericTargetStringContentAnnotation);
			if (result == null)
				result = caseAnnotation(genericTargetStringContentAnnotation);
			if (result == null)
				result = caseGenericTarget(genericTargetStringContentAnnotation);
			if (result == null)
				result = caseStringContent(genericTargetStringContentAnnotation);
			if (result == null)
				result = caseStringAnnotationType(genericTargetStringContentAnnotation);
			if (result == null)
				result = caseIdentifiedElement(genericTargetStringContentAnnotation);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case EcoreannotationsPackage.STRING_CONTENT: {
			StringContent stringContent = (StringContent) theEObject;
			T result = caseStringContent(stringContent);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case EcoreannotationsPackage.STRING_ANNOTATION_TYPE: {
			StringAnnotationType stringAnnotationType = (StringAnnotationType) theEObject;
			T result = caseStringAnnotationType(stringAnnotationType);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Annotation Repository</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Annotation Repository</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnnotationRepository(AnnotationRepository object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Annotation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Annotation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnnotation(Annotation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generic Target</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generic Target</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGenericTarget(GenericTarget object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generic Target String Content Annotation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generic Target String Content Annotation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGenericTargetStringContentAnnotation(GenericTargetStringContentAnnotation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String Content</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String Content</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringContent(StringContent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String Annotation Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String Annotation Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringAnnotationType(StringAnnotationType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Identified Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Identified Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIdentifiedElement(IdentifiedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //EcoreannotationsSwitch
