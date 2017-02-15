package com.dbserver.voting.jobs;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dbserver.voting.services.VoteService;

@Component
public class LunchTimeJob {
	private static final Logger logger = LoggerFactory.getLogger(LunchTimeJob.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");

    @Autowired
    VoteService voteService;

    // Scheduling voting each day at 11:30 AM, change cron to execute time tests
    @Scheduled(cron="0 30 11 * * MON-FRI") // second, minute, hour, day, month, weekday
    public void reportCurrentTime() {
    	logger.info("The time is now {}. Running LunchTimeJob.", dateFormat.format(new Date(Calendar.getInstance().getTimeInMillis())));
        voteService.executeVoting(new Date(Calendar.getInstance().getTimeInMillis()));
    }
}
