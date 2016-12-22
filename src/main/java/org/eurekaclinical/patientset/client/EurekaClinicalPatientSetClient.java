package org.eurekaclinical.patientset.client;

/*-
 * #%L
 * Eureka! Clinical User Agreement Client
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

import java.io.InputStream;
import java.net.URI;
import org.eurekaclinical.common.comm.clients.ClientException;
import org.eurekaclinical.common.comm.clients.EurekaClinicalClient;
import org.eurekaclinical.patientset.client.comm.PatientSet;

/**
 * EurekaClinicalPatientSetClient is a client for use by external applications
 * 
 * @author Andrew Post
 */
public final class EurekaClinicalPatientSetClient extends EurekaClinicalClient {

    private final String serviceUrl;

    public EurekaClinicalPatientSetClient(String inServiceUrl) {
        super(null);
        this.serviceUrl = inServiceUrl;
    }

    @Override
    protected String getResourceUrl() {
        return this.serviceUrl;
    }
    
    public Long post(PatientSet patientSet) throws ClientException {
        URI uri = doPostCreate("/api/protected/patientsets", patientSet);
        return extractId(uri);
    }
    
    public Long postStreaming(InputStream patientSetInputStream) throws ClientException {
        URI uri = doPostCreate("/api/protected/patientsets", patientSetInputStream);
        return extractId(uri);
    }
    
}
