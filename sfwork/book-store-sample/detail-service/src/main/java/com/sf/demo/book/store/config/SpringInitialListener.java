package com.sf.demo.book.store.config;

import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientAssignConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfigManager;
import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterFlowRuleManager;
import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterParamFlowRuleManager;
import com.alibaba.csp.sentinel.cluster.server.config.ClusterServerConfigManager;
import com.alibaba.csp.sentinel.cluster.server.config.ServerTransportConfig;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.apollo.ApolloDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.transport.config.TransportConfig;
import com.alibaba.csp.sentinel.util.HostNameUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.sf.demo.book.store.dto.ClusterGroupEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class SpringInitialListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ApolloStaticValue apolloStaticValue;

    private String apolloNamespace;
    private String flowRuleKey;
    private String configDataId;
    private String clusterMapDataId;

    @PostConstruct
    public void init(){
        apolloNamespace = apolloStaticValue.getNamespace();
        flowRuleKey = apolloStaticValue.getApolloRuleKey();
        configDataId = apolloStaticValue.getAppName() + "-cluster-client-config";
        clusterMapDataId = apolloStaticValue.getAppName() + DemoConstants.CLUSTER_MAP_POSTFIX;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //容器初始化完毕

        loadAndListenRules();
    }

    /**
     * 从apollo监听限流规则
     */
    public void loadAndListenRules() {

        log.info("start apollo config...");
        log.info("current appId :[{}],meta url :[{}],namespace :[{}],rulesKey :[{}]", System.getProperty("app.id"), System.getProperty("apollo.meta"), apolloNamespace, flowRuleKey);

        //单机限流规则
        initSingleProperty();

        //嵌入模式cluster
        //token client
        initClientConfigProperty();
        initClientServerAssignProperty();

        //token server
        initServerTransportConfigProperty();
        registerClusterRuleSupplier();
        initStateProperty();
    }

    private void initSingleProperty(){
        // It's better to provide a meaningful default value.
        String defaultFlowRules = "[]";

        //单机限流规则
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new ApolloDataSource<>(apolloNamespace,
                flowRuleKey, defaultFlowRules, source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
        }));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
    }


    private void initClientConfigProperty() {
        //集群客户端通信配置
        ReadableDataSource<String, ClusterClientConfig> clientConfigDs2 = new ApolloDataSource<>(apolloNamespace,
                configDataId, null, source -> JSON.parseObject(source, new TypeReference<ClusterClientConfig>() {
        }));
        ClusterClientConfigManager.registerClientConfigProperty(clientConfigDs2.getProperty());
    }

    private void initClientServerAssignProperty() {
        //集群客户端分配配置

        // Cluster map format:
        // [{"clientSet":["112.12.88.66@8729","112.12.88.67@8727"],"ip":"112.12.88.68","machineId":"112.12.88.68@8728","port":11111}]
        // machineId: <ip@commandPort>, commandPort for port exposed to Sentinel dashboard (transport module)
        ReadableDataSource<String, ClusterClientAssignConfig> clientAssignDs = new ApolloDataSource<>(apolloNamespace, clusterMapDataId,
                null, source -> {
            List<ClusterGroupEntity> groupList = JSON.parseObject(source, new TypeReference<List<ClusterGroupEntity>>() {
            });
            return Optional.ofNullable(groupList)
                    .flatMap(this::extractClientAssignment)
                    .orElse(null);
        });
        ClusterClientConfigManager.registerServerAssignProperty(clientAssignDs.getProperty());
    }


    //token server
    private void initServerTransportConfigProperty() {
        //集群server 配置 meta
        ReadableDataSource<String, ServerTransportConfig> serverTransportDs = new ApolloDataSource<>(apolloNamespace, clusterMapDataId,
                null, source -> {
            List<ClusterGroupEntity> groupList = JSON.parseObject(source, new TypeReference<List<ClusterGroupEntity>>() {
            });
            return Optional.ofNullable(groupList)
                    .flatMap(this::extractServerTransportConfig)
                    .orElse(null);
        });
        ClusterServerConfigManager.registerServerTransportProperty(serverTransportDs.getProperty());
    }

    private void registerClusterRuleSupplier() {
        // Register cluster flow rule property supplier which creates data source by namespace.
        // Flow rule dataId format: ${namespace}-flow-rules
        ClusterFlowRuleManager.setPropertySupplier(namespace -> {
            ReadableDataSource<String, List<FlowRule>> ds = new ApolloDataSource<>(apolloNamespace, namespace + DemoConstants.FLOW_POSTFIX,
                    null, source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
            }));
            return ds.getProperty();
        });
        // Register cluster parameter flow rule property supplier which creates data source by namespace.
        ClusterParamFlowRuleManager.setPropertySupplier(namespace -> {
            ReadableDataSource<String, List<ParamFlowRule>> ds = new ApolloDataSource<>(apolloNamespace, namespace + DemoConstants.PARAM_FLOW_POSTFIX,
                    null, source -> JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {
            }));
            return ds.getProperty();
        });
    }

    private void initStateProperty() {
        // Cluster map format:
        // [{"clientSet":["112.12.88.66@8729","112.12.88.67@8727"],"ip":"112.12.88.68","machineId":"112.12.88.68@8728","port":11111}]
        // machineId: <ip@commandPort>, commandPort for port exposed to Sentinel dashboard (transport module)
        ReadableDataSource<String, Integer> clusterModeDs = new ApolloDataSource<>(apolloNamespace, clusterMapDataId,
                null, source -> {
            List<ClusterGroupEntity> groupList = JSON.parseObject(source, new TypeReference<List<ClusterGroupEntity>>() {
            });
            return Optional.ofNullable(groupList)
                    .map(this::extractMode)
                    .orElse(ClusterStateManager.CLUSTER_NOT_STARTED);
        });
        ClusterStateManager.registerProperty(clusterModeDs.getProperty());
    }

    private int extractMode(List<ClusterGroupEntity> groupList) {
        // If any server group machineId matches current, then it's token server.
        if (groupList.stream().anyMatch(this::machineEqual)) {
            return ClusterStateManager.CLUSTER_SERVER;
        }
        // If current machine belongs to any of the token server group, then it's token client.
        // Otherwise it's unassigned, should be set to NOT_STARTED.
        boolean canBeClient = groupList.stream()
                .flatMap(e -> e.getClientSet().stream())
                .filter(Objects::nonNull)
                .anyMatch(e -> e.equals(getCurrentMachineId()));
        return canBeClient ? ClusterStateManager.CLUSTER_CLIENT : ClusterStateManager.CLUSTER_NOT_STARTED;
    }

    private Optional<ServerTransportConfig> extractServerTransportConfig(List<ClusterGroupEntity> groupList) {
        return groupList.stream()
                .filter(this::machineEqual)
                .findAny()
                .map(e -> new ServerTransportConfig().setPort(e.getPort()).setIdleSeconds(600));
    }

    private Optional<ClusterClientAssignConfig> extractClientAssignment(List<ClusterGroupEntity> groupList) {
        if (groupList.stream().anyMatch(this::machineEqual)) {
            return Optional.empty();
        }
        // Build client assign config from the client set of target server group.
        for (ClusterGroupEntity group : groupList) {
            if (group.getClientSet().contains(getCurrentMachineId())) {
                String ip = group.getIp();
                Integer port = group.getPort();
                return Optional.of(new ClusterClientAssignConfig(ip, port));
            }
        }
        return Optional.empty();
    }

    private boolean machineEqual(/*@Valid*/ ClusterGroupEntity group) {
        return getCurrentMachineId().equals(group.getMachineId());
    }

    private String getCurrentMachineId() {
        // Note: this may not work well for container-based env.
        return HostNameUtil.getIp() + SEPARATOR + TransportConfig.getRuntimePort();
    }

    private static final String SEPARATOR = "@";
}
