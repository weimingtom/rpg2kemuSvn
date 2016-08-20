#ifndef _INC__RPG2k__GAMEMODE_DEBUG_HPP
#define _INC__RPG2k__GAMEMODE_DEBUG_HPP

#include <GameMode.hpp>


namespace rpg2kLib
{
	namespace gamemode
	{
		PP_defineGameMode(Debug);
/*
		class Debug : public GameMode {
		private:
		public:
			Debug(rpg2kLib::Main& m);

			void reset();
			void run(input::KeyListener& keyList);
			void gameModeChanged();
		}; // class Debug
 */
	} // namespace gamemode
} // namespace rpg2kLib

#endif
