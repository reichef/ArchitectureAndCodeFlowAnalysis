/**
 */
package edu.kit.kastel.sdq.ecoreannotations.impl;

import edu.kit.kastel.sdq.ecoreannotations.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EcoreannotationsFactoryImpl extends EFactoryImpl implements EcoreannotationsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EcoreannotationsFactory init() {
		try {
			EcoreannotationsFactory theEcoreannotationsFactory = (EcoreannotationsFactory) EPackage.Registry.INSTANCE
					.getEFactory(EcoreannotationsPackage.eNS_URI);
			if (theEcoreannotationsFactory != null) {
				return theEcoreannotationsFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EcoreannotationsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EcoreannotationsFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case EcoreannotationsPackage.ANNOTATION_REPOSITORY:
			return createAnnotationRepository();
		case EcoreannotationsPackage.GENERIC_TARGET_STRING_CONTENT_ANNOTATION:
			return createGenericTargetStringContentAnnotation();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnnotationRepository createAnnotationRepository() {
		AnnotationRepositoryImpl annotationRepository = new AnnotationRepositoryImpl();
		return annotationRepository;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenericTargetStringContentAnnotation createGenericTargetStringContentAnnotation() {
		GenericTargetStringContentAnnotationImpl genericTargetStringContentAnnotation = new GenericTargetStringContentAnnotationImpl();
		return genericTargetStringContentAnnotation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EcoreannotationsPackage getEcoreannotationsPackage() {
		return (EcoreannotationsPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EcoreannotationsPackage getPackage() {
		return EcoreannotationsPackage.eINSTANCE;
	}

} //EcoreannotationsFactoryImpl
