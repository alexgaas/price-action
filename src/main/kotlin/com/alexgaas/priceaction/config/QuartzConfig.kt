package com.alexgaas.priceaction.config

import com.alexgaas.priceaction.domain.schedule.enums.ScheduleInterval
import com.alexgaas.priceaction.schedule.impl.DataJobFifteenMinImpl
import com.alexgaas.priceaction.schedule.impl.DataJobFiveMinImpl
import com.alexgaas.priceaction.schedule.impl.DataJobOneDayImpl
import com.alexgaas.priceaction.schedule.impl.DataJobOneHourImpl
import com.alexgaas.priceaction.schedule.impl.DataJobOneMinImpl
import org.quartz.CronScheduleBuilder
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QuartzConfig {
    // 1 min
    @Bean
    fun marketDataJobOneMin(): JobDetail {
        return JobBuilder
            .newJob(DataJobOneMinImpl::class.java)
            .storeDurably()
            .build()
    }

    @Bean
    fun jobOneMin(marketDataJobOneMin: JobDetail?): Trigger {
        return TriggerBuilder.newTrigger()
            .forJob(marketDataJobOneMin)
            .withSchedule(CronScheduleBuilder.cronSchedule(ScheduleInterval.EVERY_MIN.expression))
            .build()
    }

    // 5 mins
    @Bean
    fun marketDataJobFiveMin(): JobDetail {
        return JobBuilder
            .newJob(DataJobFiveMinImpl::class.java)
            .storeDurably()
            .build()
    }

    @Bean
    fun jobFiveMin(marketDataJobFiveMin: JobDetail?): Trigger {
        return TriggerBuilder.newTrigger()
            .forJob(marketDataJobFiveMin)
            .withSchedule(CronScheduleBuilder.cronSchedule(ScheduleInterval.EVERY_FIVE_MIN.expression))
            .build()
    }

    // 15 mins
    @Bean
    fun marketDataJoFifteenMin(): JobDetail {
        return JobBuilder
            .newJob(DataJobFifteenMinImpl::class.java)
            .storeDurably()
            .build()
    }

    @Bean
    fun jobFifteenMin(marketDataJoFifteenMin: JobDetail?): Trigger {
        return TriggerBuilder.newTrigger()
            .forJob(marketDataJoFifteenMin)
            .withSchedule(CronScheduleBuilder.cronSchedule(ScheduleInterval.EVERY_FIFTEEN_MIN.expression))
            .build()
    }

    // 1 hour
    @Bean
    fun marketDataJobOneHour(): JobDetail {
        return JobBuilder
            .newJob(DataJobOneHourImpl::class.java)
            .storeDurably()
            .build()
    }

    @Bean
    fun jobOneHour(marketDataJobOneHour: JobDetail?): Trigger {
        return TriggerBuilder.newTrigger()
            .forJob(marketDataJobOneHour)
            .withSchedule(CronScheduleBuilder.cronSchedule(ScheduleInterval.EVERY_HOUR.expression))
            .build()
    }

    // 1 day
    @Bean
    fun marketDataJobOneDay(): JobDetail {
        return JobBuilder
            .newJob(DataJobOneDayImpl::class.java)
            .storeDurably()
            .build()
    }

    @Bean
    fun jobOneDay(marketDataJobOneDay: JobDetail?): Trigger {
        return TriggerBuilder.newTrigger()
            .forJob(marketDataJobOneDay)
            .withSchedule(CronScheduleBuilder.cronSchedule(ScheduleInterval.EVERY_DAY.expression))
            .build()
    }
}
