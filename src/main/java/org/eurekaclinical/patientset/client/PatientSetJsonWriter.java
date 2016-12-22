package org.eurekaclinical.patientset.client;

/*-
 * #%L
 * Eureka! Clinical Patient Set Client
 * %%
 * Copyright (C) 2016 Emory University
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.IOException;
import java.io.OutputStream;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Andrew Post
 */
public class PatientSetJsonWriter implements AutoCloseable {

    private final JsonGenerator jsonGenerator;

    public PatientSetJsonWriter(OutputStream outputStream, String name, String username) throws IOException {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        if (username == null) {
            throw new IllegalArgumentException("username cannot be null");
        }
        ObjectMapper mapper = new ObjectMapper();
        this.jsonGenerator = mapper.getJsonFactory().createJsonGenerator(outputStream, JsonEncoding.UTF8);
        this.jsonGenerator.writeStartObject();
        this.jsonGenerator.writeFieldName("name");
        this.jsonGenerator.writeString(name);
        this.jsonGenerator.writeFieldName("username");
        this.jsonGenerator.writeString(username);
        this.jsonGenerator.writeFieldName("patients");
        this.jsonGenerator.writeStartArray();
    }

    public void writePatient(String patientId) throws IOException {
        if (patientId == null) {
            throw new IllegalArgumentException("patientId cannot be null");
        }
        this.jsonGenerator.writeString(patientId);
    }

    public void finish() throws IOException {
        this.jsonGenerator.writeEndArray();
        this.jsonGenerator.writeEndObject();
    }

    @Override
    public void close() throws IOException {
        this.jsonGenerator.close();
    }

}
