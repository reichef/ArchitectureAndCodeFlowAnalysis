package edu.kit.kastel.sdq.pcmjoanaflowanalysis.utils;

import java.util.Objects;

public class Pair<A, B> {
	private final A first;
	private final B second;
	
	public Pair(A first, B second) {
		this.first = first;
		this.second = second;
	}

	public A getFirst() {
		return first;
	}

	public B getSecond() {
		return second;
	}

	@Override
	public int hashCode() {
		return Objects.hash(first, second);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Pair other = (Pair) obj;
		return Objects.equals(this.first, other.first) && Objects.equals(this.second, other.second);
	}
	
}
