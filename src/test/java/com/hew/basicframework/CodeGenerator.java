package com.hew.basicframework;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HeXiaoWei
 * @date 2020/11/8 22:03
 */
public class CodeGenerator {
    public static void main(String[] args) {
        tableToCodeGenerator("HeXiaoWei", "users");
    }

    public static void tableToCodeGenerator(String author, String... tables) {
        //构建一个代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setAuthor(author);
        gc.setOutputDir(projectPath + "/src/main/java");
        //是否覆盖文件默认为false
//        gc.setFileOverride(false);
        gc.setOpen(false);
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

        //数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setUrl("jdbc:oracle:thin:@localhost:1521/dtcentapp");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("uif_ndig");
        dsc.setPassword("Cross!()20183");
        mpg.setDataSource(dsc);

        //包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.bonc.gdltnetwork");
        pc.setEntity("DO");
        mpg.setPackageInfo(pc);

        //自定义配置输出xml位置
        InjectionConfig ic = new InjectionConfig() {
            @Override
            public void initMap() {
                //to do nothing
            }
        };
        List<FileOutConfig> foc = new ArrayList<>();
        String templatePath = "/templates/mapper.xml.vm";
        foc.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String moduleName = StringUtils.isNotEmpty(pc.getModuleName()) ? pc.getModuleName() : "";
                return projectPath + "/src/main/resources/mapper" + moduleName +
                        "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        ic.setFileOutConfigList(foc);
        mpg.setCfg(ic);

        //配置模板
        TemplateConfig tc = new TemplateConfig();
        //控制不生成那个对应文件,则把参数置为"",要生成那个文件则不动默认配置
        tc.setController("");
        tc.setService("");
        tc.setServiceImpl("");
//        tc.setMapper("");
//        tc.setEntityKt("");
//        tc.setEntity("");
        //xml输出由【自定义配置】输出xml
        tc.setXml("");
        mpg.setTemplate(tc);

        //策略配置
        StrategyConfig sc = new StrategyConfig();
        sc.setInclude(tables);
        sc.setNaming(NamingStrategy.underline_to_camel);
        sc.setColumnNaming(NamingStrategy.underline_to_camel);
        sc.setEntityLombokModel(true);
        sc.setRestControllerStyle(true);
        sc.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(sc);

        mpg.execute();
    }
}
