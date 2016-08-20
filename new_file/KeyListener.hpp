#ifndef _INC__RPG2K__KEY_BUFFER
#define _INC__RPG2K__KEY_BUFFER

#include <queue>
#include <stack>
#include <SDL.h>
#include "Define.hpp"

namespace rpg2kLib
{
	class Main;

	namespace input
	{
		class KeyListener
		{
		private:
			Main& owner_;

			std::queue< RPG2kKey > buffer_;
			std::stack< RPG2kKey > cursor_;

			RPG2kKey lastCursor_;

			bool down_, right_, up_, left_;
			bool alt_, ctrl_, shift_;
			bool enter_, cancel_;
		protected:
			void removeCursor(RPG2kKey key);
		public:
			KeyListener(Main& m);
			~KeyListener();

			RPG2kKey get() const;
			RPG2kKey pop();
			void push(RPG2kKey key);

			RPG2kKey getCursor();
			RPG2kKey lastCursor() const { return lastCursor_; }

			bool left () const { return left_ ; }
			bool up   () const { return up_   ; }
			bool right() const { return right_; }
			bool down () const { return down_ ; }

			bool alt  () const { return alt_  ; }
			bool ctrl () const { return ctrl_ ; }
			bool shift() const { return shift_; }

			bool enter () const { return enter_ ; }
			bool cancel() const { return cancel_; }

			void clear();
			void clearBuffer();
			void clearCursor();

			void load();
		}; // class KeyListener
	} // namespace input
} // namespace rpg2kLib

#endif
