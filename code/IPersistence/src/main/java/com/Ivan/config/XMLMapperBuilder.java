package com.Ivan.config;

import com.Ivan.pojo.Configuration;
import com.Ivan.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author apple
 * @date 2021/4/2 下午12:31
 * @description
 */
public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream inputStream) throws DocumentException {

        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");

        //
        List<List<Element>> all = new ArrayList<>();
        all.add(rootElement.selectNodes("//select"));
        all.add(rootElement.selectNodes("//insert"));
        all.add(rootElement.selectNodes("//update"));
        all.add(rootElement.selectNodes("//delete"));

        for (List<Element> elements : all) {
            for (Element element : elements) {
                String id = element.attributeValue("id");
                String resultType = element.attributeValue("resultType");
                String parameterType = element.attributeValue("parameterType");
                String sqlText = element.getTextTrim();

                MappedStatement mappedStatement = new MappedStatement();
                mappedStatement.setId(id);
                mappedStatement.setResultType(resultType);
                mappedStatement.setParameterType(parameterType);
                mappedStatement.setSql(sqlText);

                String key = namespace+"."+id;
                configuration.getMappedStatementMap().put(key,mappedStatement);
            }
        }

    }
}
