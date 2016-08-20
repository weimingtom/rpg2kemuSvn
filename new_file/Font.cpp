#include <cctype>
#include <stdexcept>

#include "Font.hpp"


template< typename T >
bool within(T a, T v, T b) { return ( (a <= v) && (v < b) ); }

namespace rpg2kLib
{
	namespace font
	{
		font_t* Font::getFull(RPG2kString::const_iterator& it, Face::Type t)
		{
			switch(t) {
				case Face::MINCHO: return getMincho(it);
				case Face::GOTHIC: return getGothic(it);
				default: throw std::runtime_error("unknown face type");
			}
		}
		font_t* Font::getFullFrom(RPG2kString::const_iterator& it, font_t (&src)[KU_MAX][TEN_MAX][FONT_H])
		{
			if( isExternal(it) ) return getExternal(it);

			uint ku = 0, ten = 0;

		// calculate ku
			int first = *(it++) & 0xff;
			     if( within(0x81, first, 0xa0) ) ku = first - 0x81 + 0x00;
			else if( within(0xe0, first, 0xf0) ) ku = first - 0xe0 + 0x20;
			else throw std::runtime_error("unknown char");
			ku *= 2;
		// calculate ten
			int second = *(it++) & 0xff;
			     if( within(0x40, second, 0x7f) ) { ten = second - 0x40 + 0x00; ku += 0; }
			else if( within(0x80, second, 0x9f) ) { ten = second - 0x80 + 0x3f; ku += 0; }
			else if( within(0x9f, second, 0xfd) ) { ten = second - 0x9f + 0x00; ku += 1; }
			else throw std::runtime_error("unknown char");

			return src[ku][ten];
		}
		font_t* Font::getHalfFrom(RPG2kString::const_iterator& it, font_t (&src)[0x100][FONT_H])
		{
			int first = *it & 0xff;
			if( (it[0] == '$') && (it[1] == '$') ) return src[static_cast< uint >('$')];
			else if(
				within(0x00, first, 0x80) || within(0xa1, first, 0xe0)
			) return src[static_cast< uint >( *(it++) )];
			else throw std::runtime_error("unknown char");
		}

		font_t* Font::getExternal(RPG2kString::const_iterator& it)
		{
			if( isExternal(it++) ) {
				     if( std::islower(*it) ) return EXTERNAL_LW[*(it++) - 'a'];
				else if( std::isupper(*it) ) return EXTERNAL_UP[*(it++) - 'A'];
			}
			throw std::runtime_error("unknown char");
		}

		CharType Font::check(RPG2kString::const_iterator const& it)
		{
			int first = *it & 0xff;
			if(
				isExternal(it) ||
				within(0x81, first, 0xa0) || within(0xe0, first, 0xf0)
			) return FULL;
			else if(
				within(0x00, first, 0x80) || within(0xa1, first, 0xe0)
			) return HALF;
			else throw std::runtime_error("unknown char");
		}
		bool Font::isExternal(RPG2kString::const_iterator const& it)
		{
			return (it[0] == '$') && ( std::isalpha( it[1] ) );
		}

		uint Font::width(RPG2kString const& str)
		{
			static std::string DOLLAR_SIGN = "$$";

			uint ret = str.length();

			for(
				uint pos = 0;
				( pos = str.find(DOLLAR_SIGN, pos) ) != RPG2kString::npos;
				ret--, pos += DOLLAR_SIGN.length()
			);

			return HALF_FONT_W * ret;
		}
	} // namespace font
} // namespace rpg2kLib
