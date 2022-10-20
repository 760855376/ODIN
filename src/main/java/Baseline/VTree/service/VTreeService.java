package Baseline.VTree.service;

import Baseline.VTree.domain.VTreekNN;
import Baseline.base.domain.GlobalVariable;
import Baseline.base.domain.enumeration.IndexType;
import Baseline.base.service.api.IKnnService;
import Baseline.base.service.dto.KnnDTO;
import Baseline.base.service.dto.result.KnnResultDTO;
import Baseline.base.service.utils.DistributionUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * TODO
 * 2022/9/18 zhoutao
 */
@Service
public class VTreeService implements IKnnService {
    VTreekNN knn;

    public VTreeService() {
        register();
    }

    @Override
    public void initKnn(int queryName) {
        knn = new VTreekNN(queryName);
    }

    @Override
    public void knnSearch(int queryName) {
        if(queryName==-1){

            ArrayList<Integer> list1 = new ArrayList<>(DistributionUtil.getRandomVertexList());
            long startKnn = System.nanoTime();
//            list.parallelStream().forEach(integer -> knnService.knnSearch(integer));
            long endKnn=0;
            for (int i:list1){
                initKnn(i);
                long start = System.nanoTime();
                knn.knn();
                long end = (long) ((System.nanoTime() - start) / 1000.0 / GlobalVariable.COMPUTE_NUM);
                endKnn += end;
//                System.out.println(end);
            }
//            long endKnnt= (long) ((System.nanoTime() - startKnn) / 1000.0);
            System.out.println("QueryTime:"+endKnn+"us");
//            knn.setQueryTime(endKnn);
//            System.out.println("querytime1"+endKnn+"us");
//            System.out.println("querytime2"+endKnnt+"us");
        }else {
            initKnn(queryName);
            knn.knn();
        }

    }

    @Override
    public KnnResultDTO buildResult(KnnDTO knnDTO) {
        return buildResult(knn, knnDTO);
    }

    @Override
    public IndexType supportType() {
        return IndexType.VTree;
    }
}
