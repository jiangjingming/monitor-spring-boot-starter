/*
 * Copyright 2012-2013 the original author or authors.
 *
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
 */

package cn.jjm.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author jiangjingming
 */
public interface AccessMonitorRepository extends ElasticsearchRepository<AccessMonitor, String> {


    /**
     * 查找accessMonitor对象
     * @param methodSignature
     * @param methodName
     * @param accessDate
     * @return
     */
    List<AccessMonitor> findByMethodSignatureAndMethodNameAndAccessDate(String methodSignature,String methodName,String accessDate);

    /**
     * 查询
     * @param methodName
     * @return
     */
    List<AccessMonitor> findByMethodName(String methodName);
}