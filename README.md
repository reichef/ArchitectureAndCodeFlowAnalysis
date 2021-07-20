# ArchitectureAndCodeFlowAnalysis
This project is under development to create an analyses that is able to analyse flows over several projects by using the architectural model of the [Palladio Simulator](https://www.palladio-simulator.com/home/), the Palladio Component Model (PCM), and the IFC Analyses [JOANA](https://pp.ipd.kit.edu/projects/joana/) 


## Setting up the project
This project uses the contents of following submodules:

- compositionofsecurityanalyses (cosa)
- JOANAConnector

To setup the project for development, do the following steps:

1. Setup an [Eclipse Modeling Tools Version 2020-12](https://www.eclipse.org/downloads/packages/release/2020-12/r/eclipse-modeling-tools) including the Palladio Component Model (Release 4.3) and its Editors. For more information see [here](https://sdqweb.ipd.kit.edu/wiki/PCM_4.3).
2. Clone the Repository
3. Execute **git submodule init** and **git submodule update**  
4. Import following Projects in Eclipse for development:
    - edu.kit.kastel.sdq.PCMJoanaFlowAnalysis
    - edu.kit.kastel.sdq.ecoreannotations
    - edu.kit.kastel.sdq.cosa.correspondences.PCM2SourceCode
    - edu.kit.kastel.sdq.cosa.quality.JOANA
    - edu.kit.kastel.sdq.cosa.structure.SourceCode
5. Generate model-content if necessary






