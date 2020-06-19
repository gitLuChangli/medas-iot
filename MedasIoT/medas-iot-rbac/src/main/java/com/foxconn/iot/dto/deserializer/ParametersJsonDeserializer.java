package com.foxconn.iot.dto.deserializer;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.foxconn.iot.dto.PropertyDto;

public class ParametersJsonDeserializer extends JsonDeserializer<List<PropertyDto>> {

	@Override
	public List<PropertyDto> deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		while (!p.isClosed()) {
			JsonToken jsonToken = p.nextToken();
			System.out.println(jsonToken.asString());
		}
		return  null;
	}
}
