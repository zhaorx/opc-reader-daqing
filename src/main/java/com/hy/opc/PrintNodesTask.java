package com.hy.opc;


import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;

@Profile({"dev"})
@Component
public class PrintNodesTask {
    private static final Logger logger = LoggerFactory.getLogger(PrintNodesTask.class);
    @Value("${opc-url}")
    private String opcUrl;

    private String sep = "_";
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private OpcConnector connector;

    private OpcUaClient opcUaClient;

    @Scheduled(fixedDelayString = "10000000")
    public void transferSchedule() throws Exception {
        logger.info("printNodeTree start #########################################");
        this.printNodeTree();
        logger.info("printNodeTree end ###########################################");
    }

    public void printNodeTree() throws Exception {

        //创建OPC UA客户端
        if (opcUaClient == null) {
            opcUaClient = connector.createClient(opcUrl);
            //开启连接
            opcUaClient.connect().get();
        }

        connector.browseNode(opcUaClient, null);
    }

}
