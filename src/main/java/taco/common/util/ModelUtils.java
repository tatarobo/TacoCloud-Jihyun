package taco.common.util;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NameTokenizers;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModelUtils {
	private static final ModelMapper MODEL_MAPPER;
	private static final ModelMapper SKIP_NULL_MODEL_MAPPER;

	static {
		MODEL_MAPPER = createDefaultModelMapper();

		SKIP_NULL_MODEL_MAPPER = createDefaultModelMapper();
		SKIP_NULL_MODEL_MAPPER.getConfiguration()
							  .setPropertyCondition(Conditions.isNotNull());
	}

	private static ModelMapper createDefaultModelMapper() {
		ModelMapper mapper = new ModelMapper();

		Configuration configuration = mapper.getConfiguration();
		configuration.setMatchingStrategy(MatchingStrategies.STRICT);
		configuration.setSourceNameTokenizer(NameTokenizers.UNDERSCORE);
		configuration.setDestinationNameTokenizer(NameTokenizers.UNDERSCORE);

		return mapper;
	}

	public static ModelMapper getModelMapper() {
		return MODEL_MAPPER;
	}

	private static ModelMapper getSkipNullModelMapper() {
		return SKIP_NULL_MODEL_MAPPER;
	}

	public static <D> D map(Object source, Class<D> destinationType) {
		if (source != null) {
			return getModelMapper().map(source, destinationType);
		}
		return null;
	}

	public static void map(Object source, Object destination) {
		if (source != null && destination != null) {
			getModelMapper().map(source, destination);
		}
	}

	public static <D> List<D> map(List<?> source, Class<D> destinationType) {
		return Optional.ofNullable(source)
					   .map(List::stream)
					   .orElse(Stream.empty())
					   .map(value -> map(value, destinationType))
					   .collect(Collectors.toList());
	}

	public static <D> Set<D> mapToSet(List<?> source, Class<D> destinationType) {
		return Optional.ofNullable(source)
					   .map(List::stream)
					   .orElse(Stream.empty())
					   .map(value -> map(value, destinationType))
					   .collect(Collectors.toSet());
	}

	public static void mapSkipNull(Object source, Object destination) {
		if (source != null && destination != null) {
			getSkipNullModelMapper().map(source, destination);
		}
	}
}
