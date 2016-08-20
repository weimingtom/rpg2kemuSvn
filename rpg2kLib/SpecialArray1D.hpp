#ifndef _INC__STRUCTURE__SPECIAL_ARRAY1D_HPP
#define _INC__STRUCTURE__SPECIAL_ARRAY1D_HPP

#include "Array1D.hpp"
#include "Element.hpp"


namespace rpg2kLib
{
	namespace structure
	{
		class Sound : public Array1D
		{
		public:
			Sound(Element& e, Descriptor const& info) : Array1D(e, info) {}
			Sound(Element& e, Descriptor const& info, StreamReader& s) : Array1D(e, info, s) {}
			Sound(Element& e, Descriptor const& info, Binary const& b) : Array1D(e, info, b) {}

			virtual ~Sound() {}

			RPG2kString fileName() const { return (*this)[1]; }
			int volume () const { return (*this)[3]; }
			int tempo  () const { return (*this)[4]; }
			int balance() const { return (*this)[5]; }

			operator RPG2kString() const { return fileName(); }
		}; // class Sound

		class Music : public Sound
		{
		public:
			Music(Element& e, Descriptor const& info) : Sound(e, info) {}
			Music(Element& e, Descriptor const& info, StreamReader& s) : Sound(e, info, s) {}
			Music(Element& e, Descriptor const& info, Binary const& b) : Sound(e, info, b) {}

			virtual ~Music() {}

			int fadeInTime() const { return (*this)[2]; }
		}; // class Music

		class EventState : public Array1D
		{
		public:
			EventState(Element& e, Descriptor const& info) : Array1D(e, info) {}
			EventState(Element& e, Descriptor const& info, StreamReader& s) : Array1D(e, info, s) {}
			EventState(Element& e, Descriptor const& info, Binary const& b) : Array1D(e, info, b) {}

			virtual ~EventState() {}

			int mapID() const { return (*this)[11]; }
			int x() const { return (*this)[12]; }
			int y() const { return (*this)[13]; }
			Vector2D position() const { return Vector2D( x(), y() ); }

			RPG2kString const& charSet() const { return (*this)[73]; }
			int charSetPos() const { return (*this)[74]; }
			CharSetDir charSetDir() const { return (CharSetDir) (*this)[75].get_int(); }
			CharSetPat charSetPat() const { return CHAR_PAT_MIDDLE; }

			EventDir eventDir() const { return (EventDir) (*this)[21].get_int(); }
			EventDir  talkDir() const { return (EventDir) (*this)[22].get_int(); }

			// CharSetDir charSetDir() const;
		}; // class EventState
	} // namespace structure
} // namespace rpg2kLib

#endif // _INC__STRUCTURE__SPECIAL_ARRAY1D_HPP
