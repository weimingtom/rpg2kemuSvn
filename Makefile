TARGET = RPG2K_Emu_PC

LOG_DIR = log

CONFIG_SRC = Config.hpp

LIBS = \
	-lz -lSDL_mixer \
	$(shell pkg-config sdl --libs) \
	$(shell pkg-config glu --libs) \
	$(shell pkg-config gl  --libs) \

CFLAGS   = \
	-Os -Wall -Werror \
	-g3 \
	$(shell pkg-config glu --cflags) \
	$(shell pkg-config gl  --cflags) \
	$(shell sdl-config --cflags) \
	-I$(RPG2KLIB_BASE_DIR) -I./SOIL/src \

CXXFLAGS = $(CFLAGS) -fexceptions -include $(CONFIG_SRC)
LDFLAGS  = -Wl,-Map=$(TARGET).map

# using distcc + ccache
# if you don't have it, disable it
COMPILER_PREFIX = ccache
# COMPILER_PREFIX = distcc ccache

CC  = $(COMPILER_PREFIX) clang
CXX = $(COMPILER_PREFIX) clang++
LD  = $(COMPILER_PREFIX) clang++

#
CC  += -std=c99
CXX += -std=c++98

all : $(TARGET)

include objs.mak

$(TARGET) : $(OBJS)
	$(LD) $(CFLAGS) $(LDFLAGS) $(LIBS) -o $(TARGET) $(OBJS)

run : $(TARGET) copy_log_file
	./$(TARGET)

CFLAGS += -D_FORTIFY_SOURCE=2 \
#	-fstack-protector-all \

copy_log_file :
	-cat $(LOG_DIR)/err.txt > $(LOG_DIR)/err.$(CURRENT_TIME).txt
	-cat $(LOG_DIR)/out.txt > $(LOG_DIR)/out.$(CURRENT_TIME).txt

copy_profile_file :
	-cat $(LOG_DIR)/profile.txt > $(LOG_DIR)/profile.$(CURRENT_TIME).txt

check_leak : $(TARGET)
	valgrind --tool=memcheck --leak-check=full --show-reachable=yes ./$(TARGET)
check_malloc : $(TARGET)
	MALLOC_CHECK_=2 gdb $(TARGET)
# execute "ulimit -c unlimited" before this
efence : $(TARGET)
	-LD_PRELOAD=/usr/lib/libefence.so ./$(TARGET)
	gdb $(TARGET) core

doxygen :
	doxygen Doxyfile

profile : run copy_profile_file
	gprof ./$(TARGET) gmon.out > $(LOG_DIR)/profile.txt

debug : $(TARGET) copy_log_file
	gdb $(TARGET)

clean :
	-rm -f $(OBJS) $(TARGET) gmon.out $(TARGET).* core $(CONFIG_COMPILED)

clean_log :
	-rm $(LOG_DIR)/*.txt

rebuild : clean $(TARGET)
