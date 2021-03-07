package vaseba.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import vaseba.converter.Converter;
import vaseba.model.OrderIn;
import vaseba.model.OrderOut;
import vaseba.parser.OrderParser;
import vaseba.exception.ParserException;

import java.io.BufferedReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OrderParserJson extends OrderParser {
	public static final String JSON_IS_INVALID = "формат не соответсвует формату json";

	private ObjectMapper jsonMapper;

	public OrderParserJson(String fileName) {
		super(fileName);
		/** Метод изменения состояния функции включения / выключения сопоставителя для этого экземпляра сопоставителя */
		jsonMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	@Override
	public List<OrderOut> parse(BufferedReader input) {
		String data = input.lines().collect(Collectors.joining());
		/** http://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/StringUtils.html
		 * */
		if (StringUtils.isBlank(data)) {
			throw new ParserException(FILE_IS_EMPTY);
		}

		Object json;
		try {
			json = new JSONTokener(data).nextValue();
		} catch (JSONException je) {
			throw new ParserException(JSON_IS_INVALID);
		}

		/** https://docs.spring.io/spring-boot/docs/2.3.0.M1/api/org/springframework/boot/configurationprocessor/json/JSONTokener.html*/
		if (json instanceof JSONObject) { //json with single object-order
			OrderIn source;
			try {
				source = jsonMapper.readValue(data, OrderIn.class);
			} catch (Exception e) {
				throw new ParserException(cutDeserializationError(e.getMessage()));
			}
			/** Возвращает неизменяемый список, содержащий только указанный объект */
			return Collections.singletonList(Converter.convertInToOut(source, filename, null, null));
		} else if (json instanceof JSONArray) { //json with array of orders
			List<OrderIn> sourceArray;
			try {
				sourceArray = jsonMapper.readValue(data, new TypeReference<List<OrderIn>>(){});
			} catch (Exception e) {
				throw new ParserException(cutDeserializationError(e.getMessage()));
			}
			return sourceArray.parallelStream()
					.map(s -> Converter.convertInToOut(s, filename, null, null))
					.collect(Collectors.toList());
		} else { //strange content
			throw new ParserException(JSON_IS_INVALID);
		}
	}

	private String cutDeserializationError(String errorMsg) {
		return errorMsg.substring(0, errorMsg.indexOf('\n')).replace('"','`');
	}

}
