package com.eps.mdp.project.dp.utils;

import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;
/**
 * Created by tomaszdabek on 30.05.2017.
 */
public class JobRunner implements Runnable {
    private JobControl control;

    public JobRunner(JobControl _control) {
        this.control = _control;
    }

    public void run() {
        this.control.run();
    }
}
