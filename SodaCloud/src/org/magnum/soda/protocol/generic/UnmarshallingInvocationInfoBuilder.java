// CHECKSTYLE:OFF
/**
 * Source code generated by Fluent Builders Generator
 * Do not modify this file
 * See generator home page at: http://code.google.com/p/fluent-builders-generator-eclipse-plugin/
 */

package org.magnum.soda.protocol.generic;

import org.json.simple.JSONArray;
import org.magnum.soda.marshalling.Marshaller;

public class UnmarshallingInvocationInfoBuilder
		extends
		UnmarshallingInvocationInfoBuilderBase<UnmarshallingInvocationInfoBuilder> {
	public static UnmarshallingInvocationInfoBuilder unmarshallingInvocationInfo() {
		return new UnmarshallingInvocationInfoBuilder();
	}

	public UnmarshallingInvocationInfoBuilder() {
		super(new UnmarshallingInvocationInfo());
	}

	public UnmarshallingInvocationInfo build() {
		return getInstance();
	}
}

class UnmarshallingInvocationInfoBuilderBase<GeneratorT extends UnmarshallingInvocationInfoBuilderBase<GeneratorT>> {
	private UnmarshallingInvocationInfo instance;

	protected UnmarshallingInvocationInfoBuilderBase(
			UnmarshallingInvocationInfo aInstance) {
		instance = aInstance;
	}

	protected UnmarshallingInvocationInfo getInstance() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	public GeneratorT withMarshalledParameters(JSONArray aValue) {
		instance.setMarshalledParameters(aValue);

		return (GeneratorT) this;
	}

	@SuppressWarnings("unchecked")
	public GeneratorT withAddedMarshalledParameter(Object aValue) {
		if (instance.getMarshalledParameters() == null) {
			instance.setMarshalledParameters(new JSONArray());
		}

		((JSONArray) instance.getMarshalledParameters()).add(aValue);

		return (GeneratorT) this;
	}

	@SuppressWarnings("unchecked")
	public GeneratorT withMarshaller(Marshaller aValue) {
		instance.setMarshaller(aValue);

		return (GeneratorT) this;
	}

	@SuppressWarnings("unchecked")
	public GeneratorT withMethod(String aValue) {
		instance.setMethod(aValue);

		return (GeneratorT) this;
	}

	@SuppressWarnings("unchecked")
	public GeneratorT withParameters(Object[] aValue) {
		instance.setParameters(aValue);

		return (GeneratorT) this;
	}

	@SuppressWarnings("unchecked")
	public GeneratorT withParameterTypes(Class<?>[] aValue) {
		instance.setParameterTypes(aValue);

		return (GeneratorT) this;
	}
}
