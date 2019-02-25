package br.com.lanchonete.lanchonete.view;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import br.com.lanchonete.lanchonete.domain.MoedaReal;

public class MoedaRealSerializer extends JsonSerializer<MoedaReal> {

	@Override
	public void serialize(final MoedaReal value, final JsonGenerator gen, final SerializerProvider serializers) throws IOException {
		gen.writeString(value.asBigDecimal().toString());
	}

}
