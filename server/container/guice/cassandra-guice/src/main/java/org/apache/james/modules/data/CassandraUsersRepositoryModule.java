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
package org.apache.james.modules.data;

import org.apache.james.backends.cassandra.components.CassandraModule;
import org.apache.james.user.api.UsersRepository;
import org.apache.james.user.cassandra.CassandraUsersRepository;
import org.apache.james.utils.ConfigurationPerformer;
import org.apache.james.utils.ConfigurationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;

public class CassandraUsersRepositoryModule extends AbstractModule {

    private static final Logger LOGGER = LoggerFactory.getLogger(CassandraUsersRepositoryModule.class);

    @Override
    public void configure() {
        bind(CassandraUsersRepository.class).in(Scopes.SINGLETON);
        bind(UsersRepository.class).to(CassandraUsersRepository.class);
        Multibinder<CassandraModule> cassandraDataDefinitions = Multibinder.newSetBinder(binder(), CassandraModule.class);
        cassandraDataDefinitions.addBinding().to(org.apache.james.user.cassandra.CassandraUsersRepositoryModule.class);

        Multibinder.newSetBinder(binder(), ConfigurationPerformer.class).addBinding().to(CassandraUsersRepositoryConfigurationPerformer.class);
    }

    @Singleton
    public static class CassandraUsersRepositoryConfigurationPerformer implements ConfigurationPerformer {

        private final ConfigurationProvider configurationProvider;
        private final CassandraUsersRepository usersRepository;

        @Inject
        public CassandraUsersRepositoryConfigurationPerformer(ConfigurationProvider configurationProvider, CassandraUsersRepository usersRepository) {
            this.configurationProvider = configurationProvider;
            this.usersRepository = usersRepository;
        }

        @Override
        public void initModule() throws Exception {
            usersRepository.setLog(LOGGER);
            usersRepository.configure(configurationProvider.getConfiguration("usersrepository"));
        }
    }

}