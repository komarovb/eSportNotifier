package com.komarov.patel.research.methodology.esportservice.scheduler;
import com.komarov.patel.research.methodology.esportservice.service.NotificationsService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationsCheckJob implements Job {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NotificationsService jobService;

    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("Job - {} - fired {}", context.getJobDetail().getKey().getName(), context.getFireTime());

        jobService.checkAllNotifications();

        logger.info("Next job scheduled for {}", context.getNextFireTime());
    }
}
