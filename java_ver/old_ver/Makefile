TARGET			= $(EXE_CLASS).class

MAKE			= make.exe
DEL				= rm
MORE			= more
JAR				= jar.exe cvfm
JAR_UPDATE		= jar.exe uvfm
JAVAC			= javac.exe -g -deprecation -Xlint:unchecked \
				-source 1.5 -target 1.5
JAVA			= java.exe
JAVA_JAR		= java.exe -jar
JAVA_1.1		= c:\jdk1.1.8\bin\$(JAVA)
JAVA_1.2		= $(JAVA) -version:1.2
JAVA_1.3		= $(JAVA) -version:1.3.1_20
JAVA_1.4		= $(JAVA) -version:1.4.2_18
JAVA_1.5		= $(JAVA) -version:1.5
JAVA_1.6		= $(JAVA) -version:1.6
RUN_APPLET		= appletviewer Controller.java
RUN_PROF		= -Xrunhprof

CLASSES		= *.class \
			rpg2k/*.class \
			rpg2k/analyze/*.class \
			rpg2k/database/*.class \
			rpg2k/media/*.class \
			rpg2k/media/image/*.class \
			rpg2k/sprite/*.class
SOURCES		= *.java \
			rpg2k/*.java \
			rpg2k/analyze/*.java \
			rpg2k/database/*.java \
			rpg2k/media/*.java \
			rpg2k/media/image/*.java \
			rpg2k/sprite/*.java
EXE_CLASS	= Controller
MANIFEST	= Emulater.mf
SETTING		= SETTING DEF_SETTING
JAR_FILE	= RPG2k_Emu_J.jar

default : $(TARGET)

$(TARGET) : $(EXE_CLASS).java
	$(JAVAC) $(EXE_CLASS).java

run :
	$(MAKE) default
	$(JAVA) $(EXE_CLASS)
runapplet : $(JAR_FILE)
	$(RUN_APPLET)
runjar : $(JAR_FILE)
	$(JAVA_JAR) $(JAR_FILE)
run1.1 :
	$(MAKE) default
	$(JAVA_1.1) $(EXE_CLASS)
run1.2 :
	$(MAKE) default
	$(JAVA_1.2) $(EXE_CLASS)
run1.3 :
	$(MAKE) default
	$(JAVA_1.3) $(EXE_CLASS)
run1.4 :
	$(MAKE) default
	$(JAVA_1.4) $(EXE_CLASS)
run1.5 :
	$(MAKE) default
	$(JAVA_1.5) $(EXE_CLASS)
run1.6 :
	$(MAKE) default
	$(JAVA_1.6) $(EXE_CLASS)
prof :
	$(MAKE) default
	$(JAVA) $(RUN_PROF) $(EXE_CLASS)
clean :
	-$(DEL) $(CLASSES)
	-$(DEL) $(JAR_FILE)
jar : $(JAR_FILE)
srcjar : $(TARGET)
	$(JAR) $(JAR_FILE) $(MANIFEST) $(CLASSES) $(SOURCES) $(SETTING)
$(JAR_FILE) : $(TARGET)
	$(JAR) $(JAR_FILE) $(MANIFEST) $(CLASSES) $(SETTING)
