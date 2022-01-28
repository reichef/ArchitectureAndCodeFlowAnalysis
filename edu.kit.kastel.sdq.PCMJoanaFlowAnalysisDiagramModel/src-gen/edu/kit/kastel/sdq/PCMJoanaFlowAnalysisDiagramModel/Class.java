/**
 */
package edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Class</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class#getMethod <em>Method</em>}</li>
 *   <li>{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.PCMJoanaFlowAnalysisDiagramModelPackage#getClass_()
 * @model
 * @generated
 */
public interface Class extends EObject {
	/**
	 * Returns the value of the '<em><b>Method</b></em>' containment reference list.
	 * The list contents are of type {@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Method</em>' containment reference list.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.PCMJoanaFlowAnalysisDiagramModelPackage#getClass_Method()
	 * @model containment="true"
	 * @generated
	 */
	EList<Method> getMethod();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.PCMJoanaFlowAnalysisDiagramModelPackage#getClass_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // Class
