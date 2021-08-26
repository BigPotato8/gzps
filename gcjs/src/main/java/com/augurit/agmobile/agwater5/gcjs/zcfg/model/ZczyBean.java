package com.augurit.agmobile.agwater5.gcjs.zcfg.model;

import java.util.List;

public class ZczyBean {

    /**
     * dirName : 操作指引
     * dirId : 89b45a1e-33db-4481-9559-4b5d934084b3
     * children : [{"id":"2c9cca64-4268-4ca3-bc55-2cd35b11d40d","name":"操作手册"},{"id":"e3805c0a-d152-44a8-b9e5-eb66966cbdaa","name":"其他手册"}]
     */

    private String dirName;
    private String dirId;
    private List<ChildrenBean> children;

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public String getDirId() {
        return dirId;
    }

    public void setDirId(String dirId) {
        this.dirId = dirId;
    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    public static class ChildrenBean {
        /**
         * id : 2c9cca64-4268-4ca3-bc55-2cd35b11d40d
         * name : 操作手册
         */

        private String id;
        private String name;

        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
