/*****************************************************************************
 * Copyright 2013 Olivier Croquette <ocroquette@free.fr>                     *
 *                                                                           *
 *  Licensed under the Apache License, Version 2.0 (the "License");          *
 *  you may not use this file except in compliance with the License.         *
 *  You may obtain a copy of the License at                                  *
 *                                                                           *
 *      http://www.apache.org/licenses/LICENSE-2.0                           *
 *                                                                           *
 *  Unless required by applicable law or agreed to in writing, software      *
 *  distributed under the License is distributed on an "AS IS" BASIS,        *
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. *
 *  See the License for the specific language governing permissions and      *
 *  limitations under the License.                                           *
 ****************************************************************************/
package org.magnum.soda.server.wamp.messages;

import java.lang.reflect.Type;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


public class CallResultMessage extends Message {

	public String callId;
	public JsonElement payload;

	public CallResultMessage() {
		super(MessageType.CALLRESULT);
	}

	public CallResultMessage(String callId) {
		super(MessageType.CALLRESULT);
		this.callId = callId;
	}


	public static class Serializer implements JsonSerializer<CallResultMessage> {
		@Override
		public JsonElement serialize(CallResultMessage msg, Type arg1,
				JsonSerializationContext context) {
			JsonArray array = new JsonArray();
			array.add(context.serialize(msg.getType().getCode()));
			array.add(context.serialize(msg.callId));
			array.add(msg.payload);
			return array;
		}
	}

	public static class Deserializer implements JsonDeserializer<CallResultMessage> {
		@Override
		public CallResultMessage deserialize(JsonElement element, Type arg1,
				JsonDeserializationContext context) throws JsonParseException {

			JsonArray array = element.getAsJsonArray();
			
			if ( MessageType.fromInteger(array.get(0).getAsInt()) != MessageType.CALLRESULT)
				return null;
			
			CallResultMessage msg = new CallResultMessage();
			msg.callId = array.get(1).getAsString();
			msg.payload = array.get(2);
			return msg;
		}
	}

	public <PayloadType> PayloadType getPayload(Class<PayloadType> type) {
		Gson gson = new Gson();
		return gson.fromJson(payload, type);
	}

	public <PayloadType> void setPayload(PayloadType payload, Class<PayloadType> type) {
		Gson gson = new Gson();
		this.payload = gson.toJsonTree(payload);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
