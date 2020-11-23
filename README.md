# ArchitectureAndCodeFlowAnalysis
This project is under development to create an analyses that is able to analyse flows over several projects by using the architectural model of the [Palladio Simulator](https://www.palladio-simulator.com/home/), the Palladio Component Model (PCM), and the IFC Analyses [JOANA](https://pp.ipd.kit.edu/projects/joana/) 


## Setting up the project
This project uses the contents of following submodules:

- MDSDBasics
- compositionofsecurityanalyses
- JOANAConnector

Because many/all of those submodules are currently in private repositories, request access directly. 

To setup the project for development, do the following steps:

1. Setup an Eclipse including the Palladio. For more information see [here](https://sdqweb.ipd.kit.edu/wiki/PCM_4.3)
2. Clone the Repository
3. Execute **git submodule init** and **git submodule update**  
4. Import following Projects in Eclipse for development:
    - edu.kit.kastel.sdq.PCMJoanaFlowAnalysis
    - edu.kit.kastel.sdq.ecoreannotations
    -  edu.kit.ipd.sdq.composition.securityanalyses.basic (MDSDBasics)
    - edu.kit.ipd.sdq.composition.securityanalyses.coupling.correspondences.PCM2SourceCode (compositionofsecurityanalyses)
    - edu.kit.ipd.sdq.composition.securityanalyses.coupling.quality.JOANA (compositionofsecurityanalyses)
    - edu.kit.ipd.sdq.composition.securityanalyses.coupling.structure.SourceCode (compositionofsecurityanalyses)
5. Generate model-content if necessary






