package Baseline.VTree.domain;

import Baseline.base.domain.api.Variable;
import Baseline.base.service.dto.IndexDTO;
import lombok.Getter;

/**
 * TODO
 * 2022/9/14 zhoutao
 */
@Getter
public class VTreeVariable extends Variable<VTreeVertex, VTreeCluster> {
    public static final VTreeVariable INSTANCE = new VTreeVariable();

    private VTreeVariable() {
    }

    @Override
    public void initVariables(IndexDTO indexDTO) {
        super.initVariables();
    }
}
