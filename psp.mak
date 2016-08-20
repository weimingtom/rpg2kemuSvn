TARGET = RPG2K_Emu_PSP

PSPSDK   = $(shell psp-config --pspsdk-path)
PSPBIN   = $(PSPSDK)/../bin

CFLAGS   = \
	-Os -Wall -Werror -g3 \
	$(shell $(PSPBIN)/sdl-config --cflags) \

ASFLAGS  = $(CFLAGS)
CXXFLAGS = $(CFLAGS) -fexceptions -fno-rtti -include Config.hpp
LDFLAGS  = -Wl,-Map=$(TARGET).map --enable-gold

CXXFLAGS += -DRPG2K_IS_LITTLE_ENDIAN=1 -DRPG2K_IS_BIG_ENDIAN=0

INCDIR = $(RPG2KLIB_BASE_DIR) ./SOIL/src
LIBDIR =

LIBS     = \
	-lstdc++ -lc -lSDLmain \
	-lz \
	-lSDL_mixer -lsmpeg -lvorbisfile -lvorbis -logg \
	-lGL \
	$(shell $(PSPBIN)/sdl-config --libs) \
	-lpsppower -lpspvfpu

EXTRA_TARGETS    = EBOOT.PBP
PSP_EBOOT_TITLE  = "RPG2K Emulator for PSP"
PSP_EBOOT_ICON   = NULL
PSP_EBOOT_ICON1  = NULL
PSP_EBOOT_UNKPNG = NULL
PSP_EBOOT_PIC1   = NULL
PSP_EBOOT_SND0   = NULL

default : all

include objs.mak

include $(PSPSDK)/lib/build.mak

CC  = ccache psp-gcc -std=c99
CXX = ccache psp-g++ -std=c++98

debug : $(TARGET).elf
	psp-gdb $(TARGET).elf
