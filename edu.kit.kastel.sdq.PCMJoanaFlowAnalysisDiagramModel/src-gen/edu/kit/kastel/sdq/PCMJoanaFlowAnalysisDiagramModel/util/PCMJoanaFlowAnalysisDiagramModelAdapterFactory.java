/**
 */
package edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.util;

import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Assembly;
import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Flow;
import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.FlowAnalysisDiagram;
import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.JOANAFlowAnalysisDiagram;
import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method;
import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.PCMJoanaFlowAnalysisDiagramModelPackage;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.PCMJoanaFlowAnalysisDiagramModelPackage
 * @generated
 */
public class PCMJoanaFlowAnalysisDiagramModelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static PCMJoanaFlowAnalysisDiagramModelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PCMJoanaFlowAnalysisDiagramModelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = PCMJoanaFlowAnalysisDiagramModelPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PCMJoanaFlowAnalysisDiagramModelSwitch<Adapter> modelSwitch = new PCMJoanaFlowAnalysisDiagramModelSwitch<Adapter>() {
		@Override
		public Adapter caseAssembly(Assembly object) {
			return createAssemblyAdapter();
		}

		@Override
		public Adapter caseClass(edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class object) {
			return createClassAdapter();
		}

		@Override
		public Adapter caseMethod(Method object) {
			return createMethodAdapter();
		}

		@Override
		public Adapter caseFlowAnalysisDiagram(FlowAnalysisDiagram object) {
			return createFlowAnalysisDiagramAdapter();
		}

		@Override
		public Adapter caseJOANAFlowAnalysisDiagram(JOANAFlowAnalysisDiagram object) {
			return createJOANAFlowAnalysisDiagramAdapter();
		}

		@Override
		public Adapter caseFlow(Flow object) {
			return createFlowAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Assembly <em>Assembly</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Assembly
	 * @generated
	 */
	public Adapter createAssemblyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class <em>Class</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class
	 * @generated
	 */
	public Adapter createClassAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method <em>Method</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method
	 * @generated
	 */
	public Adapter createMethodAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.FlowAnalysisDiagram <em>Flow Analysis Diagram</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.FlowAnalysisDiagram
	 * @generated
	 */
	public Adapter createFlowAnalysisDiagramAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.JOANAFlowAnalysisDiagram <em>JOANA Flow Analysis Diagram</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.JOANAFlowAnalysisDiagram
	 * @generated
	 */
	public Adapter createJOANAFlowAnalysisDiagramAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Flow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Flow
	 * @generated
	 */
	public Adapter createFlowAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //PCMJoanaFlowAnalysisDiagramModelAdapterFactory
