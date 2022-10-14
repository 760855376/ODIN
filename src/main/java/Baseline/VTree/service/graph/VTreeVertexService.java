package Baseline.VTree.service.graph;


import Baseline.VTree.domain.VTreeVariable;
import Baseline.VTree.domain.VTreeVertex;
import Baseline.base.domain.Node;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * TODO
 * 2022/9/15 zhoutao
 */
@Service
public class VTreeVertexService {

    /**
     * build borders
     */
    public void buildBorders() {
        VTreeVariable.INSTANCE.getVertices().parallelStream().forEach(this::buildBorder);
    }

    /**
     * build border for vertex
     */
    private void buildBorder(VTreeVertex vertex) {
        String[] clusterNames = vertex.getClusterNames();
        int minIndex = clusterNames.length;
        // traverse neighbor nodes
        for (Node node : vertex.getOrigionEdges()) {
            VTreeVertex neighborVertex = VTreeVariable.INSTANCE.getVertex(node.getName());
            String[] neighborClusterNames = neighborVertex.getClusterNames();

//          Determine whether the i -th layer is a boundary point
            for (int i = 0; i < minIndex; i++) {
                if (!clusterNames[i].equals(neighborClusterNames[i])) {
                    minIndex = i;
                    break;
                }
            }
        }
        Arrays.fill(vertex.getBorder(), minIndex, clusterNames.length, true);
    }
}
