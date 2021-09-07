package edu.kit.kastel.sdq.pcmjoanaflowanalysis.datastructure.refactored;

import java.util.Objects;

public class Connection<T1, T2> {

	private T1 endpoint1;
	private T2 endpoint2;
	
	public Connection(T1 endpoint1, T2 endpoint2) {
		this.endpoint1 = endpoint1;
		this.endpoint2 = endpoint2;
	}

	public T1 getEndpoint1() {
		return endpoint1;
	}

	public void setEndpoint1(T1 endpoint1) {
		this.endpoint1 = endpoint1;
	}

	public T2 getEndpoint2() {
		return endpoint2;
	}

	public void setEndpoint2(T2 endpoint2) {
		this.endpoint2 = endpoint2;
	}

	@Override
	public int hashCode() {
		return Objects.hash(endpoint1, endpoint2);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Connection other = (Connection) obj;
		return Objects.equals(this.endpoint1, other.endpoint1) && Objects.equals(this.endpoint1, other.endpoint2);
	}

}
