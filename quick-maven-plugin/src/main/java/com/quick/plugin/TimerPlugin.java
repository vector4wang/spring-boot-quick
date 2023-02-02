package com.quick.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Mojo(name = "TimerPlugin")
public class TimerPlugin extends AbstractMojo {

    @Parameter(property = "timer.username",defaultValue = "vector")
    private String userName;

    @Parameter(property = "timer.status",defaultValue = "nice")
    private String status;



    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        getLog().info("timer plugin is running，current time is " + currentTime);
        getLog().info(String.format("hi %s ! Now you are %s",userName,status));
    }
}
