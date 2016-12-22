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
import java.io.InputStream;
import java.util.NoSuchElementException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Reads a patient set from an input stream in a streaming fashion.
 *
 * This class is designed to be used as follows:  <code>
 * ...
 * try (PatientSetJsonReader jsonReader = new PatientSetJsonReader(inputStream)) {
 *   while (jsonReader.hasMorePatients()) {
 *     String patientId = jsonReader.nextPatientId();
 *     ...
 *   }
 *   String username = jsonReader.getUsername(); //the owner
 *   String name = jsonReader.getName(); //the name of the patient set
 * }
 * </code> The username and patient set name fields are not guaranteed to be set
 * until {@link #hasMorePatients} is <code>false</code>.
 *
 * This class is not thread-safe.
 *
 * @author Andrew Post
 */
public class PatientSetJsonReader implements AutoCloseable {

    private final JsonParser jsonParser;
    private String name;
    private String username;
    private boolean inPatientIdArray;
    private boolean crankTurned;
    private String nextPatientId;

    public PatientSetJsonReader(InputStream inputStream) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        this.jsonParser = mapper.getJsonFactory().createJsonParser(inputStream);
        this.jsonParser.nextToken(); // {
    }

    public boolean hasMorePatients() throws IOException {
        if (!this.crankTurned) {
            turnTheCrank();
            this.crankTurned = true;
        }
        return this.inPatientIdArray;
    }

    public String nextPatientId() throws IOException {
        if (!this.crankTurned) {
            turnTheCrank();
        }
        if (this.nextPatientId == null) {
            throw new NoSuchElementException("No patient id");
        }
        this.crankTurned = false;
        return this.nextPatientId;
    }

    public String getName() throws IOException {
        if (this.name == null) {
            throw new IOException("Required field name not present");
        } else {
            return this.name;
        }
    }

    public String getUsername() throws IOException {
        if (this.username == null) {
            throw new IOException("Required field username not present");
        } else {
            return this.username;
        }
    }

    @Override
    public void close() throws IOException {
        this.jsonParser.close();
    }

    private boolean turnTheCrank() throws IOException {
        if (this.inPatientIdArray) {
            if (this.jsonParser.nextToken() != JsonToken.END_ARRAY) {
                this.nextPatientId = this.jsonParser.getText();
                return true;
            } else {
                this.inPatientIdArray = false;
            }
        }
        while (this.jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = this.jsonParser.getCurrentName();
            switch (fieldName) {
                case "name":
                    this.jsonParser.nextToken();
                    this.name = this.jsonParser.getText();
                    break;
                case "username":
                    this.jsonParser.nextToken();
                    this.username = this.jsonParser.getText();
                    break;
                case "patientIds":
                    this.jsonParser.nextToken(); // [
                    if (this.jsonParser.nextToken() != JsonToken.END_ARRAY) {
                        this.nextPatientId = this.jsonParser.getText();
                        this.inPatientIdArray = true;
                        return true;
                    } else {
                        this.inPatientIdArray = false;
                        return false;
                    }
                default:
                    throw new IOException("Unexpected field name " + fieldName);
            }
        }
        return false;
    }

}
