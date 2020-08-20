package com.shen.mybatis.generator;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.toolkit.StringUtils;

import java.util.Scanner;

public class MybatisPlusGenerator {
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入一下你的" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath= System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setOpen(false);
//        gc.setSwagger2(true);
        gc.setBaseResultMap(true);
        gc.setIdType(IdType.ID_WORKER); //自增
        String model = scanner("实体类名");
        gc.setControllerName(model+"Controller");
        gc.setServiceName(model+"Service");
        gc.setServiceImplName(model+"ServiceImpl");
        gc.setMapperName(model+"Mapper");
        gc.setXmlName(model+"Mapper");
        gc.setAuthor("shenfuding");
//        gc.setDateType(DateType.ONLY_DATE);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://rm-echatwz95khhk40wn2y0k6uo.mysql.rds.aliyuncs.com/echat_database_dev?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowMultiQueries=true");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("echat");
        dsc.setPassword("echat^_^online123");
        mpg.setDataSource(dsc);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));
        pc.setParent("com.shen")
                .setMapper("mapper")
                .setService("service")
                .setController("controller")
                .setEntity("model")
                .setXml("mapper");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 表名生成策略(下划线转驼峰命名)
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 列名生成策略(下划线转驼峰命名)
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 是否启动Lombok配置
        strategy.setEntityLombokModel(true);
        // 是否启动REST风格配置
        strategy.setRestControllerStyle(true);
        // 自定义实体父类
//        strategy.setSuperEntityClass("com.mmj.common.model.BaseModel");
        // 自定义controller父类
//        strategy.setSuperControllerClass("com.mmj.common.controller.BaseController");
        // 自定义service父接口
        strategy.setSuperServiceClass("com.baomidou.mybatisplus.service.IService");
        // 自定义service实现类
        strategy.setSuperServiceImplClass("com.baomidou.mybatisplus.service.impl.ServiceImpl");
        // 自定义mapper接口
        strategy.setSuperMapperClass("com.baomidou.mybatisplus.mapper.BaseMapper");
        strategy.setInclude(scanner("表名"));
        strategy.setTablePrefix("t_");
//        strategy.setSuperEntityColumns(new String[] {"createrId", "createrTime", "modifyId", "modifyTime"});
        mpg.setStrategy(strategy);

        // 执行
        mpg.execute();
    }
}
