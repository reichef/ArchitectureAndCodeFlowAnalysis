package edu.kit.kastel.sdq.pcmjoanaflowanalysis.pcmflow;

import org.palladiosimulator.pcm.repository.OperationSignature;

import edu.kit.kastel.sdq.pcmjoanaflowanalysis.Datastructure.AssemblyConnectorRepresentation;

public class CallInformation {
	private final AssemblyConnectorRepresentation assemblyConnector;
	private final OperationSignature calledOperation;
	
	public CallInformation(AssemblyConnectorRepresentation connector, OperationSignature operationSignature) {
		this.assemblyConnector = connector;
		this.calledOperation = operationSignature;
	}

	public AssemblyConnectorRepresentation getAssemblyConnector() {
		return assemblyConnector;
	}

	public OperationSignature getCalledOperation() {
		return calledOperation;
	}
}
