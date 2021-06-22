/**
 */
package edu.kit.kastel.sdq.ecoreannotations.impl;

import edu.kit.kastel.sdq.ecoreannotations.EcoreannotationsPackage;
import edu.kit.kastel.sdq.ecoreannotations.GenericTarget;
import edu.kit.kastel.sdq.ecoreannotations.GenericTargetStringContentAnnotation;
import edu.kit.kastel.sdq.ecoreannotations.StringAnnotationType;
import edu.kit.kastel.sdq.ecoreannotations.StringContent;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Generic Target String Content Annotation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.kastel.sdq.ecoreannotations.impl.GenericTargetStringContentAnnotationImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link edu.kit.kastel.sdq.ecoreannotations.impl.GenericTargetStringContentAnnotationImpl#getContent <em>Content</em>}</li>
 *   <li>{@link edu.kit.kastel.sdq.ecoreannotations.impl.GenericTargetStringContentAnnotationImpl#getAnnotationType <em>Annotation Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenericTargetStringContentAnnotationImpl extends AnnotationImpl
		implements GenericTargetStringContentAnnotation {
	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected EObject target;

	/**
	 * The default value of the '{@link #getContent() <em>Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContent()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContent() <em>Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContent()
	 * @generated
	 * @ordered
	 */
	protected String content = CONTENT_EDEFAULT;

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
	protected GenericTargetStringContentAnnotationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EcoreannotationsPackage.Literals.GENERIC_TARGET_STRING_CONTENT_ANNOTATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject) target;
			target = eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							EcoreannotationsPackage.GENERIC_TARGET_STRING_CONTENT_ANNOTATION__TARGET, oldTarget,
							target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(EObject newTarget) {
		EObject oldTarget = target;
		target = newTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EcoreannotationsPackage.GENERIC_TARGET_STRING_CONTENT_ANNOTATION__TARGET, oldTarget, target));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getContent() {
		return content;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContent(String newContent) {
		String oldContent = content;
		content = newContent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EcoreannotationsPackage.GENERIC_TARGET_STRING_CONTENT_ANNOTATION__CONTENT, oldContent, content));
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
					EcoreannotationsPackage.GENERIC_TARGET_STRING_CONTENT_ANNOTATION__ANNOTATION_TYPE,
					oldAnnotationType, annotationType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case EcoreannotationsPackage.GENERIC_TARGET_STRING_CONTENT_ANNOTATION__TARGET:
			if (resolve)
				return getTarget();
			return basicGetTarget();
		case EcoreannotationsPackage.GENERIC_TARGET_STRING_CONTENT_ANNOTATION__CONTENT:
			return getContent();
		case EcoreannotationsPackage.GENERIC_TARGET_STRING_CONTENT_ANNOTATION__ANNOTATION_TYPE:
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
		case EcoreannotationsPackage.GENERIC_TARGET_STRING_CONTENT_ANNOTATION__TARGET:
			setTarget((EObject) newValue);
			return;
		case EcoreannotationsPackage.GENERIC_TARGET_STRING_CONTENT_ANNOTATION__CONTENT:
			setContent((String) newValue);
			return;
		case EcoreannotationsPackage.GENERIC_TARGET_STRING_CONTENT_ANNOTATION__ANNOTATION_TYPE:
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
		case EcoreannotationsPackage.GENERIC_TARGET_STRING_CONTENT_ANNOTATION__TARGET:
			setTarget((EObject) null);
			return;
		case EcoreannotationsPackage.GENERIC_TARGET_STRING_CONTENT_ANNOTATION__CONTENT:
			setContent(CONTENT_EDEFAULT);
			return;
		case EcoreannotationsPackage.GENERIC_TARGET_STRING_CONTENT_ANNOTATION__ANNOTATION_TYPE:
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
		case EcoreannotationsPackage.GENERIC_TARGET_STRING_CONTENT_ANNOTATION__TARGET:
			return target != null;
		case EcoreannotationsPackage.GENERIC_TARGET_STRING_CONTENT_ANNOTATION__CONTENT:
			return CONTENT_EDEFAULT == null ? content != null : !CONTENT_EDEFAULT.equals(content);
		case EcoreannotationsPackage.GENERIC_TARGET_STRING_CONTENT_ANNOTATION__ANNOTATION_TYPE:
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
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == GenericTarget.class) {
			switch (derivedFeatureID) {
			case EcoreannotationsPackage.GENERIC_TARGET_STRING_CONTENT_ANNOTATION__TARGET:
				return EcoreannotationsPackage.GENERIC_TARGET__TARGET;
			default:
				return -1;
			}
		}
		if (baseClass == StringContent.class) {
			switch (derivedFeatureID) {
			case EcoreannotationsPackage.GENERIC_TARGET_STRING_CONTENT_ANNOTATION__CONTENT:
				return EcoreannotationsPackage.STRING_CONTENT__CONTENT;
			default:
				return -1;
			}
		}
		if (baseClass == StringAnnotationType.class) {
			switch (derivedFeatureID) {
			case EcoreannotationsPackage.GENERIC_TARGET_STRING_CONTENT_ANNOTATION__ANNOTATION_TYPE:
				return EcoreannotationsPackage.STRING_ANNOTATION_TYPE__ANNOTATION_TYPE;
			default:
				return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == GenericTarget.class) {
			switch (baseFeatureID) {
			case EcoreannotationsPackage.GENERIC_TARGET__TARGET:
				return EcoreannotationsPackage.GENERIC_TARGET_STRING_CONTENT_ANNOTATION__TARGET;
			default:
				return -1;
			}
		}
		if (baseClass == StringContent.class) {
			switch (baseFeatureID) {
			case EcoreannotationsPackage.STRING_CONTENT__CONTENT:
				return EcoreannotationsPackage.GENERIC_TARGET_STRING_CONTENT_ANNOTATION__CONTENT;
			default:
				return -1;
			}
		}
		if (baseClass == StringAnnotationType.class) {
			switch (baseFeatureID) {
			case EcoreannotationsPackage.STRING_ANNOTATION_TYPE__ANNOTATION_TYPE:
				return EcoreannotationsPackage.GENERIC_TARGET_STRING_CONTENT_ANNOTATION__ANNOTATION_TYPE;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (content: ");
		result.append(content);
		result.append(", AnnotationType: ");
		result.append(annotationType);
		result.append(')');
		return result.toString();
	}

} //GenericTargetStringContentAnnotationImpl
