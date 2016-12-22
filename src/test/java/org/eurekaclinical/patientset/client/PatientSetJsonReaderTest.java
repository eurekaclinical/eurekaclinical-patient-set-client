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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Andrew Post
 */
public class PatientSetJsonReaderTest {
    @Test
    public void testFullPatientIds() throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/testFull.json");
                PatientSetJsonReader jsonReader = new PatientSetJsonReader(is)) {
            List<String> actuals = new ArrayList<>();
            while (jsonReader.hasMorePatients()) {
                actuals.add(jsonReader.nextPatientId());
            }
            assertEquals(Arrays.asList(new String[] {"00001", "00002", "00003"}), actuals);
        }
    }
    
    @Test
    public void testFullName() throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/testFull.json");
                PatientSetJsonReader jsonReader = new PatientSetJsonReader(is)) {
            while (jsonReader.hasMorePatients()) {
                jsonReader.nextPatientId();
            }
            assertEquals("Full", jsonReader.getName());
        }
    }
    
    @Test
    public void testFullUsername() throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/testFull.json");
                PatientSetJsonReader jsonReader = new PatientSetJsonReader(is)) {
            while (jsonReader.hasMorePatients()) {
                jsonReader.nextPatientId();
            }
            assertEquals("testuser", jsonReader.getUsername());
        }
    }
    
    @Test
    public void testNoPatientIds() throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/testNoPatientIds.json");
                PatientSetJsonReader jsonReader = new PatientSetJsonReader(is)) {
            List<String> actuals = new ArrayList<>();
            while (jsonReader.hasMorePatients()) {
                actuals.add(jsonReader.nextPatientId());
            }
            assertEquals(Collections.emptyList(), actuals);
        }
    }
    
    @Test
    public void testPatientIdsMissing() throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/testPatientIdsMissing.json");
                PatientSetJsonReader jsonReader = new PatientSetJsonReader(is)) {
            List<String> actuals = new ArrayList<>();
            while (jsonReader.hasMorePatients()) {
                actuals.add(jsonReader.nextPatientId());
            }
            assertEquals(Collections.emptyList(), actuals);
        }
    }
}
