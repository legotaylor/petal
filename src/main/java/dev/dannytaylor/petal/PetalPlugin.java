/*
    Petal
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/petal
    Licence: LGPL-3.0-or-later
*/

package dev.dannytaylor.petal;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.tasks.JavaExec;

import java.io.File;

/**
 * A Gradle plugin that adds tasks for building and running Spore addons.
 */
public class PetalPlugin implements Plugin<Project> {
	/**
	 * Adds 'copy' and 'runServer' tasks.
	 */
	public PetalPlugin() {
	}
	public void apply(Project project) {
		Configuration sporeConfig = project.getConfigurations().maybeCreate("spore");
		sporeConfig.setCanBeConsumed(false);
		sporeConfig.setCanBeResolved(true);

		project.getConfigurations().getByName("implementation").extendsFrom(sporeConfig);

		var copyTask = project.getTasks().register("copy", task -> {
			task.setGroup("petal");
			task.setDescription("Assembles and copies the project to the 'run/addons' directory.");
			task.dependsOn("jar");
			task.doLast(t -> {
				File jar = project.getTasks().getByName("jar").getOutputs().getFiles().getSingleFile();
				File runMods = project.file("run/addons");
				runMods.mkdirs();
				project.copy(copySpec -> {
					copySpec.from(jar);
					copySpec.into(runMods);
				});
			});
		});

		var runServerTask = project.getTasks().register("runServer", JavaExec.class, task -> {
			task.setGroup("petal");
			task.setDescription("Starts the 'server' run configuration.");
			task.getMainClass().set("dev.dannytaylor.spore.SporeMain");
			task.setClasspath(sporeConfig);
			task.setWorkingDir(project.file("run"));
			task.dependsOn(copyTask);
		});
	}
}
