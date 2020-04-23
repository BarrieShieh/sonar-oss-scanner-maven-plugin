/*
 * Copyright 2008-2013 Don Brown
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.twdata.maven.mojoexecutor.plugin;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.maven.cli.logging.Slf4jConfiguration;
import org.apache.maven.cli.logging.Slf4jConfigurationFactory;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.*;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.Xpp3DomBuilder;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import static org.twdata.maven.mojoexecutor.MojoExecutor.executeMojo;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executionEnvironment;

/**
 * Execute a Mojo using the MojoExecutor.
 */
@SuppressWarnings("unused")
@Mojo(name = "scan", defaultPhase = LifecyclePhase.TEST, requiresDependencyResolution = ResolutionScope.TEST)
public class MojoExecutorMojo extends AbstractMojo {
//    /**
//     * Plugin to execute.
//     */
//    @Parameter(required = true)
//    private Plugin plugin;
//
//    /**
//     * Plugin goal to execute.
//     */
//    @Parameter(required = true)
//    private String goal;
//
//    /**
//     * Plugin configuration to use in the execution.
//     */
//    @Parameter
//    private XmlPlexusConfiguration configuration;

    /**
     * The project currently being build.
     */
    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject mavenProject;

    /**
     * The current Maven session.
     */
    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession mavenSession;

    /**
     * The Maven BuildPluginManager component.
     */
    @Component
    private BuildPluginManager pluginManager;

    /**
     * Disable logging on executed mojos
     */
    @Parameter(defaultValue = "false")
    private boolean quiet;

    /**
     * Ignore injected maven projetc
     */
    @Parameter(defaultValue = "false")
    private boolean ignoreMavenProject;

    public void execute() throws MojoExecutionException {
        try {
            if (quiet) {
                disableLogging();
            }

            getLog().info("Executing with maven project " + mavenProject + " for session " + mavenSession);

            InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("plugins.xml");
            Reader reader = new InputStreamReader(input);
            Xpp3Dom configurationDom = Xpp3DomBuilder.build(reader);
            Xpp3Dom pluginsDom = configurationDom.getChild("plugins");
            Xpp3Dom[] pluginDomList = pluginsDom.getChildren();
            for (Xpp3Dom pluginDom : pluginDomList) {
                Xpp3Dom goalDom = pluginDom.getChild("goal");
                Xpp3Dom pluginDetailDom = pluginDom.getChild("plugin");
                Xpp3Dom pluginConfigurationDom = pluginDom.getChild("configuration");

                getLog().debug(goalDom.getValue());
                getLog().debug(pluginDetailDom.toString());
                getLog().debug(pluginConfigurationDom.toString());

                XmlMapper xmlMapper = new XmlMapper();
                xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                Plugin plugin = xmlMapper.readValue(pluginDetailDom.toString(), Plugin.class);

                executeMojo(plugin, goalDom.getValue(), pluginConfigurationDom,
                        (ignoreMavenProject ?
                                executionEnvironment(mavenSession, pluginManager) :
                                executionEnvironment(mavenProject, mavenSession, pluginManager)));

            }
            getLog().info("     [echo] Mojo Executor ran successfully.");
        } catch (Exception e) {
            throw new MojoExecutionException("Execute plugin failed", e);
        }
    }

    private void disableLogging() throws MojoExecutionException {
        // Maven < 3.1
        Logger logger;
        try {
            logger = (Logger) FieldUtils.readField(getLog(), "logger", true);
        } catch (IllegalAccessException e) {
            throw new MojoExecutionException("Unable to access logger field ", e);
        }
        logger.setThreshold(5);

        // Maven >= 3.1
        ILoggerFactory slf4jLoggerFactory = LoggerFactory.getILoggerFactory();
        Slf4jConfiguration slf4jConfiguration = Slf4jConfigurationFactory.getConfiguration(slf4jLoggerFactory);
        slf4jConfiguration.setRootLoggerLevel(Slf4jConfiguration.Level.ERROR);
        slf4jConfiguration.activate();
    }
}
