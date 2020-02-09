package com.chl.sys.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: badbad
 * @Date: 2019/9/22 19:26
 * @Version 1.0
 */
public class TreeNodeBuilder {

    /**
     * 构建层级关系重新组装树
     * @param baseNodes
     * @return
     */
    public  static List<TreeNode> bulidTree(List<TreeNode> baseNodes, Integer basePid){
        List<TreeNode> rtnNodes = new ArrayList<>();
        for (TreeNode n1 : baseNodes) {
            /*如果父id是1就装进去*/
            if (n1.getPid() == basePid){
                rtnNodes.add(n1);
                /*再遍历全部节点,看其他节点有没有是这个节点的字节点，如果有，就放进他的字节点集合*/
                for (TreeNode n2 : baseNodes) {
                    if (n2.getPid() == n1.getId()){
                        n1.getChildren().add(n2);
                    }
                }
            }
        }

        return rtnNodes;
    }
}
