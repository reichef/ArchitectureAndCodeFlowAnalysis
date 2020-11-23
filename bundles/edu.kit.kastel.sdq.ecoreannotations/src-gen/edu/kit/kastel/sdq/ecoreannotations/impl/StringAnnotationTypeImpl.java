/**
 */
package edu.kit.kastel.sdq.ecoreannotations.impl;

import edu.kit.kastel.sdq.ecoreannotations.EcoreannotationsPackage;
import edu.kit.kastel.sdq.ecoreannotations.StringAnnotationType;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>String Annotation Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.kastel.sdq.ecoreannotations.impl.StringAnnotationTypeImpl#getAnnotationType <em>Annotation Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class StringAnnotationTypeImpl extends MinimalEObjectImpl.Container implements StringAnnotationType {
	/**
	 * The default value of the '{@link #getAnnotationType() <em>Annotation Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnotationType()
	 * @generated
	 * @ordered
	 */
	protected static final String ANNOTATION_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAnnotationType() <em>Annotation Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnotationType()
	 * @generated
	 * @ordered
	 */
	protected String annotationType = ANNOTATION_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StringAnnotationTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EcoreannotationsPackage.Literals.STRING_ANNOTATION_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAnnotationType() {
		return annotationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAnnotationType(String newAnnotationType) {
		String oldAnnotationType = annotationType;
		annotationType = newAnnotationType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EcoreannotationsPackage.STRING_ANNOTATION_TYPE__ANNOTATION_TYPE, oldAnnotationType,
					annotationType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case EcoreannotationsPackage.STRING_ANNOTATION_TYPE__ANNOTATION_TYPE:
			return getAnnotationType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case EcoreannotationsPackage.STRING_ANNOTATION_TYPE__ANNOTATION_TYPE:
			setAnnotationType((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case EcoreannotationsPackage.STRING_ANNOTATION_TYPE__ANNOTATION_TYPE:
			setAnnotationType(ANNOTATION_TYPE_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case EcoreannotationsPackage.STRING_ANNOTATION_TYPE__ANNOTATION_TYPE:
			return ANNOTATION_TYPE_EDEFAULT == null ? annotationType != null
					: !ANNOTATION_TYPE_EDEFAULT.equals(annotationType);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (AnnotationType: ");
		result.append(annotationType);
		result.append(')');
		return result.toString();
	}

} //StringAnnotationTypeImpl
