package edu.kit.kastel.sdq.pcmjoanaflowanalysis.tests;

import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.Models;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.AbstractJoanaFlowDatastructureFactory;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.FlowGraph;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.FlowGraphEdge;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.FlowGraphVertex;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored.PCMJoanaFlowDatastructureFactory;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored.SystemRepresentation;
import edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmutil.PCMSubtypeResolver;

class PCMJoanaFlowDatastructureFactoryTest {

	private static final String TESTMODEL_PATH = System.getProperty("user.dir") + ".CaseStudies.FlowDatastructureTestCases";
	
	private static final String TESTMODEL_NAME__BASIC_COMPONENT = "BasicComponent";
	private static final String TESTMODEL_NAME__COMPOSITE_COMPONENT = "CompositeComponent";
	private static final String TESTMODEL_NAME__NESTED_COMPOSITE_COMPONENT = "NestedCompositeComponent";
	private static final String TESTMODEL_NAME__BASIC_TO_BASIC_ASSEMBLY = "BasicToBasicAssembly";
	private static final String TESTMODEL_NAME__BASIC_TO_COMPOSITE_ASSEMBLY = "BasicToCompositeAssembly";
	private static final String TESTMODEL_NAME__COMPOSITE_TO_BASIC_ASSEMBLY = "CompositeToBasicAssembly";
	private static final String TESTMODEL_NAME__COMPOSITE_TO_COMPOSITE_ASSEMBLY = "CompositeToCompositeAssembly";

	private static Models testmodel_BasicComponent;
	private static Models testmodel_CompositeComponent;
	private static Models testmodel_NestedCompositeComponent;
	private static Models testmodel_BasicToBasicAssembly;
	private static Models testmodel_BasicToCompositeAssembly;
	private static Models testmodel_CompositeToBasicAssembly;
	private static Models testmodel_CompositeToCompositeAssembly;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		testmodel_BasicComponent = loadModels(Path.of(TESTMODEL_PATH, TESTMODEL_NAME__BASIC_COMPONENT));
		testmodel_CompositeComponent = loadModels(Path.of(TESTMODEL_PATH, TESTMODEL_NAME__COMPOSITE_COMPONENT));
		testmodel_NestedCompositeComponent = loadModels(Path.of(TESTMODEL_PATH, TESTMODEL_NAME__NESTED_COMPOSITE_COMPONENT));
		testmodel_BasicToBasicAssembly = loadModels(Path.of(TESTMODEL_PATH, TESTMODEL_NAME__BASIC_TO_BASIC_ASSEMBLY));
		testmodel_BasicToCompositeAssembly = loadModels(Path.of(TESTMODEL_PATH, TESTMODEL_NAME__BASIC_TO_COMPOSITE_ASSEMBLY));
		testmodel_CompositeToBasicAssembly = loadModels(Path.of(TESTMODEL_PATH, TESTMODEL_NAME__COMPOSITE_TO_BASIC_ASSEMBLY));
		testmodel_CompositeToCompositeAssembly = loadModels(Path.of(TESTMODEL_PATH, TESTMODEL_NAME__COMPOSITE_TO_COMPOSITE_ASSEMBLY));
		// Add more models here..
	}
	
	private static Models loadModels(Path path) {
		// TODO: load model files
		return null;
	}

	@Test
	void createFlowGraphRepresentation_BasicComponentShouldBeRepresentedAsVertex() {
		AbstractJoanaFlowDatastructureFactory factory = new PCMJoanaFlowDatastructureFactory(testmodel_BasicComponent.getSystem(), testmodel_BasicComponent.getAnnotationRepository());
		FlowGraph representation = factory.createFlowGraphRepresentation();
		
		assertNotNull(representation, "Model \'BasicComponent\' is null");
		assertTrue(representation.getVertices().size() == 1, "Number of Vertices incorrect");
		assertTrue(representation.getEdges().size() == 0, "Number of Edges incorrect");
		
		List<FlowGraphVertex> vertices = new ArrayList<>(representation.getVertices());
		
		List<RepositoryComponent> components = extractRepositoryComponents(testmodel_BasicComponent.getSystem());
		assertEquals(vertices.get(0).getId(), components.get(0).getId());
	}
	
	@Test
	void createFlowGraphRepresentation_CompositeComponentShouldNotBeRepresented() {
		AbstractJoanaFlowDatastructureFactory factory = new PCMJoanaFlowDatastructureFactory(testmodel_CompositeComponent.getSystem(), testmodel_CompositeComponent.getAnnotationRepository());
		FlowGraph representation = factory.createFlowGraphRepresentation();
		
		assertNotNull(representation, "Model \'CompositeComponent\' is null");
		assertTrue(representation.getVertices().size() == 1, "Number of Vertices incorrect");
		assertTrue(representation.getEdges().size() == 0, "Number of Edges incorrect");
		
		List<FlowGraphVertex> vertices = new ArrayList<>(representation.getVertices());
		
		List<RepositoryComponent> components = extractRepositoryComponents(testmodel_CompositeComponent.getSystem());
		List<CompositeComponent> compositeComponents = (List<CompositeComponent>) PCMSubtypeResolver.filterCompositeComponents(components);
		
		assertNotEquals(vertices.get(0).getId(), compositeComponents.get(0).getId());
	}
	
	private List<RepositoryComponent> extractRepositoryComponents(ComposedStructure structure) {
		return structure.getAssemblyContexts__ComposedStructure().stream().map(context -> context.getEncapsulatedComponent__AssemblyContext()).collect(Collectors.toList());
	}
	
	@Test
	void createFlowGraphRepresentation_NestedCompositeComponentShouldBeFlattened() {
		AbstractJoanaFlowDatastructureFactory factory = new PCMJoanaFlowDatastructureFactory(testmodel_NestedCompositeComponent.getSystem(), testmodel_NestedCompositeComponent.getAnnotationRepository());
		FlowGraph representation = factory.createFlowGraphRepresentation();
		
		assertNotNull(representation, "Model \'NestedCompositeComponent\' is null");
		assertTrue(representation.getVertices().size() == 1, "Number of Vertices incorrect");
		assertTrue(representation.getEdges().size() == 0, "Number of Edges incorrect");
		
		List<FlowGraphVertex> vertices = new ArrayList<>(representation.getVertices());
		
		List<RepositoryComponent> components = extractRepositoryComponents(testmodel_CompositeComponent.getSystem());
		List<CompositeComponent> compositeComponents = (List<CompositeComponent>) PCMSubtypeResolver.filterCompositeComponents(components);
		CompositeComponent compositeComponent = compositeComponents.get(0);
		
		components = extractRepositoryComponents(compositeComponent);
		compositeComponents = (List<CompositeComponent>) PCMSubtypeResolver.filterCompositeComponents(components);
		compositeComponent = compositeComponents.get(0);
		
		components = extractRepositoryComponents(compositeComponent);
		List<BasicComponent> basicComponents = (List<BasicComponent>) PCMSubtypeResolver.filterBasicComponents(components);
		
		assertEquals(vertices.get(0).getId(), basicComponents.get(0).getId());
	}
	
	@Test
	void createFlowGraphRepresentation_BasicToBasicAssemblyCheckEdgeRepresentation() {
		AbstractJoanaFlowDatastructureFactory factory = new PCMJoanaFlowDatastructureFactory(testmodel_BasicToBasicAssembly.getSystem(), testmodel_BasicToBasicAssembly.getAnnotationRepository());
		FlowGraph representation = factory.createFlowGraphRepresentation();
		
		assertNotNull(representation, "Model \'BasicToBasicAssembly\' is null");
		assertTrue(representation.getVertices().size() == 2, "Number of Vertices incorrect");
		assertTrue(representation.getEdges().size() == 1, "Number of Edges incorrect");
		
		List<FlowGraphEdge> edges = new ArrayList<>(representation.getEdges());
		
		List<Connector> connectors = testmodel_BasicToBasicAssembly.getSystem().getConnectors__ComposedStructure();
		List<AssemblyConnector> assemblyConnectors = (List<AssemblyConnector>) PCMSubtypeResolver.filterAssemblyConnectors(connectors);
		AssemblyConnector assemblyConnector = assemblyConnectors.get(0);
		
		RepositoryComponent providingComponent = assemblyConnector.getProvidingAssemblyContext_AssemblyConnector().getEncapsulatedComponent__AssemblyContext();
		RepositoryComponent requiringComponent = assemblyConnector.getRequiringAssemblyContext_AssemblyConnector().getEncapsulatedComponent__AssemblyContext();
		
		assertEquals(edges.get(0).getTail().getId(), requiringComponent.getId());
		assertEquals(edges.get(0).getHead().getId(), providingComponent.getId());
	}
	
	@Test
	void createFlowGraphRepresentation_BasicToCompositeAssemblyCheckEdgeRepresentation() {
		AbstractJoanaFlowDatastructureFactory factory = new PCMJoanaFlowDatastructureFactory(testmodel_BasicToCompositeAssembly.getSystem(), testmodel_BasicToCompositeAssembly.getAnnotationRepository());
		FlowGraph representation = factory.createFlowGraphRepresentation();
		
		assertNotNull(representation, "Model \'BasicToCompositeAssembly\' is null");
		assertTrue(representation.getVertices().size() == 2, "Number of Vertices incorrect");
		assertTrue(representation.getEdges().size() == 1, "Number of Edges incorrect");
		
		List<RepositoryComponent> components = extractRepositoryComponents(testmodel_BasicToCompositeAssembly.getSystem());
		List<BasicComponent> basicComponents = (List<BasicComponent>) PCMSubtypeResolver.filterBasicComponents(components);
		RepositoryComponent requiringComponent = basicComponents.get(0);
		
		List<CompositeComponent> compositeComponents = (List<CompositeComponent>) PCMSubtypeResolver.filterCompositeComponents(components);
		components = extractRepositoryComponents(compositeComponents.get(0));
		RepositoryComponent providingComponent = components.get(0);
		
		List<FlowGraphEdge> edges = new ArrayList<>(representation.getEdges());
		
		assertEquals(edges.get(0).getTail().getId(), requiringComponent.getId());
		assertEquals(edges.get(0).getHead().getId(), providingComponent.getId());
	}
	
	@Test
	void createFlowGraphRepresentation_CompositeToBasicAssemblyCheckEdgeRepresentation() {
		AbstractJoanaFlowDatastructureFactory factory = new PCMJoanaFlowDatastructureFactory(testmodel_CompositeToBasicAssembly.getSystem(), testmodel_CompositeToBasicAssembly.getAnnotationRepository());
		FlowGraph representation = factory.createFlowGraphRepresentation();
		
		assertNotNull(representation, "Model \'CompositeToBasicAssembly\' is null");
		assertTrue(representation.getVertices().size() == 1, "Number of Vertices incorrect");
		assertTrue(representation.getEdges().size() == 0, "Number of Edges incorrect");
		
		List<RepositoryComponent> components = extractRepositoryComponents(testmodel_CompositeToBasicAssembly.getSystem());
		List<BasicComponent> basicComponents = (List<BasicComponent>) PCMSubtypeResolver.filterBasicComponents(components);
		RepositoryComponent providingComponent = basicComponents.get(0);
		
		List<CompositeComponent> compositeComponents = (List<CompositeComponent>) PCMSubtypeResolver.filterCompositeComponents(components);
		components = extractRepositoryComponents(compositeComponents.get(0));
		RepositoryComponent requiringComponent = components.get(0);
		
		List<FlowGraphEdge> edges = new ArrayList<>(representation.getEdges());
		
		assertEquals(edges.get(0).getTail().getId(), requiringComponent.getId());
		assertEquals(edges.get(0).getHead().getId(), providingComponent.getId());
	}
	
	@Test
	void createFlowGraphRepresentation_CompositeToCompositeAssemblyCheckEdgeRepresentation() {
		AbstractJoanaFlowDatastructureFactory factory = new PCMJoanaFlowDatastructureFactory(testmodel_CompositeToCompositeAssembly.getSystem(), testmodel_CompositeToCompositeAssembly.getAnnotationRepository());
		FlowGraph representation = factory.createFlowGraphRepresentation();
		
		assertNotNull(representation, "Model \'CompositeToCompositeAssembly\' is null");
		assertTrue(representation.getVertices().size() == 2, "Number of Vertices incorrect");
		assertTrue(representation.getEdges().size() == 1, "Number of Edges incorrect");
		
		List<RepositoryComponent> components = extractRepositoryComponents(testmodel_CompositeToCompositeAssembly.getSystem());
		List<CompositeComponent> compositeComponents = (List<CompositeComponent>) PCMSubtypeResolver.filterCompositeComponents(components);
		
		components = compositeComponents.stream().flatMap(composite -> extractRepositoryComponents(composite).stream()).collect(Collectors.toList());
		List<BasicComponent> basicComponents = (List<BasicComponent>) PCMSubtypeResolver.filterBasicComponents(components);
		RepositoryComponent providingComponent = basicComponents.get(0);
		
		compositeComponents = (List<CompositeComponent>) PCMSubtypeResolver.filterCompositeComponents(components);
		components = extractRepositoryComponents(compositeComponents.get(0));
		RepositoryComponent requiringComponent = components.get(0);
		
		List<FlowGraphEdge> edges = new ArrayList<>(representation.getEdges());
		
		assertEquals(edges.get(0).getTail().getId(), requiringComponent.getId());
		assertEquals(edges.get(0).getHead().getId(), providingComponent.getId());
	}

}
