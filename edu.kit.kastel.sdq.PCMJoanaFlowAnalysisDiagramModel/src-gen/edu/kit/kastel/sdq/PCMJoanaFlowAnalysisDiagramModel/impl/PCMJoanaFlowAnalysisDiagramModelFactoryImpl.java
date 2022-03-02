/**
 */
package edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl;

import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Assembly;
import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Flow;
import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.JOANAFlowAnalysisDiagram;
import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method;
import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.PCMJoanaFlowAnalysisDiagramModelFactory;
import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.PCMJoanaFlowAnalysisDiagramModelPackage;

import edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Parameter;
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
public class PCMJoanaFlowAnalysisDiagramModelFactoryImpl extends EFactoryImpl
		implements PCMJoanaFlowAnalysisDiagramModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PCMJoanaFlowAnalysisDiagramModelFactory init() {
		try {
			PCMJoanaFlowAnalysisDiagramModelFactory thePCMJoanaFlowAnalysisDiagramModelFactory = (PCMJoanaFlowAnalysisDiagramModelFactory) EPackage.Registry.INSTANCE
					.getEFactory(PCMJoanaFlowAnalysisDiagramModelPackage.eNS_URI);
			if (thePCMJoanaFlowAnalysisDiagramModelFactory != null) {
				return thePCMJoanaFlowAnalysisDiagramModelFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PCMJoanaFlowAnalysisDiagramModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PCMJoanaFlowAnalysisDiagramModelFactoryImpl() {
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
		case PCMJoanaFlowAnalysisDiagramModelPackage.ASSEMBLY:
			return createAssembly();
		case PCMJoanaFlowAnalysisDiagramModelPackage.CLASS:
			return createClass();
		case PCMJoanaFlowAnalysisDiagramModelPackage.METHOD:
			return createMethod();
		case PCMJoanaFlowAnalysisDiagramModelPackage.JOANA_FLOW_ANALYSIS_DIAGRAM:
			return createJOANAFlowAnalysisDiagram();
		case PCMJoanaFlowAnalysisDiagramModelPackage.FLOW:
			return createFlow();
		case PCMJoanaFlowAnalysisDiagramModelPackage.PARAMETER:
			return createParameter();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Assembly createAssembly() {
		AssemblyImpl assembly = new AssemblyImpl();
		return assembly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class createClass() {
		ClassImpl class_ = new ClassImpl();
		return class_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Method createMethod() {
		MethodImpl method = new MethodImpl();
		return method;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JOANAFlowAnalysisDiagram createJOANAFlowAnalysisDiagram() {
		JOANAFlowAnalysisDiagramImpl joanaFlowAnalysisDiagram = new JOANAFlowAnalysisDiagramImpl();
		return joanaFlowAnalysisDiagram;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Flow createFlow() {
		FlowImpl flow = new FlowImpl();
		return flow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Parameter createParameter() {
		ParameterImpl parameter = new ParameterImpl();
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PCMJoanaFlowAnalysisDiagramModelPackage getPCMJoanaFlowAnalysisDiagramModelPackage() {
		return (PCMJoanaFlowAnalysisDiagramModelPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static PCMJoanaFlowAnalysisDiagramModelPackage getPackage() {
		return PCMJoanaFlowAnalysisDiagramModelPackage.eINSTANCE;
	}

} //PCMJoanaFlowAnalysisDiagramModelFactoryImpl
