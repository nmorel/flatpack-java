# FlatPack m2eclipse connector

This project contains an Eclipse plugin to make the FlatPack Maven build plugins play nicely with m2eclipse. To install, run "mvn package" and copy the resulting jar in `target` into your 'eclipse/dropins` folder and restart Eclipse.  Then, right-click your project, and select Maven > Update Project.

Once m2eclipse 1.1 has rolled out, the lifycycle-mapping-metadata.xml can be moved into the individual maven-plugin projects and this plugin can be retired (unless it gains additional features).
