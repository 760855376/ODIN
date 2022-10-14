package Baseline.VTree.service.graph;

import Baseline.VTree.domain.VTreeCluster;
import Baseline.VTree.domain.VTreeVariable;
import Baseline.VTree.domain.VTreeVertex;
import Baseline.VTree.service.build.VTreeClusterBuilder;
import Baseline.base.domain.Node;
import Baseline.base.domain.enumeration.IndexType;
import Baseline.base.service.api.IVariableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * TODO
 * 2022/9/14 zhoutao
 */
@Slf4j
@Service
public class VTreeVariableService implements IVariableService {

    public VTreeVariableService() {
        register();
    }

    @Override
    public void buildVertex(int vertexName, String clusterName) {
        if (!VTreeVariable.INSTANCE.containsClusterKey(clusterName)) {
            VTreeVariable.INSTANCE.addCluster(clusterName, VTreeClusterBuilder.build(clusterName, true));
        }

        VTreeVertex vertex = new VTreeVertex();
        vertex.setName(vertexName);
        vertex.setClusterName(clusterName);
        VTreeVariable.INSTANCE.addVertex(vertex);
        VTreeVariable.INSTANCE.getCluster(clusterName).addVertex(vertexName);
    }

    @Override
    public void buildEdge(int vertexName, String[] edgeInfo) {
        VTreeVertex vertex = VTreeVariable.INSTANCE.getVertex(vertexName);
        String clusterName = vertex.getClusterName();
        VTreeCluster cluster = VTreeVariable.INSTANCE.getCluster(clusterName);

        for (int j = 0; j < edgeInfo.length; j += 2) {
            int neighbor = Integer.parseInt(edgeInfo[j]), dis = Integer.parseInt(edgeInfo[j + 1]);

            if (neighbor < vertexName) continue;

            VTreeVertex neighborVertex = VTreeVariable.INSTANCE.getVertex(neighbor);

            // add Origion Edge
            vertex.addOrigionEdge(new Node(neighbor, dis));
            neighborVertex.addOrigionEdge(new Node(vertexName, dis));

            if (clusterName.equals(neighborVertex.getClusterName())) {
                cluster.addClusterLink(vertexName, neighbor, dis);
            } else {
                cluster.addBorderLink(vertexName, neighbor, dis);
                VTreeVariable.INSTANCE.getCluster(neighborVertex.getClusterName())
                        .addBorderLink(neighbor, vertexName, dis);
            }

        }
    }

    @Override
    public IndexType supportType() {
        return IndexType.VTree;
    }
}
