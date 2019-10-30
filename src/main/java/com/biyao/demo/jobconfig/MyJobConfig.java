package com.biyao.demo.jobconfig;

import com.biyao.demo.job.MySimpleJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Created by jk
 * Create Date: 2019/10/30 11:20
 * Description:
 */
@Configuration
public class MyJobConfig {

    private final String cron = "0/5 * * * * ?";
    private final int shardingTotalCount = 4;
    private final String shardingItemParameters = "0=A,1=B,2=C,3=D";
    private final String jobParameters = "parameter";

    @Resource
    private ZookeeperRegistryCenter regCenter;

    @Bean
    public SimpleJob stockJob() {
        return new MySimpleJob();
    }

    @Bean(initMethod = "init")
    public JobScheduler simpleJobScheduler(final SimpleJob simpleJob) {
        return new SpringJobScheduler(simpleJob, regCenter, getLiteJobConfiguration(simpleJob.getClass(),
                cron, shardingTotalCount, shardingItemParameters, jobParameters));
    }

    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass,
                                                         final String cron,
                                                         final int shardingTotalCount,
                                                         final String shardingItemParameters,
                                                         final String jobParameters) {
        // 定义作业核心配置
        JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount).
                shardingItemParameters(shardingItemParameters).jobParameter(jobParameters).build();
        // 定义SIMPLE类型配置
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(simpleCoreConfig, jobClass.getCanonicalName());
        // 定义Lite作业根配置
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfig).overwrite(true).build();
        return simpleJobRootConfig;

    }
}
