/**
 */
package edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel;

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
 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.PCMJoanaFlowAnalysisDiagramModelFactory
 * @model kind="package"
 * @generated
 */
public interface PCMJoanaFlowAnalysisDiagramModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "PCMJoanaFlowAnalysisDiagramModel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/PCMJoanaFlowAnalysisDiagramModel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "PCMJoanaFlowAnalysisDiagramModel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PCMJoanaFlowAnalysisDiagramModelPackage eINSTANCE = edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.PCMJoanaFlowAnalysisDiagramModelPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.AssemblyImpl <em>Assembly</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.AssemblyImpl
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.PCMJoanaFlowAnalysisDiagramModelPackageImpl#getAssembly()
	 * @generated
	 */
	int ASSEMBLY = 0;

	/**
	 * The feature id for the '<em><b>Class</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSEMBLY__CLASS = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSEMBLY__NAME = 1;

	/**
	 * The number of structural features of the '<em>Assembly</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSEMBLY_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Assembly</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSEMBLY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.ClassImpl <em>Class</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.ClassImpl
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.PCMJoanaFlowAnalysisDiagramModelPackageImpl#getClass_()
	 * @generated
	 */
	int CLASS = 1;

	/**
	 * The feature id for the '<em><b>Method</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS__METHOD = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS__NAME = 1;

	/**
	 * The number of structural features of the '<em>Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.MethodImpl <em>Method</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.MethodImpl
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.PCMJoanaFlowAnalysisDiagramModelPackageImpl#getMethod()
	 * @generated
	 */
	int METHOD = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__NAME = 0;

	/**
	 * The feature id for the '<em><b>Flow</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__FLOW = 1;

	/**
	 * The feature id for the '<em><b>Parameter</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__PARAMETER = 2;

	/**
	 * The number of structural features of the '<em>Method</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Method</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.FlowAnalysisDiagramImpl <em>Flow Analysis Diagram</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.FlowAnalysisDiagramImpl
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.PCMJoanaFlowAnalysisDiagramModelPackageImpl#getFlowAnalysisDiagram()
	 * @generated
	 */
	int FLOW_ANALYSIS_DIAGRAM = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_ANALYSIS_DIAGRAM__NAME = 0;

	/**
	 * The number of structural features of the '<em>Flow Analysis Diagram</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_ANALYSIS_DIAGRAM_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Flow Analysis Diagram</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_ANALYSIS_DIAGRAM_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.JOANAFlowAnalysisDiagramImpl <em>JOANA Flow Analysis Diagram</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.JOANAFlowAnalysisDiagramImpl
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.PCMJoanaFlowAnalysisDiagramModelPackageImpl#getJOANAFlowAnalysisDiagram()
	 * @generated
	 */
	int JOANA_FLOW_ANALYSIS_DIAGRAM = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JOANA_FLOW_ANALYSIS_DIAGRAM__NAME = FLOW_ANALYSIS_DIAGRAM__NAME;

	/**
	 * The feature id for the '<em><b>Assembly</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JOANA_FLOW_ANALYSIS_DIAGRAM__ASSEMBLY = FLOW_ANALYSIS_DIAGRAM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>JOANA Flow Analysis Diagram</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JOANA_FLOW_ANALYSIS_DIAGRAM_FEATURE_COUNT = FLOW_ANALYSIS_DIAGRAM_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>JOANA Flow Analysis Diagram</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JOANA_FLOW_ANALYSIS_DIAGRAM_OPERATION_COUNT = FLOW_ANALYSIS_DIAGRAM_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.FlowImpl <em>Flow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.FlowImpl
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.PCMJoanaFlowAnalysisDiagramModelPackageImpl#getFlow()
	 * @generated
	 */
	int FLOW = 5;

	/**
	 * The feature id for the '<em><b>Method</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW__METHOD = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW__ID = 1;

	/**
	 * The feature id for the '<em><b>Parameter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW__PARAMETER = 2;

	/**
	 * The number of structural features of the '<em>Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.ParameterImpl
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.PCMJoanaFlowAnalysisDiagramModelPackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__NAME = 0;

	/**
	 * The feature id for the '<em><b>Flow</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__FLOW = 1;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Assembly <em>Assembly</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Assembly</em>'.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Assembly
	 * @generated
	 */
	EClass getAssembly();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Assembly#getClass_ <em>Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Class</em>'.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Assembly#getClass_()
	 * @see #getAssembly()
	 * @generated
	 */
	EReference getAssembly_Class();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Assembly#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Assembly#getName()
	 * @see #getAssembly()
	 * @generated
	 */
	EAttribute getAssembly_Name();

	/**
	 * Returns the meta object for class '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class <em>Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Class</em>'.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class
	 * @generated
	 */
	EClass getClass_();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class#getMethod <em>Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Method</em>'.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class#getMethod()
	 * @see #getClass_()
	 * @generated
	 */
	EReference getClass_Method();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Class#getName()
	 * @see #getClass_()
	 * @generated
	 */
	EAttribute getClass_Name();

	/**
	 * Returns the meta object for class '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method <em>Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Method</em>'.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method
	 * @generated
	 */
	EClass getMethod();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method#getName()
	 * @see #getMethod()
	 * @generated
	 */
	EAttribute getMethod_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method#getFlow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Flow</em>'.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method#getFlow()
	 * @see #getMethod()
	 * @generated
	 */
	EReference getMethod_Flow();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method#getParameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameter</em>'.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Method#getParameter()
	 * @see #getMethod()
	 * @generated
	 */
	EReference getMethod_Parameter();

	/**
	 * Returns the meta object for class '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.FlowAnalysisDiagram <em>Flow Analysis Diagram</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Flow Analysis Diagram</em>'.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.FlowAnalysisDiagram
	 * @generated
	 */
	EClass getFlowAnalysisDiagram();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.FlowAnalysisDiagram#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.FlowAnalysisDiagram#getName()
	 * @see #getFlowAnalysisDiagram()
	 * @generated
	 */
	EAttribute getFlowAnalysisDiagram_Name();

	/**
	 * Returns the meta object for class '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.JOANAFlowAnalysisDiagram <em>JOANA Flow Analysis Diagram</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JOANA Flow Analysis Diagram</em>'.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.JOANAFlowAnalysisDiagram
	 * @generated
	 */
	EClass getJOANAFlowAnalysisDiagram();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.JOANAFlowAnalysisDiagram#getAssembly <em>Assembly</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Assembly</em>'.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.JOANAFlowAnalysisDiagram#getAssembly()
	 * @see #getJOANAFlowAnalysisDiagram()
	 * @generated
	 */
	EReference getJOANAFlowAnalysisDiagram_Assembly();

	/**
	 * Returns the meta object for class '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Flow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Flow</em>'.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Flow
	 * @generated
	 */
	EClass getFlow();

	/**
	 * Returns the meta object for the reference '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Flow#getMethod <em>Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Method</em>'.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Flow#getMethod()
	 * @see #getFlow()
	 * @generated
	 */
	EReference getFlow_Method();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Flow#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Flow#getId()
	 * @see #getFlow()
	 * @generated
	 */
	EAttribute getFlow_Id();

	/**
	 * Returns the meta object for the reference '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Flow#getParameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parameter</em>'.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Flow#getParameter()
	 * @see #getFlow()
	 * @generated
	 */
	EReference getFlow_Parameter();

	/**
	 * Returns the meta object for class '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Parameter#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Parameter#getName()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Parameter#getFlow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Flow</em>'.
	 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.Parameter#getFlow()
	 * @see #getParameter()
	 * @generated
	 */
	EReference getParameter_Flow();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PCMJoanaFlowAnalysisDiagramModelFactory getPCMJoanaFlowAnalysisDiagramModelFactory();

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
		 * The meta object literal for the '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.AssemblyImpl <em>Assembly</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.AssemblyImpl
		 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.PCMJoanaFlowAnalysisDiagramModelPackageImpl#getAssembly()
		 * @generated
		 */
		EClass ASSEMBLY = eINSTANCE.getAssembly();

		/**
		 * The meta object literal for the '<em><b>Class</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSEMBLY__CLASS = eINSTANCE.getAssembly_Class();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSEMBLY__NAME = eINSTANCE.getAssembly_Name();

		/**
		 * The meta object literal for the '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.ClassImpl <em>Class</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.ClassImpl
		 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.PCMJoanaFlowAnalysisDiagramModelPackageImpl#getClass_()
		 * @generated
		 */
		EClass CLASS = eINSTANCE.getClass_();

		/**
		 * The meta object literal for the '<em><b>Method</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLASS__METHOD = eINSTANCE.getClass_Method();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLASS__NAME = eINSTANCE.getClass_Name();

		/**
		 * The meta object literal for the '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.MethodImpl <em>Method</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.MethodImpl
		 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.PCMJoanaFlowAnalysisDiagramModelPackageImpl#getMethod()
		 * @generated
		 */
		EClass METHOD = eINSTANCE.getMethod();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METHOD__NAME = eINSTANCE.getMethod_Name();

		/**
		 * The meta object literal for the '<em><b>Flow</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METHOD__FLOW = eINSTANCE.getMethod_Flow();

		/**
		 * The meta object literal for the '<em><b>Parameter</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METHOD__PARAMETER = eINSTANCE.getMethod_Parameter();

		/**
		 * The meta object literal for the '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.FlowAnalysisDiagramImpl <em>Flow Analysis Diagram</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.FlowAnalysisDiagramImpl
		 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.PCMJoanaFlowAnalysisDiagramModelPackageImpl#getFlowAnalysisDiagram()
		 * @generated
		 */
		EClass FLOW_ANALYSIS_DIAGRAM = eINSTANCE.getFlowAnalysisDiagram();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLOW_ANALYSIS_DIAGRAM__NAME = eINSTANCE.getFlowAnalysisDiagram_Name();

		/**
		 * The meta object literal for the '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.JOANAFlowAnalysisDiagramImpl <em>JOANA Flow Analysis Diagram</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.JOANAFlowAnalysisDiagramImpl
		 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.PCMJoanaFlowAnalysisDiagramModelPackageImpl#getJOANAFlowAnalysisDiagram()
		 * @generated
		 */
		EClass JOANA_FLOW_ANALYSIS_DIAGRAM = eINSTANCE.getJOANAFlowAnalysisDiagram();

		/**
		 * The meta object literal for the '<em><b>Assembly</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JOANA_FLOW_ANALYSIS_DIAGRAM__ASSEMBLY = eINSTANCE.getJOANAFlowAnalysisDiagram_Assembly();

		/**
		 * The meta object literal for the '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.FlowImpl <em>Flow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.FlowImpl
		 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.PCMJoanaFlowAnalysisDiagramModelPackageImpl#getFlow()
		 * @generated
		 */
		EClass FLOW = eINSTANCE.getFlow();

		/**
		 * The meta object literal for the '<em><b>Method</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FLOW__METHOD = eINSTANCE.getFlow_Method();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLOW__ID = eINSTANCE.getFlow_Id();

		/**
		 * The meta object literal for the '<em><b>Parameter</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FLOW__PARAMETER = eINSTANCE.getFlow_Parameter();

		/**
		 * The meta object literal for the '{@link edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.ParameterImpl
		 * @see edu.kit.kastel.sdq.PCMJoanaFlowAnalysisDiagramModel.impl.PCMJoanaFlowAnalysisDiagramModelPackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__NAME = eINSTANCE.getParameter_Name();

		/**
		 * The meta object literal for the '<em><b>Flow</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETER__FLOW = eINSTANCE.getParameter_Flow();

	}

} //PCMJoanaFlowAnalysisDiagramModelPackage
