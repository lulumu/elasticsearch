/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.index.shard;

import org.elasticsearch.common.inject.AbstractModule;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.engine.EngineFactory;
import org.elasticsearch.index.engine.InternalEngineFactory;
import org.elasticsearch.index.warmer.ShardIndexWarmerService;

/**
 *
 */
public class IndexShardModule extends AbstractModule {

    public static final String ENGINE_FACTORY = "index.engine.factory";
    private static final Class<? extends EngineFactory> DEFAULT_ENGINE_FACTORY_CLASS = InternalEngineFactory.class;

    private final ShardId shardId;
    private final Settings settings;

    public IndexShardModule(ShardId shardId, Settings settings) {
        this.settings = settings;
        this.shardId = shardId;
    }

    @Override
    protected void configure() {
        bind(ShardId.class).toInstance(shardId);
        bind(IndexShard.class).asEagerSingleton();
        bind(EngineFactory.class).to(settings.getAsClass(ENGINE_FACTORY, DEFAULT_ENGINE_FACTORY_CLASS,
                "org.elasticsearch.index.engine.", "EngineFactory"));
        bind(ShardIndexWarmerService.class).asEagerSingleton();
    }

}