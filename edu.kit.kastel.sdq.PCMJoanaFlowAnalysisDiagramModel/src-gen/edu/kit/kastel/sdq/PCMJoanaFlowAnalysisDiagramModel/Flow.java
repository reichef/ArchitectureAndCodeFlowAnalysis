/**
 */
package edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Flow</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Flow#getMethod <em>Method</em>}</li>
 *   <li>{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Flow#getId <em>Id</em>}</li>
 * </ul>
 *
 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.PCMJoanaFlowAnalysisDiagramModelPackage#getFlow()
 * @model
 * @generated
 */
public interface Flow extends EObject {
	/**
	 * Returns the value of the '<em><b>Method</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Method</em>' reference.
	 * @see #setMethod(Method)
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.PCMJoanaFlowAnalysisDiagramModelPackage#getFlow_Method()
	 * @model required="true"
	 * @generated
	 */
	Method getMethod();

	/**
	 * Sets the value of the '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Flow#getMethod <em>Method</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Method</em>' reference.
	 * @see #getMethod()
	 * @generated
	 */
	void setMethod(Method value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.PCMJoanaFlowAnalysisDiagramModelPackage#getFlow_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Flow#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

} // Flow
