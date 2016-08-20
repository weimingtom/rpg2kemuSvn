RPG2KLIB_BASE_DIR = ./rpg2kLib
SOIL_BASE_DIR = ./SOIL/src

CURRENT_TIME = $(shell date +%F__%H~%M~%S__%Z)

SOIL_SRC = image_helper.c stb_image_aug.c image_DXT.c SOIL.c
SRC_CXX = \
	malloc/wrapper.cpp main.cpp \
	$(wildcard $(RPG2KLIB_BASE_DIR)/*.cpp) \
	$(wildcard $(RPG2KLIB_BASE_DIR)/define/*.cpp) \
	$(wildcard $(RPG2KLIB_BASE_DIR)/gamemode/*.cpp) \

OBJS = \
	$(SRC_CXX:.cpp=.o) \
	$(addprefix $(SOIL_BASE_DIR)/, $(SOIL_SRC:.c=.o)) \

%.cpp : %.hpp
