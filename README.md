Stacksync Overlay Dependencies
=========

These are the additional steps to compìle the project:
  - Install Visual Studio 2010 with SP1
  - Add to the PATH sysvar these paths:
    - C:/Windows/Microsoft.NET/Framework64/v4.0.30319
    - %ANT_HOME%\bin
  - Create a sysvar called ANT_HOME with the path to your Ant installation.
  - Add the following code to the build.xml script:
    ```sh
    <?xml version="1.0"?>
    <project name="liferay-nativity-java" basedir="." default="compile">
    
        <taskdef resource="net/sf/antcontrib/antlib.xml">
    		<classpath>
    			<fileset dir="${basedir}/antlib"/>
    		</classpath>
    	</taskdef>
    	
    	<property name="project.dir" value="." />
    	<property name="project.tmp.dir" value="${project.dir}/tmp" />
    
    	<property name="java.dir" value="${project.dir}/java" />
    
    	<property environment="env" />
    
    	<property file="${project.dir}/build.${user.name}.properties" />
    	<property file="${project.dir}/build.${env.COMPUTERNAME}.properties" />
    	<property file="${project.dir}/build.${env.HOST}.properties" />
    	<property file="${project.dir}/build.${env.HOSTNAME}.properties" />
    	<property file="${project.dir}/build.properties" />
    
    	<path id="portal.classpath">
    		<pathelement location="${portal.dir}/portal-impl/classes" />
    		<pathelement location="${portal.dir}/portal-service/classes" />
    		<pathelement location="${portal.dir}/portal-web/docroot/WEB-INF/classes" />
    		<pathelement location="${portal.dir}/util-java/classes" />
    		<fileset dir="${portal.dir}/lib/development" includes="*.jar" />
    		<fileset dir="${portal.dir}/lib/global" includes="*.jar" />
    		<fileset dir="${portal.dir}/lib/portal" includes="*.jar" />
    	</path>
    
    	<path id="lib.classpath">
    		<fileset dir="${java.dir}/lib" includes="*.jar" />
    	</path>
    
    	<target name="build-jar" depends="compile">
    		<delete dir="${java.dir}/dist" />
  
    		<jar destfile="${project.dir}/dist/liferay-nativity-${package.version}.jar" filesetmanifest="mergewithoutmain">
    			<fileset dir="${java.dir}/classes" />
    			<zipgroupfileset dir="${java.dir}/lib" excludes="META-INF/*.SF" includes="*.jar" />
    		</jar>
    	</target>
    	<target name="build-jni-headers" depends="compile">
    		<javah destdir="${project.dir}/windows/LiferayNativityShellExtensions/LiferayNativityWindowsUtil"                         classpath="${java.dir}/classes" class="com.liferay.nativity.control.win.WindowsNativityUtil"/>
    	</target>
    
    	<target name="build-windows-util" depends="compile">
    		<property name="nativity.dir" value="${nativity.dir}" />
    		<property name="target.os" value="windows" />
    		<property name="ms.sdk.7.1.dir" value="${ms.sdk.7.1.dir}" />
    		<property name="framework.dir" value="${framework.dir}" />
            <if>
                <not>
                    <equals arg1="${target.os}" arg2="windows" />
                </not>
                <then>
                    <fail>
                        Ant target must be run in Windows
                    </fail>
                </then>
            </if>
    
    		<mkdir dir="${project.dir}/dist/" />
    
            <exec
                executable="${nativity.dir}/build-windows.cmd"
                failonerror="true"
            >
                <arg value="LiferayNativityUtil" />
                <arg value="${nativity.dir}/windows" />
                <arg value="${ms.sdk.7.1.dir}" />
                <arg value="${framework.dir}" />
            </exec>
    
            <copy
                file="${nativity.dir}/windows/LiferayNativityShellExtensions/Release/x64/LiferayNativityUtil_x64.dll"
                toFile="${project.dir}/dist/LiferayNativityUtil_x64.dll"
            />
    
            <copy
                file="${nativity.dir}/windows/LiferayNativityShellExtensions/Release/Win32/LiferayNativityUtil_x86.dll"
                toFile="${project.dir}/dist/LiferayNativityUtil_x86.dll"/>
    
        </target>
    
        <target name="build-windows-native-util" depends="compile">
            <if>
                <not>
                    <equals arg1="${target.os}" arg2="windows" />
                </not>
                <then>
                    <fail>
                        Ant target must be run in Windows
                    </fail>
                </then>
            </if>
    
    		<mkdir dir="${project.dir}/dist/" />
    
    		<antcall target="build-jni-headers" />
    
            <exec
                executable="${nativity.dir}/build-windows.cmd"
                failonerror="true"
            >
                <arg value="LiferayNativityWindowsUtil" />
                <arg value="${nativity.dir}/windows" />
                <arg value="${ms.sdk.7.1.dir}" />
                <arg value="${framework.dir}" />
            </exec>
    
            <copy
                file="${nativity.dir}/windows/LiferayNativityShellExtensions/Release/x64/LiferayNativityWindowsUtil_x64.dll"
                toFile="${project.dir}/dist/LiferayNativityWindowsUtil_x64.dll"
            />
    
            <copy
                file="${nativity.dir}/windows/LiferayNativityShellExtensions/Release/Win32/LiferayNativityWindowsUtil_x86.dll"
                toFile="${project.dir}/dist/LiferayNativityWindowsUtil_x86.dll"
            />
    
    		<copy
                file="${nativity.dir}/windows/LiferayNativityShellExtensions/Release/x64/LiferayNativityWindowsUtil_x64.dll"
                toFile="${java.dir}/lib/native/LiferayNativityWindowsUtil_x64.dll"
            />
    
            <copy
                file="${nativity.dir}/windows/LiferayNativityShellExtensions/Release/Win32/LiferayNativityWindowsUtil_x86.dll"
                toFile="${java.dir}/lib/native/LiferayNativityWindowsUtil_x86.dll"
            />
        </target>
    
    	<target name="build-windows-menus" depends="compile">
    		<property name="nativity.dir" value="${nativity.dir}" />
    		<property name="target.os" value="windows" />
    		<property name="ms.sdk.7.1.dir" value="${ms.sdk.7.1.dir}" />
    		<property name="framework.dir" value="${framework.dir}" />
    		<property name="context.menu.guid" value="${context.menu.guid}" />
    		
            <if>
                <not>
                    <equals arg1="${target.os}" arg2="windows" />
                </not>
                <then>
                    <fail>
                        Ant target must be run in Windows
                    </fail>
                </then>
            </if>
    
    		<mkdir dir="${project.dir}/dist/" />
    
    	    <copy
    	        file="${nativity.dir}/windows/LiferayNativityShellExtensions/LiferayNativityContextMenus/ContextMenuContants.h.original"
    	        toFile="${nativity.dir}/windows/LiferayNativityShellExtensions/LiferayNativityContextMenus/ContextMenuContants.h"
    	        overwrite="true"
    	        force="true"
    	        failonerror="true"
    	    />
    
    	    <replace file="${nativity.dir}/windows/LiferayNativityShellExtensions/LiferayNativityContextMenus/ContextMenuContants.h">
    	    	<replacefilter token="[$context.menu.guid$]" value="${context.menu.guid}" />
    	   </replace>
    
            <exec
                executable="${nativity.dir}/build-windows.cmd"
                failonerror="true"
            >
                <arg value="LiferayNativityContextMenus" />
                <arg value="${nativity.dir}/windows" />
                <arg value="${ms.sdk.7.1.dir}" />
                <arg value="${framework.dir}" />
            </exec>
    
            <copy
                file="${nativity.dir}/windows/LiferayNativityShellExtensions/Release/x64/LiferayNativityContextMenus_x64.dll"
                toFile="${project.dir}/dist/LiferayNativityContextMenus_x64.dll"
            />
    
            <copy
                file="${nativity.dir}/windows/LiferayNativityShellExtensions/Release/Win32/LiferayNativityContextMenus_x86.dll"
                toFile="${project.dir}/dist/LiferayNativityContextMenus_x86.dll"
            />
    
        </target>
    
    	<target name="build-windows-overlays" depends="compile">
    		  <property name="nativity.dir" value="${nativity.dir}" />
    		  <property name="dist.dir" value="${project.dir}/dist" />
    		  <property name="target.os" value="windows" />
    		  <property name="ms.sdk.7.1.dir" value="${ms.sdk.7.1.dir}" />
    		  <property name="framework.dir" value="${framework.dir}" />
    
    		  <property name="overlay.name" value="${overlay.name.?}" />
    		  <property name="overlay.guid" value="${overlay.guid.?}" />
    		  <property name="overlay.id" value="${overlay.id.?}" />
    		  <property name="overlay.path" value="${overlay.path.?}" />
            <if>
                <not>
                    <equals arg1="${target.os}" arg2="windows" />
                </not>
                <then>
                    <fail>
                        Ant target must be run in Windows
                    </fail>
                </then>
            </if>
    
    		<mkdir dir="${project.dir}/dist/" />
    		
    	    <copy
    		    file="${overlay.path}"
    	        toFile="${nativity.dir}/windows/LiferayNativityShellExtensions/LiferayNativityOverlays/overlay.ico"
    	        overwrite="true"
    	        force="true"
    	        failonerror="true"
    	    />
    
    		<copy
    		    file="${nativity.dir}/windows/LiferayNativityShellExtensions/LiferayNativityOverlays/OverlayConstants.h.original"
    		    toFile="${nativity.dir}/windows/LiferayNativityShellExtensions/LiferayNativityOverlays/OverlayConstants.h"
    		    overwrite="true"
    		    force="true"
    		    failonerror="true"
    		/>
    
    	    <replace file="${nativity.dir}/windows/LiferayNativityShellExtensions/LiferayNativityOverlays/OverlayConstants.h">
    	        <replacefilter token="[$overlay.guid$]" value="${overlay.guid}" />
    	        <replacefilter token="[$overlay.id$]" value="${overlay.id}" />
    	    	<replacefilter token="[$overlay.name$]" value="${overlay.name}" />
    	    </replace>
    
            <exec
                executable="${nativity.dir}/build-windows.cmd"
                failonerror="true"
            >
                <arg value="LiferayNativityOverlays" />
                <arg value="${nativity.dir}/windows" />
                <arg value="${ms.sdk.7.1.dir}" />
                <arg value="${framework.dir}" />
            </exec>
    
    		<copy
    		    file="${nativity.dir}/windows/LiferayNativityShellExtensions/Release/x64/LiferayNativityOverlays_x64.dll"
    		    toFile="${project.dir}/dist/${overlay.name}_x64.dll"
    		/>
    
            <copy
                file="${nativity.dir}/windows/LiferayNativityShellExtensions/Release/Win32/LiferayNativityOverlays_x86.dll"
                toFile="${project.dir}/dist/${overlay.name}_x86.dll"
            />
    
        </target>
    
    	<target name="clean">
    		<delete dir="${java.dir}/classes" />
    		<delete dir="dist" />
    		<delete dir="${project.tmp.dir}" />
    	</target>
    
    	<target name="compile">
    		<antcall target="compile-java">
    			<param name="javac.classpathref" value="lib.classpath" />
    			<param name="javac.destdir" value="${java.dir}/classes" />
    			<param name="javac.srcdir" value="${java.dir}/src" />
    		</antcall>
    	</target>
    
    	<target name="compile-java">
    		<mkdir dir="${java.dir}/classes" />
    
    		<copy todir="${javac.destdir}">
    			<fileset dir="${javac.srcdir}" excludes="**/*.java" />
    		</copy>
    
    		<javac
    			classpathref="${javac.classpathref}"
    			compiler="${javac.compiler}"
    			debug="${javac.debug}"
    			deprecation="${javac.deprecation}"
    			destdir="${javac.destdir}"
    			encoding="${javac.encoding}"
    			includeAntRuntime="false"
    			nowarn="${javac.nowarn}"
    			srcdir="${javac.srcdir}"
    		/>
    
    		<echo file="${javac.destdir}/.touch"></echo>
    	</target>
    
    	<target name="format-source">
    		<java
    			classname="com.liferay.portal.tools.sourceformatter.SourceFormatter"
    			classpathref="portal.classpath"
    			dir="${java.dir}"
    			fork="true"
    			newenvironment="true"
    		>
    			<jvmarg value="-Xmx128m" />
    		</java>
    
    		<delete file="ServiceBuilder.temp" />
    	</target>
    </project>

    

-Configure your build.properties, for example like this:

       
        ##
        ## Nativity
        ##
            nativity.dir=Your liferay path here
        	nativity.version=1.0.1
        	ms.sdk.7.1.dir=C:/Program Files/Microsoft SDKs/Windows/v7.1
        	framework.dir=C:/Windows/Microsoft.NET/Framework64/v4.0.30319
        
        	context.menu.guid={0DD5B4B0-25AF-4e09-A46B-9F274F3D7000}
        
        	overlay.name=LiferayNativityOKOverlay
        	overlay.guid={0DD5B4B0-25AF-4e09-A46B-9F274F3D7001}
        	overlay.id=1
        	overlay.path=Your liferay path here/windows/LiferayNativityShellExtensions/LiferayNativityOverlays/ok_overlay.ico
        
        	overlay.name.syncing=LiferayNativitySyncingOverlay
        	overlay.guid.syncing={0DD5B4B0-25AF-4e09-A46B-9F274F3D7002}
        	overlay.id.syncing=2
        	overlay.path.syncing=Your liferay path here/windows/LiferayNativityShellExtensions/LiferayNativityOverlays/syncing_overlay.ico
        
        	overlay.name.error=LiferayNativityErrorOverlay
        	overlay.guid.error={0DD5B4B0-25AF-4e09-A46B-9F274F3D7003}
        	overlay.id.error=3
        	overlay.path.error=Your liferay path here/windows/LiferayNativityShellExtensions/LiferayNativityOverlays/error_overlay.ico
    

Little build-compile guide
--------------
Here are a few steps you can follow to make the projects and libraries:

-Open a shell as an Admin user going to Start->Execute->"cmd.exe" (without quotes)   

-Try to execute the ant command. If all is ok, you should see something like this in the shell:
            
            Buildfile: build.xml does not exist!
            Build failed
            
-If you receive a message saying something like the executable cannot be found or that doesn't exist, you probably did something wrong on the first steps.

-To build the Util project or the Overlay project you can execute this commands (respectively):
        ```
        ant build-windows-overlays
        ant build-windows-native-util
        ```
-All should be done if you followed all the steps.
