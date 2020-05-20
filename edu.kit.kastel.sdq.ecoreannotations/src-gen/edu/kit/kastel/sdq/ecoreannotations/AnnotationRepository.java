/**
 */
package edu.kit.kastel.sdq.ecoreannotations;

import edu.kit.ipd.sdq.composition.securityanalyses.basic.IdentifiedElement;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Annotation Repository</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.kastel.sdq.ecoreannotations.AnnotationRepository#getAnnotations <em>Annotations</em>}</li>
 * </ul>
 *
 * @see edu.kit.kastel.sdq.ecoreannotations.EcoreannotationsPackage#getAnnotationRepository()
 * @model
 * @generated
 */
public interface AnnotationRepository extends IdentifiedElement {
	/**
	 * Returns the value of the '<em><b>Annotations</b></em>' containment reference list.
	 * The list contents are of type {@link edu.kit.kastel.sdq.ecoreannotations.Annotation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Annotations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Annotations</em>' containment reference list.
	 * @see edu.kit.kastel.sdq.ecoreannotations.EcoreannotationsPackage#getAnnotationRepository_Annotations()
	 * @model containment="true"
	 * @generated
	 */
	EList<Annotation> getAnnotations();

} // AnnotationRepository
