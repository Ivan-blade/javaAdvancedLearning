package com.Ivan.sqlSession;

import com.Ivan.utils.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author apple
 * @date 2021/4/3 下午1:04
 * @description
 */
public class BoundSql {

    private String sqlText;

    private List<ParameterMapping> parameterMappingList = new ArrayList<>();


    public BoundSql(String sqlText, List<ParameterMapping> parameterMappingList) {
        this.sqlText = sqlText;
        this.parameterMappingList = parameterMappingList;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public List<ParameterMapping> getParameterMappingList() {
        return parameterMappingList;
    }

    public void setParameterMappingList(List<ParameterMapping> parameterMappingList) {
        this.parameterMappingList = parameterMappingList;
    }
}
