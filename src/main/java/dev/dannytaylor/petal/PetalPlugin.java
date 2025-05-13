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
import org.gradle.api.tasks.TaskProvider;

import java.io.File;

public class PetalPlugin implements Plugin<Project> {
	public void apply(Project project) {
		Configuration sporeConfig = project.getConfigurations().maybeCreate("spore");
		sporeConfig.setCanBeConsumed(false);
		sporeConfig.setCanBeResolved(true);

		TaskProvider<JavaExec> copyTask = project.getTasks().register("copy", JavaExec.class, task -> {
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

		TaskProvider<JavaExec> runServerTask = project.getTasks().register("runServer", JavaExec.class, task -> {
			task.setGroup("petal");
			task.setDescription("Starts the 'server' run configuration.");
			task.getMainClass().set("dev.dannytaylor.spore.SporeMain");
			task.setClasspath(sporeConfig);
			task.setWorkingDir(project.file("run"));
			task.dependsOn(copyTask);
		});
	}
}
