/**
 */
package edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Assembly</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Assembly#getClass_ <em>Class</em>}</li>
 *   <li>{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Assembly#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.PCMJoanaFlowAnalysisDiagramModelPackage#getAssembly()
 * @model
 * @generated
 */
public interface Assembly extends EObject {
	/**
	 * Returns the value of the '<em><b>Class</b></em>' containment reference list.
	 * The list contents are of type {@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class</em>' containment reference list.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.PCMJoanaFlowAnalysisDiagramModelPackage#getAssembly_Class()
	 * @model containment="true"
	 * @generated
	 */
	EList<edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class> getClass_();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.PCMJoanaFlowAnalysisDiagramModelPackage#getAssembly_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Assembly#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // Assembly
