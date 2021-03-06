/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/

package org.apache.mailet.base;

import javax.mail.internet.AddressException;

import org.apache.mailet.MailAddress;

import com.google.common.base.Throwables;

public class MailAddressFixture {


    public static final String JAMES_APACHE_ORG = "james.apache.org";
    public static final String JAMES2_APACHE_ORG = "james2.apache.org";

    public static final MailAddress ANY_AT_JAMES = createMailAddress("any@" + JAMES_APACHE_ORG);
    public static final MailAddress OTHER_AT_JAMES = createMailAddress("other@" + JAMES_APACHE_ORG);
    public static final MailAddress ANY_AT_JAMES2 = createMailAddress("any@" + JAMES2_APACHE_ORG);
    public static final MailAddress OTHER_AT_JAMES2 = createMailAddress("other@" + JAMES2_APACHE_ORG);

    private static MailAddress createMailAddress(String mailAddress) {
        try {
            return new MailAddress(mailAddress);
        } catch (AddressException e) {
            throw Throwables.propagate(e);
        }
    }
}
