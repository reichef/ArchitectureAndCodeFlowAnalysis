/**
 */
package edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>JOANA Flow Analysis Diagram</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.JOANAFlowAnalysisDiagram#getAssembly <em>Assembly</em>}</li>
 * </ul>
 *
 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.PCMJoanaFlowAnalysisDiagramModelPackage#getJOANAFlowAnalysisDiagram()
 * @model
 * @generated
 */
public interface JOANAFlowAnalysisDiagram extends FlowAnalysisDiagram {
	/**
	 * Returns the value of the '<em><b>Assembly</b></em>' containment reference list.
	 * The list contents are of type {@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Assembly}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assembly</em>' containment reference list.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.PCMJoanaFlowAnalysisDiagramModelPackage#getJOANAFlowAnalysisDiagram_Assembly()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Assembly> getAssembly();

} // JOANAFlowAnalysisDiagram
