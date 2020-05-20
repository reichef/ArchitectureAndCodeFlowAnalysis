/**
 */
package edu.kit.kastel.sdq.ecoreannotations;

import edu.kit.ipd.sdq.composition.securityanalyses.basic.BasicPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see edu.kit.kastel.sdq.ecoreannotations.EcoreannotationsFactory
 * @model kind="package"
 * @generated
 */
public interface EcoreannotationsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "ecoreannotations";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/ecoreannotations";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "ecoreannotations";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EcoreannotationsPackage eINSTANCE = edu.kit.kastel.sdq.ecoreannotations.impl.EcoreannotationsPackageImpl.init();

	/**
	 * The meta object id for the '{@link edu.kit.kastel.sdq.ecoreannotations.impl.AnnotationRepositoryImpl <em>Annotation Repository</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.kastel.sdq.ecoreannotations.impl.AnnotationRepositoryImpl
	 * @see edu.kit.kastel.sdq.ecoreannotations.impl.EcoreannotationsPackageImpl#getAnnotationRepository()
	 * @generated
	 */
	int ANNOTATION_REPOSITORY = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_REPOSITORY__ID = BasicPackage.IDENTIFIED_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_REPOSITORY__ANNOTATIONS = BasicPackage.IDENTIFIED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Annotation Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_REPOSITORY_FEATURE_COUNT = BasicPackage.IDENTIFIED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Annotation Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_REPOSITORY_OPERATION_COUNT = BasicPackage.IDENTIFIED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.kit.kastel.sdq.ecoreannotations.impl.AnnotationImpl <em>Annotation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.kastel.sdq.ecoreannotations.impl.AnnotationImpl
	 * @see edu.kit.kastel.sdq.ecoreannotations.impl.EcoreannotationsPackageImpl#getAnnotation()
	 * @generated
	 */
	int ANNOTATION = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__ID = BasicPackage.IDENTIFIED_ELEMENT__ID;

	/**
	 * The number of structural features of the '<em>Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_FEATURE_COUNT = BasicPackage.IDENTIFIED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_OPERATION_COUNT = BasicPackage.IDENTIFIED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.kit.kastel.sdq.ecoreannotations.impl.GenericTargetImpl <em>Generic Target</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.kastel.sdq.ecoreannotations.impl.GenericTargetImpl
	 * @see edu.kit.kastel.sdq.ecoreannotations.impl.EcoreannotationsPackageImpl#getGenericTarget()
	 * @generated
	 */
	int GENERIC_TARGET = 2;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_TARGET__TARGET = 0;

	/**
	 * The number of structural features of the '<em>Generic Target</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_TARGET_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Generic Target</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_TARGET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.kit.kastel.sdq.ecoreannotations.impl.GenericTargetStringContentAnnotationImpl <em>Generic Target String Content Annotation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.kastel.sdq.ecoreannotations.impl.GenericTargetStringContentAnnotationImpl
	 * @see edu.kit.kastel.sdq.ecoreannotations.impl.EcoreannotationsPackageImpl#getGenericTargetStringContentAnnotation()
	 * @generated
	 */
	int GENERIC_TARGET_STRING_CONTENT_ANNOTATION = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_TARGET_STRING_CONTENT_ANNOTATION__ID = ANNOTATION__ID;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_TARGET_STRING_CONTENT_ANNOTATION__TARGET = ANNOTATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_TARGET_STRING_CONTENT_ANNOTATION__CONTENT = ANNOTATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Annotation Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_TARGET_STRING_CONTENT_ANNOTATION__ANNOTATION_TYPE = ANNOTATION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Generic Target String Content Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_TARGET_STRING_CONTENT_ANNOTATION_FEATURE_COUNT = ANNOTATION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Generic Target String Content Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_TARGET_STRING_CONTENT_ANNOTATION_OPERATION_COUNT = ANNOTATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.kit.kastel.sdq.ecoreannotations.impl.StringContentImpl <em>String Content</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.kastel.sdq.ecoreannotations.impl.StringContentImpl
	 * @see edu.kit.kastel.sdq.ecoreannotations.impl.EcoreannotationsPackageImpl#getStringContent()
	 * @generated
	 */
	int STRING_CONTENT = 4;

	/**
	 * The feature id for the '<em><b>Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_CONTENT__CONTENT = 0;

	/**
	 * The number of structural features of the '<em>String Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_CONTENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>String Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_CONTENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.kit.kastel.sdq.ecoreannotations.impl.StringAnnotationTypeImpl <em>String Annotation Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.kastel.sdq.ecoreannotations.impl.StringAnnotationTypeImpl
	 * @see edu.kit.kastel.sdq.ecoreannotations.impl.EcoreannotationsPackageImpl#getStringAnnotationType()
	 * @generated
	 */
	int STRING_ANNOTATION_TYPE = 5;

	/**
	 * The feature id for the '<em><b>Annotation Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_ANNOTATION_TYPE__ANNOTATION_TYPE = 0;

	/**
	 * The number of structural features of the '<em>String Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_ANNOTATION_TYPE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>String Annotation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_ANNOTATION_TYPE_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link edu.kit.kastel.sdq.ecoreannotations.AnnotationRepository <em>Annotation Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotation Repository</em>'.
	 * @see edu.kit.kastel.sdq.ecoreannotations.AnnotationRepository
	 * @generated
	 */
	EClass getAnnotationRepository();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.kastel.sdq.ecoreannotations.AnnotationRepository#getAnnotations <em>Annotations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Annotations</em>'.
	 * @see edu.kit.kastel.sdq.ecoreannotations.AnnotationRepository#getAnnotations()
	 * @see #getAnnotationRepository()
	 * @generated
	 */
	EReference getAnnotationRepository_Annotations();

	/**
	 * Returns the meta object for class '{@link edu.kit.kastel.sdq.ecoreannotations.Annotation <em>Annotation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotation</em>'.
	 * @see edu.kit.kastel.sdq.ecoreannotations.Annotation
	 * @generated
	 */
	EClass getAnnotation();

	/**
	 * Returns the meta object for class '{@link edu.kit.kastel.sdq.ecoreannotations.GenericTarget <em>Generic Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Generic Target</em>'.
	 * @see edu.kit.kastel.sdq.ecoreannotations.GenericTarget
	 * @generated
	 */
	EClass getGenericTarget();

	/**
	 * Returns the meta object for the reference '{@link edu.kit.kastel.sdq.ecoreannotations.GenericTarget#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see edu.kit.kastel.sdq.ecoreannotations.GenericTarget#getTarget()
	 * @see #getGenericTarget()
	 * @generated
	 */
	EReference getGenericTarget_Target();

	/**
	 * Returns the meta object for class '{@link edu.kit.kastel.sdq.ecoreannotations.GenericTargetStringContentAnnotation <em>Generic Target String Content Annotation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Generic Target String Content Annotation</em>'.
	 * @see edu.kit.kastel.sdq.ecoreannotations.GenericTargetStringContentAnnotation
	 * @generated
	 */
	EClass getGenericTargetStringContentAnnotation();

	/**
	 * Returns the meta object for class '{@link edu.kit.kastel.sdq.ecoreannotations.StringContent <em>String Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Content</em>'.
	 * @see edu.kit.kastel.sdq.ecoreannotations.StringContent
	 * @generated
	 */
	EClass getStringContent();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.kastel.sdq.ecoreannotations.StringContent#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Content</em>'.
	 * @see edu.kit.kastel.sdq.ecoreannotations.StringContent#getContent()
	 * @see #getStringContent()
	 * @generated
	 */
	EAttribute getStringContent_Content();

	/**
	 * Returns the meta object for class '{@link edu.kit.kastel.sdq.ecoreannotations.StringAnnotationType <em>String Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Annotation Type</em>'.
	 * @see edu.kit.kastel.sdq.ecoreannotations.StringAnnotationType
	 * @generated
	 */
	EClass getStringAnnotationType();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.kastel.sdq.ecoreannotations.StringAnnotationType#getAnnotationType <em>Annotation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Annotation Type</em>'.
	 * @see edu.kit.kastel.sdq.ecoreannotations.StringAnnotationType#getAnnotationType()
	 * @see #getStringAnnotationType()
	 * @generated
	 */
	EAttribute getStringAnnotationType_AnnotationType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EcoreannotationsFactory getEcoreannotationsFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link edu.kit.kastel.sdq.ecoreannotations.impl.AnnotationRepositoryImpl <em>Annotation Repository</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.kastel.sdq.ecoreannotations.impl.AnnotationRepositoryImpl
		 * @see edu.kit.kastel.sdq.ecoreannotations.impl.EcoreannotationsPackageImpl#getAnnotationRepository()
		 * @generated
		 */
		EClass ANNOTATION_REPOSITORY = eINSTANCE.getAnnotationRepository();

		/**
		 * The meta object literal for the '<em><b>Annotations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANNOTATION_REPOSITORY__ANNOTATIONS = eINSTANCE.getAnnotationRepository_Annotations();

		/**
		 * The meta object literal for the '{@link edu.kit.kastel.sdq.ecoreannotations.impl.AnnotationImpl <em>Annotation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.kastel.sdq.ecoreannotations.impl.AnnotationImpl
		 * @see edu.kit.kastel.sdq.ecoreannotations.impl.EcoreannotationsPackageImpl#getAnnotation()
		 * @generated
		 */
		EClass ANNOTATION = eINSTANCE.getAnnotation();

		/**
		 * The meta object literal for the '{@link edu.kit.kastel.sdq.ecoreannotations.impl.GenericTargetImpl <em>Generic Target</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.kastel.sdq.ecoreannotations.impl.GenericTargetImpl
		 * @see edu.kit.kastel.sdq.ecoreannotations.impl.EcoreannotationsPackageImpl#getGenericTarget()
		 * @generated
		 */
		EClass GENERIC_TARGET = eINSTANCE.getGenericTarget();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GENERIC_TARGET__TARGET = eINSTANCE.getGenericTarget_Target();

		/**
		 * The meta object literal for the '{@link edu.kit.kastel.sdq.ecoreannotations.impl.GenericTargetStringContentAnnotationImpl <em>Generic Target String Content Annotation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.kastel.sdq.ecoreannotations.impl.GenericTargetStringContentAnnotationImpl
		 * @see edu.kit.kastel.sdq.ecoreannotations.impl.EcoreannotationsPackageImpl#getGenericTargetStringContentAnnotation()
		 * @generated
		 */
		EClass GENERIC_TARGET_STRING_CONTENT_ANNOTATION = eINSTANCE.getGenericTargetStringContentAnnotation();

		/**
		 * The meta object literal for the '{@link edu.kit.kastel.sdq.ecoreannotations.impl.StringContentImpl <em>String Content</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.kastel.sdq.ecoreannotations.impl.StringContentImpl
		 * @see edu.kit.kastel.sdq.ecoreannotations.impl.EcoreannotationsPackageImpl#getStringContent()
		 * @generated
		 */
		EClass STRING_CONTENT = eINSTANCE.getStringContent();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_CONTENT__CONTENT = eINSTANCE.getStringContent_Content();

		/**
		 * The meta object literal for the '{@link edu.kit.kastel.sdq.ecoreannotations.impl.StringAnnotationTypeImpl <em>String Annotation Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.kastel.sdq.ecoreannotations.impl.StringAnnotationTypeImpl
		 * @see edu.kit.kastel.sdq.ecoreannotations.impl.EcoreannotationsPackageImpl#getStringAnnotationType()
		 * @generated
		 */
		EClass STRING_ANNOTATION_TYPE = eINSTANCE.getStringAnnotationType();

		/**
		 * The meta object literal for the '<em><b>Annotation Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_ANNOTATION_TYPE__ANNOTATION_TYPE = eINSTANCE.getStringAnnotationType_AnnotationType();

	}

} //EcoreannotationsPackage
