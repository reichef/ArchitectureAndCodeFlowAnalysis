/**
 */
package edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl;

import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Assembly;
import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.JOANAFlowAnalysisDiagram;
import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.PCMJoanaFlowAnalysisDiagramModelPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>JOANA Flow Analysis Diagram</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.JOANAFlowAnalysisDiagramImpl#getAssembly <em>Assembly</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JOANAFlowAnalysisDiagramImpl extends FlowAnalysisDiagramImpl implements JOANAFlowAnalysisDiagram {
	/**
	 * The cached value of the '{@link #getAssembly() <em>Assembly</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssembly()
	 * @generated
	 * @ordered
	 */
	protected EList<Assembly> assembly;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JOANAFlowAnalysisDiagramImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PCMJoanaFlowAnalysisDiagramModelPackage.Literals.JOANA_FLOW_ANALYSIS_DIAGRAM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Assembly> getAssembly() {
		if (assembly == null) {
			assembly = new EObjectContainmentEList<Assembly>(Assembly.class, this,
					PCMJoanaFlowAnalysisDiagramModelPackage.JOANA_FLOW_ANALYSIS_DIAGRAM__ASSEMBLY);
		}
		return assembly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case PCMJoanaFlowAnalysisDiagramModelPackage.JOANA_FLOW_ANALYSIS_DIAGRAM__ASSEMBLY:
			return ((InternalEList<?>) getAssembly()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case PCMJoanaFlowAnalysisDiagramModelPackage.JOANA_FLOW_ANALYSIS_DIAGRAM__ASSEMBLY:
			return getAssembly();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case PCMJoanaFlowAnalysisDiagramModelPackage.JOANA_FLOW_ANALYSIS_DIAGRAM__ASSEMBLY:
			getAssembly().clear();
			getAssembly().addAll((Collection<? extends Assembly>) newValue);
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
		case PCMJoanaFlowAnalysisDiagramModelPackage.JOANA_FLOW_ANALYSIS_DIAGRAM__ASSEMBLY:
			getAssembly().clear();
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
		case PCMJoanaFlowAnalysisDiagramModelPackage.JOANA_FLOW_ANALYSIS_DIAGRAM__ASSEMBLY:
			return assembly != null && !assembly.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //JOANAFlowAnalysisDiagramImpl
