package com.menethil.tinymall.core.notify;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步线程池,用于异步发送通知
 */
@Configuration
@EnableScheduling
@EnableAsync
class ExecutorConfig {

    private int corePoolSize = 5;
    private int maxPoolSize = 100;
    private int queueCapacity = 50;

    @Bean(name = "notifyAsync")
    public Executor notifyAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("NotifyExecutor-");
        executor.initialize();
        return executor;
    }
}