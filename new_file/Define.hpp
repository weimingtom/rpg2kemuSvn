#ifndef _INC__RPG2K__DEFINES_HPP
#define _INC__RPG2K__DEFINES_HPP

#include <cstdlib>
#include <stdint.h>

#include <string>

#include "Vector.hpp"


namespace rpg2kLib
{
	typedef unsigned int uint;

	namespace structure
	{
		class Binary;
	}

	class SystemString;
	class RPG2kString : public std::string
	{
	public:
		explicit RPG2kString() : std::string() {}
		RPG2kString(const std::string& str) : std::string(str) {}
		RPG2kString(const RPG2kString& str) : std::string(str) {}
		RPG2kString(const RPG2kString& str, size_t pos, size_t n = npos) : std::string(str, pos, n) {}
		RPG2kString(const char * s, size_t n) : std::string(s, n) {}
		RPG2kString(const char * s) : std::string(s) {}
		RPG2kString(size_t n, char c) : std::string(n, c) {}
		template< class InputIterator > RPG2kString (InputIterator begin, InputIterator end) : std::string(begin, end) {}

		// ~RPG2kString() { this->std::string::~string(); }

		SystemString toSystem() const;
	}; // class RPG2kString
	class SystemString : public std::string
	{
	public:
		explicit SystemString() : std::string() {}
		SystemString(const std::string& str) : std::string(str) {}
		SystemString(const SystemString& str) : std::string(str) {}
		SystemString(const SystemString& str, size_t pos, size_t n = npos) : std::string(str, pos, n) {}
		SystemString(const char * s, size_t n) : std::string(s, n) {}
		SystemString(const char * s) : std::string(s) {}
		SystemString(size_t n, char c) : std::string(n, c) {}
		template< class InputIterator > SystemString (InputIterator begin, InputIterator end) : std::string(begin, end) {}

		// ~SystemString() { this->std::string::~string(); }

		RPG2kString toRPG2k() const;
	}; // class SystemString

	static uint const FRAME_PER_LOOP = 60;

	static uint const MEMBER_MAX = 4;

	static uint const ID_MIN = 1;
	static uint const DATA_BASE_MAX = 5000;

	static uint const MAP_UNIT_MAX = 9999, SAVE_DATA_MAX = 15;

	static uint const CHIP_REPLACE_MAX = 144;
	enum ChipSetType { CHIP_LOWER = 0, CHIP_UPPER, CHIP_TYPE_END };

	static int const CURSOR_SPEED = 4;

	static uint const ITEM_MIN = 0, ITEM_MAX = 99;

	static uint const
		EV_ID_PARTY = 10001,
		EV_ID_BOAT  = 10002, EV_ID_SHIP = 10003, EV_ID_AIRSHIP = 10004,
		EV_ID_THIS  = 10005;
	static int const EV_STACK_MAX = 1000;
	static int const EV_STEP_MAX = 10000;
	static int const INVALID_PAGE_ID = -1;

	static int const INVALID_ID = 0;
	static int const CONDITION_NORMAL = 0;

	enum RPG2kKey
	{
		KEY_NONE = 0,
		KEY_ENTER, KEY_CANCEL,
		KEY_SHIFT, KEY_INSTANT_SHOW = KEY_SHIFT,
		KEY_UP, KEY_RIGHT, KEY_DOWN, KEY_LEFT,
	// test play keys
		// CTRL
		KEY_IGNORE_BLOCK,
		// F9
		KEY_DEBUG,
		// F10
		KEY_CLOSE_EVENT,
	// change modes
		// psp select button
		KEY_QUICK_LOAD,
	};

	enum CharSetDir { CHAR_DIR_UP = 0, CHAR_DIR_RIGHT, CHAR_DIR_DOWN, CHAR_DIR_LEFT, CHAR_DIR_END };
	enum CharSetPat { CHAR_PAT_LEFT = 0, CHAR_PAT_MIDDLE, CHAR_PAT_RIGHT, CHAR_PAT_END };

	enum EventDir { EV_DIR_DOWN = 2, EV_DIR_LEFT = 4, EV_DIR_RIGHT = 6, EV_DIR_UP = 8, };

	enum EventMoveType
	{
		EV_FIXED = 0, EV_RANDOM_MOVE, EV_UP_DOWN, EV_LEFT_RIGHT,
		EV_TO_PARTY, EV_FROM_PARTY, EV_MANUAL_MOVE,
	};

	static int const EVENT_START_NUM = 5;
	enum EventStart
	{
		EV_KEY_ENTER = 0, EV_PARTY_TOUCH, EV_EVENT_TOUCH,
		EV_AUTO, EV_PARALLEL, EV_CALLED,
	};

	enum EventPriority { EV_PR_BELOW = 0, EV_PR_CHAR, EV_PR_ABOVE, EV_PR_END, };

	enum EquipType { EQ_WEAPON = 0, EQ_SIELD, EQ_ARMOR, EQ_HELMET, EQ_OTHER, EQ_END, };
	enum ItemType {
		ITEM_NORMAL = 0,
		ITEM_WEAPON, ITEM_SHIELD, ITEM_ARMOR, ITEM_HELMET, ITEM_ACCESSORY,
		ITEM_MEDICINE, ITEM_BOOK, ITEM_SEED, ITEM_SPECIAL, ITEM_SWITCH,
		ITEM_TYPE_END,
	};

	static int const EQ_PARAM_NUM = 4;
	enum ParamType {
		PRM_HP = 0, PRM_MP,
		PRM_ATTACK, PRM_GAURD, PRM_MIND, PRM_SPEED,
		PRM_END,
	};

	static Size2D const SCREEN_SIZE(320, 240);

	static Size2D const SCROLL_SIZE(16, 8);

	static Size2D const FACE_SIZE(48, 48);
	static Size2D const CHIP_SIZE(16, 16);
	static Size2D const CHAR_SIZE(24, 32);

	static Size2D const MONEY_WINDOW_SIZE(88, 32);

	static int const CURSOR_H = 16;

	static int const MIX_CHUNK_SIZE = 1024;
	static int const FADEIN_MIN =  0, FADEIN_MAX = 10000;
	static int const VOLUME_MIN =  0, VOLUME_MAX =   100;
	static int const  TEMPO_MIN = 50,  TEMPO_MAX =   150;
	static int const BALANCE_LEFT = 0, BALANCE_CENTER = 50, BALANCE_RIGHT = 100;
	static int const FADE_IN_DEF =   0;
	static int const VOLUME_DEF  = 100;
	static int const TEMPO_DEF   = 100;
	static int const BALANCE_DEF =  50;
	static RPG2kString const AUDIO_OFF = "(OFF)";

	static SystemString const PATH_SEPR = "/";
	static SystemString const SETTING_FILE = "setting";

	static int const PARAM_MAX = 999, PARAM_MIN = 1;

	static uint32_t const VAR_DEF_VAL = 0;
	static bool const SWITCH_DEF_VAL = false;

	#if defined(RPG2000) || defined(RPG2000_VALUE)
		static int32_t const VAR_MAX =  999999, VAR_MIN = -999999;

		static int const EXP_MAX = 999999, EXP_MIN = 0;
		static int const EXP_DEF_VAL = 30;

		static int const LV_MAX = 50, LV_MIN = 1;

		static int const CHAR_HP_MAX = 999, CHAR_HP_MIN = 1;
		static int const MP_MIN = 0;

		#if defined(RPG2000)
			static int const ENEMY_HP_MAX = 9999;
			static int const PICUTURE_NUM = 20;
		#elif defined(RPG2000_VALUE)
			static int const ENEMY_HP_MAX = 99999;
			static int const PICUTURE_NUM = 50;
		#endif
		static int const ENEMY_HP_MIN = 1;
	#elif defined(RPG2003)
		static int32_t const VAR_MAX =  9999999, VAR_MIN = -9999999;

		static int const EXP_MAX = 9999999, EXP_MIN = 0;
		static int const EXP_DEF_VAL = 300;

		static int const LV_MAX = 99, LV_MIN = 1;

		static int const CHAR_HP_MAX = 9999, CHAR_HP_MIN = 1;
		static int const ENEMY_HP_MAX = 99999, ENEMY_HP_MIN = 1;

		static int const PICUTURE_NUM = 50;
	#endif

	static int const MONEY_MIN = 0, MONEY_MAX = VAR_MAX;

	inline CharSetDir toCharSetDir(EventDir dir)
	{
		switch(dir) {
			case EV_DIR_DOWN : return CHAR_DIR_DOWN ;
			case EV_DIR_LEFT : return CHAR_DIR_LEFT ;
			case EV_DIR_RIGHT: return CHAR_DIR_RIGHT;
			case EV_DIR_UP   : return CHAR_DIR_UP   ;
			default: return CHAR_DIR_DOWN;
		}
	}
	inline EventDir toEventDir(RPG2kKey key)
	{
		switch(key) {
			case KEY_UP   : return EV_DIR_UP   ;
			case KEY_LEFT : return EV_DIR_LEFT ;
			case KEY_RIGHT: return EV_DIR_RIGHT;
			case KEY_DOWN : return EV_DIR_DOWN ;
			default: return (EventDir)0;
		}
	}

	extern uint random();
	inline uint random(uint max) { return random()%max; }
	inline int random(int min, int max) { return random(max-min)+min; }

	namespace Action
	{
		typedef uint Type;
		namespace Move { enum {
			UP = 0, RIGHT, DOWN, LEFT,
			RIGHT_UP, RIGHT_DOWN, LEFT_DOWN, LEFT_UP,
			RANDOM, TO_PARTY, FROM_PARTY, A_STEP,
		}; }
		namespace Face { enum {
			UP = 12, RIGHT, DOWN, LEFT,
		}; }
		namespace Turn { enum {
			RIGHT_90 = 16, LEFT_90, OPPOSITE,
			RIGHT_OR_LEFT_90, RANDOM, TO_PARTY, OPPOSITE_OF_PARTY,
		}; }
		enum {
			HALT = 23,
			BEGIN_JUMP, END_JUMP,
			FIX_DIR, UNFIX_DIR,
			SPEED_UP, SPEED_DOWN,
			FREQ_UP, FREQ_DOWN,
			SWITCH_ON, SWITCH_OFF,
			CHANGE_GRAPHIC, PLAY_SOUND,
			BEGIN_SLIP, END_SLIP,
			BEGIN_ANIME, END_ANIME,
			TRANS_UP, TRANS_DOWN, // transparentcy
		};
	}

	/*
	 * hiragana used with vocabulary
	 */
	 namespace hiragana
	 {
		static const char GA[] = { 0x82, 0xaa, 0x00 };
		static const char HA[] = { 0x82, 0xcd, 0x00 };
		static const char NI[] = { 0x82, 0xc9, 0x00 };
		static const char NO[] = { 0x82, 0xcc, 0x00 };
		static const char WO[] = { 0x82, 0xF0, 0x00 };
	}
} // namespace rpg2kLib

#endif
