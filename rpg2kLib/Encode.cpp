#include <stdexcept>

#include "Debug.hpp"
#include "Encode.hpp"

#define getlang() getenv("LANG")


namespace rpg2kLib
{
	#if RPG2K_IS_WINDOWS
		std::string Encode::SYS_ENCODE = "Windows-31J";
	#else
		std::string Encode::SYS_ENCODE = "UTF-8";
	#endif
	std::string const Encode::RPG2K_ENCODE = "Windows-31J"; // "Shift_JIS";

	Encode::Encode()
	{
		std::string sysEncode = SYS_ENCODE;

		/*
		 * geting system encoding name from "LANG" env
		 * works only on unix systems(as I know)
		 */
		if( getlang() != NULL ) {
			std::string langStr( getlang() );
			for(std::string::iterator it = langStr.begin(); it < langStr.end(); ++it) {
				if( *it == '.' ) {
					sysEncode.assign( ++it, langStr.end() );
					// clog << sysEncode << endl;
					break;
				}
			}
		}

		toSystem_ = openConverter(sysEncode, RPG2K_ENCODE);
		toRPG2k_  = openConverter(RPG2K_ENCODE, sysEncode);
	}
	Encode::~Encode()
	{
		if( ::iconv_close(toSystem_) == -1 )
			throw std::runtime_error( "iconv_close error: " + debug::getError(errno) );
		if( ::iconv_close(toRPG2k_ ) == -1 )
			throw std::runtime_error( "iconv_close error: " + debug::getError(errno) );

		toSystem_ = toRPG2k_ = NULL;
	}

	Encode& Encode::instance()
	{
		static Encode theEncode;
		return theEncode;
	}

	iconv_t Encode::openConverter(std::string const& to, std::string const& from)
	{
		iconv_t ret = ::iconv_open( to.c_str(), from.c_str() );
		if( ret == (iconv_t)-1 ) {
			throw std::runtime_error( "iconv_open error: " + debug::getError(errno) );
		} else return ret;
	}

	std::string Encode::convertString(std::string const& src, iconv_t cd)
	{
		char iconvBuff[BUFF_SIZE];
		size_t iconvOutSize = BUFF_SIZE, iconvInSize  = src.length();
		char* iconvOut = iconvBuff;
		#if RPG2K_IS_PSP && !defined(_LIBICONV_H)
			char const* iconvIn  = src.c_str();
		#else
			char* iconvIn  = const_cast< char* >( src.c_str() );
		#endif

		if( ::iconv(cd, &iconvIn, &iconvInSize, &iconvOut, &iconvOutSize) == (size_t) -1 ) {
			throw std::runtime_error( "iconv error: " + debug::getError(errno) );
		} else return std::string(iconvBuff, BUFF_SIZE-iconvOutSize);
	}
} // namespace rpg2kLib
