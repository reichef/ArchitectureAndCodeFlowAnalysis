/**
 */
package edu.kit.kastel.sdq.ecoreannotations.impl;

import edu.kit.kastel.sdq.ecoreannotations.Annotation;
import edu.kit.kastel.sdq.ecoreannotations.AnnotationRepository;
import edu.kit.kastel.sdq.ecoreannotations.EcoreannotationsFactory;
import edu.kit.kastel.sdq.ecoreannotations.EcoreannotationsPackage;
import edu.kit.kastel.sdq.ecoreannotations.GenericTarget;
import edu.kit.kastel.sdq.ecoreannotations.GenericTargetStringContentAnnotation;
import edu.kit.kastel.sdq.ecoreannotations.StringAnnotationType;
import edu.kit.kastel.sdq.ecoreannotations.StringContent;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import tools.mdsd.modelingfoundations.identifier.IdentifierPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EcoreannotationsPackageImpl extends EPackageImpl implements EcoreannotationsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass annotationRepositoryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass annotationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genericTargetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genericTargetStringContentAnnotationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringContentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringAnnotationTypeEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see edu.kit.kastel.sdq.ecoreannotations.EcoreannotationsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EcoreannotationsPackageImpl() {
		super(eNS_URI, EcoreannotationsFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link EcoreannotationsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EcoreannotationsPackage init() {
		if (isInited)
			return (EcoreannotationsPackage) EPackage.Registry.INSTANCE.getEPackage(EcoreannotationsPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredEcoreannotationsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		EcoreannotationsPackageImpl theEcoreannotationsPackage = registeredEcoreannotationsPackage instanceof EcoreannotationsPackageImpl
				? (EcoreannotationsPackageImpl) registeredEcoreannotationsPackage
				: new EcoreannotationsPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		IdentifierPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theEcoreannotationsPackage.createPackageContents();

		// Initialize created meta-data
		theEcoreannotationsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEcoreannotationsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EcoreannotationsPackage.eNS_URI, theEcoreannotationsPackage);
		return theEcoreannotationsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAnnotationRepository() {
		return annotationRepositoryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAnnotationRepository_Annotations() {
		return (EReference) annotationRepositoryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAnnotation() {
		return annotationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenericTarget() {
		return genericTargetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGenericTarget_Target() {
		return (EReference) genericTargetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenericTargetStringContentAnnotation() {
		return genericTargetStringContentAnnotationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStringContent() {
		return stringContentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStringContent_Content() {
		return (EAttribute) stringContentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStringAnnotationType() {
		return stringAnnotationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStringAnnotationType_AnnotationType() {
		return (EAttribute) stringAnnotationTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EcoreannotationsFactory getEcoreannotationsFactory() {
		return (EcoreannotationsFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		annotationRepositoryEClass = createEClass(ANNOTATION_REPOSITORY);
		createEReference(annotationRepositoryEClass, ANNOTATION_REPOSITORY__ANNOTATIONS);

		annotationEClass = createEClass(ANNOTATION);

		genericTargetEClass = createEClass(GENERIC_TARGET);
		createEReference(genericTargetEClass, GENERIC_TARGET__TARGET);

		genericTargetStringContentAnnotationEClass = createEClass(GENERIC_TARGET_STRING_CONTENT_ANNOTATION);

		stringContentEClass = createEClass(STRING_CONTENT);
		createEAttribute(stringContentEClass, STRING_CONTENT__CONTENT);

		stringAnnotationTypeEClass = createEClass(STRING_ANNOTATION_TYPE);
		createEAttribute(stringAnnotationTypeEClass, STRING_ANNOTATION_TYPE__ANNOTATION_TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		IdentifierPackage theIdentifierPackage = (IdentifierPackage) EPackage.Registry.INSTANCE
				.getEPackage(IdentifierPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		annotationRepositoryEClass.getESuperTypes().add(theIdentifierPackage.getIdentifier());
		annotationEClass.getESuperTypes().add(theIdentifierPackage.getIdentifier());
		genericTargetStringContentAnnotationEClass.getESuperTypes().add(this.getAnnotation());
		genericTargetStringContentAnnotationEClass.getESuperTypes().add(this.getGenericTarget());
		genericTargetStringContentAnnotationEClass.getESuperTypes().add(this.getStringContent());
		genericTargetStringContentAnnotationEClass.getESuperTypes().add(this.getStringAnnotationType());

		// Initialize classes, features, and operations; add parameters
		initEClass(annotationRepositoryEClass, AnnotationRepository.class, "AnnotationRepository", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAnnotationRepository_Annotations(), this.getAnnotation(), null, "Annotations", null, 0, -1,
				AnnotationRepository.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(annotationEClass, Annotation.class, "Annotation", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(genericTargetEClass, GenericTarget.class, "GenericTarget", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGenericTarget_Target(), ecorePackage.getEObject(), null, "Target", null, 1, 1,
				GenericTarget.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(genericTargetStringContentAnnotationEClass, GenericTargetStringContentAnnotation.class,
				"GenericTargetStringContentAnnotation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(stringContentEClass, StringContent.class, "StringContent", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringContent_Content(), ecorePackage.getEString(), "content", null, 1, 1,
				StringContent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(stringAnnotationTypeEClass, StringAnnotationType.class, "StringAnnotationType", IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringAnnotationType_AnnotationType(), ecorePackage.getEString(), "AnnotationType", null, 0,
				1, StringAnnotationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //EcoreannotationsPackageImpl
