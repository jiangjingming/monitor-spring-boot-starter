package cn.jjm.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author jiangjingming
 * @date 2017/11/4
 */
@Configuration
@EnableElasticsearchRepositories("cn.jjm")
public class ElasticSearchConfig {

    @Value("${elasticsearch-host}")
    private String elasticsearchHost;

    @Value("${elasticsearch-port}")
    private Integer elasticsearchPort;
    @Value("${elasticsearch-cluster-name}")
    private String clusterName;

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(client());
    }

    @Bean
    public Client client() {
        Settings settings = Settings.builder()
                .put("cluster.name", clusterName).build();
        TransportClient client = TransportClient.builder().settings(settings).build();
        TransportAddress address = null;
        try {
            address = new InetSocketTransportAddress(InetAddress.getByName(elasticsearchHost), elasticsearchPort);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        client.addTransportAddress(address);
        return client;
    }
}
