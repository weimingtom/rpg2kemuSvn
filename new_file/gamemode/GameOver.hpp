#ifndef _INC__RPG2k__GAME_MODE_GAMEOVER
#define _INC__RPG2k__GAME_MODE_GAMEOVER

#include <GameMode.hpp>


namespace rpg2kLib
{
	namespace gamemode
	{
		PP_defineGameMode(GameOver);
/*
		class GameOver : public GameMode
		{
		private:
		public:
			GameOver(rpg2kLib::Main& m);

			void reset();
			void run(input::KeyListener& keyList);
			void gameModeChanged();
		};
 */
	} // namespace gamemode
} // namespace rpg2kLib

#endif
