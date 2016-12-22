package org.eurekaclinical.patientset.client.comm;

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

/**
 *
 * @author Andrew Post
 */
public class PatientSet {

    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private String username;
    private String name;
    private String[] patients;

    public PatientSet() {
        this.patients = EMPTY_STRING_ARRAY;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getPatients() {
        return patients.clone();
    }

    public void setPatients(String[] patients) {
        if (patients != null) {
            this.patients = patients.clone();
        } else {
            this.patients = EMPTY_STRING_ARRAY;
        }
    }

}
